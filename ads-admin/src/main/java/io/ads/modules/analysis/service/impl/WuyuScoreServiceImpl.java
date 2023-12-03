package io.ads.modules.analysis.service.impl;

import cn.hutool.core.date.DateTime;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mchange.lang.IntegerUtils;
import io.ads.common.exception.RenException;
import io.ads.common.service.impl.CrudServiceImpl;
import io.ads.common.utils.ConvertUtils;
import io.ads.common.utils.ExcelUtils;
import io.ads.modules.analysis.dao.WuyuAnalysisResultDao;
import io.ads.modules.analysis.dao.WuyuScoreDao;
import io.ads.modules.analysis.dto.WuyuAnalysisResultDTO;
import io.ads.modules.analysis.dto.WuyuScoreDTO;
import io.ads.modules.analysis.dto.WuyuWeightDTO;
import io.ads.modules.analysis.entity.WuyuAnalysisResultEntity;
import io.ads.modules.analysis.entity.WuyuScoreEntity;
import io.ads.modules.analysis.entity.WuyuWeightEntity;
import io.ads.modules.analysis.excel.WuyuScoreExcel;
import io.ads.modules.analysis.excel.WuyuScoreImportExcel;
import io.ads.modules.analysis.excel.listener.WuyuScoreReadListener;
import io.ads.modules.analysis.service.WuyuAnalysisResultService;
import io.ads.modules.analysis.service.WuyuScoreService;
import cn.hutool.core.util.StrUtil;
import io.ads.modules.analysis.service.WuyuWeightService;
import io.ads.modules.security.user.SecurityUser;
import io.ads.modules.security.user.UserDetail;
import io.ads.modules.sys.dto.SysUserDTO;
import io.ads.modules.sys.enums.SuperAdminEnum;
import io.ads.modules.sys.service.SysSchoolClassService;
import io.ads.modules.sys.service.SysSchoolGradeService;
import io.ads.modules.sys.service.SysSchoolSemesterService;
import io.ads.modules.sys.service.SysUserService;
import io.swagger.models.auth.In;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 五育分析（五育成绩表）
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-03
 */
@Service
public class WuyuScoreServiceImpl extends CrudServiceImpl<WuyuScoreDao, WuyuScoreEntity, WuyuScoreDTO> implements WuyuScoreService {

    @Autowired
    WuyuWeightService wuyuWeightService;
    @Autowired
    SysUserService sysUserService;
    @Autowired
    WuyuAnalysisResultDao wuyuAnalysisResultDao;
    @Autowired
    SysSchoolSemesterService sysSchoolSemesterService;
    @Autowired
    SysSchoolGradeService sysSchoolGradeService;
    @Autowired
    SysSchoolClassService sysSchoolClassService;
    /**
    //@Autowired
    //WuyuScoreServiceImpl scoreServiceProxy; // 代理对象，用来在本类中执行事务方法，但这样会出现循环依赖，spring2.6后默认不允许这样，所以还是使用AopContext来获取代理对象
    **/
    @Autowired
    RestTemplate restTemplate;
    // 分析接口
    private final String ANALYSIS_URL = "https://chatglm2.tocmcc.cn:443";


    @Override
    public QueryWrapper<WuyuScoreEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");
        String schoolId = (String)params.get("schoolId");
        String semesterId = (String)params.get("semesterId");
        String gradeId = (String)params.get("gradeId");
        String classId = (String)params.get("classId");
        String comprehensiveLevel = (String)params.get("comprehensiveLevel");
        String studentNameOrNo = (String)params.get("studentNameOrNo");

        // 任教年级列表，（针对老师）
        List<Long> gradeList = new ArrayList<>();

        // 普通用户或普通管理员，在这里填充其学校id
        UserDetail user = SecurityUser.getUser();
        if (user.getSuperAdmin() == SuperAdminEnum.NO.value()) {
            schoolId = user.getSchoolId().toString();
            gradeList = sysSchoolGradeService.getGradeIdListByStudentNo(user.getUsername());
        }


        QueryWrapper<WuyuScoreEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id)
                .eq(StrUtil.isNotBlank(schoolId), "school_id", schoolId)
                .eq(StrUtil.isNotBlank(comprehensiveLevel), "comprehensive_level", comprehensiveLevel)
                .eq(StrUtil.isNotBlank(semesterId), "semester_id", semesterId)
                .eq(StrUtil.isNotBlank(gradeId), "grade_id", gradeId)
                .eq(StrUtil.isNotBlank(classId), "class_id", classId)
                .in(!gradeList.isEmpty(), "grade_id", gradeList) //任教年级列表
                .like(StrUtil.isNotBlank(studentNameOrNo), "student_name", studentNameOrNo)
                .or()
                .like(StrUtil.isNotBlank(studentNameOrNo), "student_no", studentNameOrNo);

        return wrapper;
    }



    @Override
    @Transactional
    public void save(WuyuScoreDTO dto) {

        dto = calComprehensiveScoreAndLevel(dto);
        // todo 学业等级怎么确定待商榷，先固定
        dto.setAcademicLevel(1);

        // todo 每个学生一学期只有一个诊断报告，这个待定
        WuyuScoreEntity entity = new WuyuScoreEntity();
        BeanUtils.copyProperties(dto, entity);
        // 填充 学期id
        if (entity.getSemesterId() == null) { //如果学期id为空
            // 查询 该学校当前最新的学期，默认按最新学期填入
            entity.setSemesterId(sysSchoolSemesterService.getLatestSemesterId(entity.getSchoolId()));
            //System.out.println("空了----------------");
        }
        // 填充 年级id，根据学号
        entity.setGradeId(sysSchoolGradeService.getGradeIdListByStudentNo(entity.getStudentNo()).get(0));
        // 填充 班级id, 根据学号
        entity.setClassId(sysSchoolClassService.getClassIdByStudentNo(entity.getStudentNo()));

        // 填充 报告id
        // 直接插入一个没有诊断内容的报告，等到用户点击个人诊断报告时再生成具体的报告
        WuyuAnalysisResultEntity reportEntity = new WuyuAnalysisResultEntity();
        // 填入学号和姓名
        reportEntity.setStudentNo(entity.getStudentNo());
        reportEntity.setStudentName(entity.getStudentName());
        wuyuAnalysisResultDao.insert(reportEntity);

        // 会返回报告id，以此来绑定 分数记录和报告记录
        entity.setReportId(reportEntity.getId());

        // 插入 五育分数记录
        super.insert(entity);

        // 更新诊断报告记录，诊断报告 关联 五育分数id，
        reportEntity.setScoreId(entity.getId());
        wuyuAnalysisResultDao.updateById(reportEntity);

    }

    @Override
    public void update(WuyuScoreDTO dto) {
        super.update(calComprehensiveScoreAndLevel(dto));
    }

    @Override
    @Transactional
    public void delete(Long[] ids) {
        // 先根据五育分数id关联的诊断报告id，删除个人诊断报告
        for (Long id : ids) {
            Long reportId = baseDao.getReportIdByScoreId(id);
            // 根据报告id删除报告记录
            LambdaQueryWrapper<WuyuAnalysisResultEntity> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(WuyuAnalysisResultEntity::getId, reportId);
            wuyuAnalysisResultDao.delete(wrapper);
        }

        super.delete(ids);
    }

    // 计算综合成绩和等级，新增和修改都用到，所以抽出来
    private WuyuScoreDTO calComprehensiveScoreAndLevel(WuyuScoreDTO dto) {
        UserDetail user = SecurityUser.getUser();
        if (user.getSuperAdmin() == SuperAdminEnum.NO.value()) {
            // 如果用户不是超级管理员，按照用户的学校id填入，不花费额外时间查询学生的学校id
            dto.setSchoolId(user.getSchoolId());
        } else {
            // 花费点时间查询学生的学校id
            SysUserDTO student = sysUserService.getByUsername(dto.getStudentNo());
            dto.setSchoolId(student.getSchoolId()); //专门给超级管理员留的后门，主要是测试用
        }

        Map<String, Object> params = new HashMap<>();
        params.put("schoolId", dto.getSchoolId());

        WuyuWeightDTO wuyuWeightDTO = wuyuWeightService.getBySchoolId(params);
        // 填充综合成绩，综合等级
        dto.setComprehensiveScore(calComprehensiveScore(dto, wuyuWeightDTO));
        dto.setComprehensiveLevel(calAcademicLevel(dto.getComprehensiveScore()));

        return dto;
    }

    // 计算综合等级
    private Integer calAcademicLevel(Integer score) {
        if (score >= 80) {
            return 0; //优
        } else if (score >= 60) {
            return 1; //中
        } else {
            return 2; //差
        }
    }

    // 计算五育综合成绩
    private Integer calComprehensiveScore(WuyuScoreDTO scoreDto, WuyuWeightDTO weightDto) {

        // 德育
        Integer moral = (int) (scoreDto.getCharacterEthics() * weightDto.getCharacterEthics()
                        + scoreDto.getRewardsPunishments() * weightDto.getRewardsPunishments()
                        + scoreDto.getMoralEducationCourses() * weightDto.getMoralEducationCourses()
                        + scoreDto.getPracticalActivities() * weightDto.getPracticalActivities()
                        + scoreDto.getOnlineCulture() * weightDto.getOnlineCulture()
                        + scoreDto.getInterpersonalRelationships() * weightDto.getInterpersonalRelationships());
        // 智育
        Integer intellectual = (int) (scoreDto.getPrepManagement() * weightDto.getPrepManagement()
                        + scoreDto.getPlanManagement() * weightDto.getPlanManagement()
                        + scoreDto.getClassroomBehavior() * weightDto.getClassroomBehavior()
                        + scoreDto.getClassroomAttendance() * weightDto.getClassroomAttendance()
                        + scoreDto.getHomeworkManagement() * weightDto.getHomeworkManagement()
                        + scoreDto.getReviewManagement() * weightDto.getReviewManagement()
                        + scoreDto.getPersonalAbilities() * weightDto.getPersonalAbilities()
                        + scoreDto.getAcademicPerformance() * weightDto.getAcademicPerformance()
                        + scoreDto.getExperimentalCompetitions() * weightDto.getExperimentalCompetitions());

        // 体育
        Integer physical = (int) (scoreDto.getExaminationMetrics() * weightDto.getExaminationMetrics()
                        + scoreDto.getPhysicalFitnessScores() * weightDto.getPhysicalFitnessScores()
                        + scoreDto.getSportingSpecialties() * weightDto.getSportingSpecialties()
                        + scoreDto.getHealthyLiving() * weightDto.getHealthyLiving()
                        + scoreDto.getMentalQualities() * weightDto.getMentalQualities()
                        + scoreDto.getPhysicalEducationCourses() * weightDto.getPhysicalEducationCourses());

        // 美育
        Integer artistic = (int) (scoreDto.getArtsCourses() * weightDto.getArtsCourses()
                        + scoreDto.getArtsAchievements() * weightDto.getArtsAchievements()
                        + scoreDto.getArtsActivities() * weightDto.getArtsActivities());

        // 劳育
        Integer labor = (int) (scoreDto.getLaborPractices() * weightDto.getLaborPractices()
                        + scoreDto.getLaborCourses() * weightDto.getLaborCourses());

        return (int) (moral * weightDto.getMoral() + intellectual * weightDto.getIntellectual()
                        + physical * weightDto.getPhysical() + artistic * weightDto.getArtistic()
                        + labor * weightDto.getLabor());

    }

    @Override
    public WuyuAnalysisResultDTO genOrGetAnalysisReport(Long id) {

        WuyuAnalysisResultDTO reportDto = new WuyuAnalysisResultDTO();

        // 根据五育分数id查询 五育诊断报告
        WuyuAnalysisResultEntity reportEntity = getReportByScoreId(id);
        if (reportEntity == null) {
            // 如果不存在报告，那么生成
            // 先根据五育分数id 获取五育分数
            WuyuScoreDTO wuyuScoreDTO = get(id);
            // 生成报告
            reportDto = genAnalysisReport(wuyuScoreDTO);

            // 报告保存到数据库
            reportEntity = new WuyuAnalysisResultEntity();
            BeanUtils.copyProperties(reportDto, reportEntity);
            // 填充其他字段
            reportEntity.setScoreId(id);
            reportEntity.setStudentNo(wuyuScoreDTO.getStudentNo());
            reportEntity.setStudentName(wuyuScoreDTO.getStudentName());

            wuyuAnalysisResultDao.insert(reportEntity);

            // 返回的dto也填充剩余字段
            reportDto.setScoreId(id);
            reportDto.setStudentNo(wuyuScoreDTO.getStudentNo());
            reportDto.setStudentName(wuyuScoreDTO.getStudentName());
        } else if (reportEntity.getResponse() == null) {  // 或 报告的内容为空，那么重新生成（修改而不是插入）
            // 代理对象
            WuyuScoreServiceImpl currentProxy = (WuyuScoreServiceImpl)AopContext.currentProxy();
            currentProxy.reGenAnalysisReport(id);
            // 再次查询，填充报告内容
            reportEntity = getReportByScoreId(id);
            BeanUtils.copyProperties(reportEntity, reportDto);
        } else {
            // 如果存在直接返回
            BeanUtils.copyProperties(reportEntity, reportDto);
        }
        // 返回
        return reportDto;

    }

    //根据五育分数id 查询 五育诊断报告
    private WuyuAnalysisResultEntity getReportByScoreId(Long id) {
        LambdaQueryWrapper<WuyuAnalysisResultEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(WuyuAnalysisResultEntity::getScoreId, id);
        return wuyuAnalysisResultDao.selectOne(lqw);
    }

    // 根据 五育分数 id 重新生成 个人诊断报告
    @Override
    @Transactional
    public void reGenAnalysisReport(Long id) {
        WuyuAnalysisResultEntity reportEntity = getReportByScoreId(id);
        if (reportEntity == null) { //如果不存在，直接调用 生成函数
            genOrGetAnalysisReport(id);
        } else {
            // 如果存在，才重新生成
            // 查询 五育分数对象
            WuyuScoreDTO scoreDto = get(id);
            // 更新 报告内容
            reportEntity.setResponse(genAnalysisReport(scoreDto).getResponse());
            // 更新 自动根据报告id更新
            wuyuAnalysisResultDao.update(reportEntity, new LambdaQueryWrapper<WuyuAnalysisResultEntity>()
                    .eq(WuyuAnalysisResultEntity::getId, reportEntity.getId())
            );
        }
    }

    // 根据五育分数 生成诊断报告
    private WuyuAnalysisResultDTO genAnalysisReport(WuyuScoreDTO dto) {

        Map<String, String> requestBody = new HashMap<>();
        StringBuilder evaluatePrompt = new StringBuilder("该生 类型#个人评价*");
        StringBuilder suggestPrompt = new StringBuilder("该生 类型#个人建议*");

        String prompt = evaluatePrompt.append(createPrompt(dto))
                .append("，输出评价或建议不少于200字")
                .toString();
        System.out.println("========================================" + prompt);
        requestBody.put("prompt", prompt);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(requestBody, headers);

        try {
            WuyuAnalysisResultDTO reportDto = restTemplate.postForObject(ANALYSIS_URL, httpEntity, WuyuAnalysisResultDTO.class);
            assert reportDto != null;
            System.out.println("============" + reportDto.getResponse());
            return reportDto;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RenException("生成报告失败，请联系管理员。");
        }

    }

    // 根据五育分数生成 prompt
    private String createPrompt(WuyuScoreDTO dto) {
        String evaluatePrompt = "*类型#个人评价*";
        String suggestPrompt = "*类型#个人建议*";

        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("主题#品德评定*等级#").append(getGrade(dto.getCharacterEthics())).append(evaluatePrompt)
                .append("主题#奖惩记录*等级#").append(getGrade(dto.getRewardsPunishments())).append(evaluatePrompt)
                .append("主题#德育课程*等级#").append(getGrade(dto.getMoralEducationCourses())).append(evaluatePrompt)
                .append("主题#实践活动*等级#").append(getGrade(dto.getPracticalActivities())).append(evaluatePrompt)
                .append("主题#网络文化*等级#").append(getGrade(dto.getOnlineCulture())).append(evaluatePrompt)
                .append("主题#人际关系*等级#").append(getGrade(dto.getInterpersonalRelationships())).append(evaluatePrompt)
                .append("主题#预习管理*等级#").append(getGrade(dto.getPrepManagement())).append(evaluatePrompt)
                .append("主题#计划管理*等级#").append(getGrade(dto.getPlanManagement())).append(evaluatePrompt)
                .append("主题#课堂行为*等级#").append(getGrade(dto.getClassroomBehavior())).append(evaluatePrompt)
                .append("主题#课堂考勤*等级#").append(getGrade(dto.getClassroomAttendance())).append(evaluatePrompt)
                .append("主题#作业管理*等级#").append(getGrade(dto.getHomeworkManagement())).append(evaluatePrompt)
                .append("主题#复习管理*等级#").append(getGrade(dto.getReviewManagement())).append(evaluatePrompt)
                .append("主题#个人能力*等级#").append(getGrade(dto.getPersonalAbilities())).append(evaluatePrompt)
                .append("主题#学业成绩*等级#").append(getGrade(dto.getAcademicPerformance())).append(evaluatePrompt)
                .append("主题#实验与竞赛*等级#").append(getGrade(dto.getExperimentalCompetitions())).append(evaluatePrompt)
                .append("主题#体检指标*等级#").append(getGrade(dto.getExaminationMetrics())).append(evaluatePrompt)
                .append("主题#体能成绩*等级#").append(getGrade(dto.getPhysicalFitnessScores())).append(evaluatePrompt)
                .append("主题#体育特长*等级#").append(getGrade(dto.getSportingSpecialties())).append(evaluatePrompt)
                .append("主题#健康生活*等级#").append(getGrade(dto.getHealthyLiving())).append(evaluatePrompt)
                .append("主题#心理素质*等级#").append(getGrade(dto.getMentalQualities())).append(evaluatePrompt)
                .append("主题#体育课程*等级#").append(getGrade(dto.getPhysicalEducationCourses())).append(evaluatePrompt)
                .append("主题#美育课程*等级#").append(getGrade(dto.getArtsCourses())).append(evaluatePrompt)
                .append("主题#美育成果*等级#").append(getGrade(dto.getArtsAchievements())).append(evaluatePrompt)
                .append("主题#美育活动*等级#").append(getGrade(dto.getArtsActivities())).append(evaluatePrompt)
                .append("主题#劳动实践*等级#").append(getGrade(dto.getLaborPractices())).append(evaluatePrompt)
                .append("主题#劳动课程*等级#").append(getGrade(dto.getLaborCourses()));
        return promptBuilder.toString();
    }

    private String getGrade(Integer score) {
        if (score >= 80) {
            return "优";
        } else if (score >= 60){
            return "中";
        } else {
            return "差";
        }
    }

    @Override
    public Map<String, Object> readExcel(MultipartFile file) throws IOException {
        // 创建一个用于存储读取结果的 Map
        Map<String, Object> resultMap = new HashMap<>();

        // 获取Excel文件的输入流
        InputStream inputStream = file.getInputStream();

        // 读取Excel文件内容
        List<WuyuScoreImportExcel> wuyuScoreExcelList =
                ExcelUtils.readExcel(inputStream, WuyuScoreImportExcel.class, new WuyuScoreReadListener());
        /**
        // 处理成功的记录数
        int success = 0;
        // 处理失败的记录数
        int failed = 0;
        // 处理数据
        for (WuyuScoreImportExcel scoreExcel : wuyuScoreExcelList) {
            // 如果当前行记录的学生学号为空，那么直接跳过
            // 检查当前行记录的学生是否存在于系统，不存在则直接跳过
            if (scoreExcel.getStudentNo() == null || sysUserService.getByUsername(scoreExcel.getStudentNo()) == null) {
                failed++;
                continue; // 如果不存在，那么直接跳过
            }
            WuyuScoreDTO scoreDTO = ConvertUtils.sourceToTarget(scoreExcel, WuyuScoreDTO.class);
            // 用代理对象执行事务方法，防止事务失效
            try {
                scoreServiceProxy.save(scoreDTO);
            } catch (Exception e) {
                failed++;
                // 捕到异常不管，继续执行
                continue;
            }
            // 成功处理记录数加1
            success++;
        }*/
        // 处理成功的记录数 AtomicInteger的incrementAndGet是线程安全的++
        AtomicInteger success = new AtomicInteger(0);
        // 处理失败的记录数
        AtomicInteger failed = new AtomicInteger(0);
        // 用并行流来过滤无效的记录, 并行流默认使用公共的 ForkJoinPool，默认线程数量是处理器核心数量
        List<WuyuScoreDTO> validScores = wuyuScoreExcelList.parallelStream()
                .filter(scoreExcel -> {
                    if (scoreExcel.getStudentNo() == null || sysUserService.getByUsername(scoreExcel.getStudentNo()) == null) {
                        // 处理失败记录数+1
                        failed.incrementAndGet();
                        return false; // 校验失败，跳过
                    }
                    return true;
                })
                .map(scoreExcel -> ConvertUtils.sourceToTarget(scoreExcel, WuyuScoreDTO.class))
                .collect(Collectors.toList());
        // 代理对象
        WuyuScoreServiceImpl currentProxy = (WuyuScoreServiceImpl)AopContext.currentProxy();
        // 有效的记录，直接用并行流进行存储
        validScores.parallelStream().forEach(scoreDTO -> {
            try {
                // 用代理对象执行事务方法，防止事务失效
                currentProxy.save(scoreDTO);
                // 处理成功
                success.incrementAndGet();
            } catch (Exception e) {
                // 处理失败的行数加1
                failed.incrementAndGet();
                // 捕到异常不管，继续执行
            }
        });

        // 将结果添加到map中
        resultMap.put("failedNum", failed);
        resultMap.put("successNum", success);


        return resultMap;
    }
}
