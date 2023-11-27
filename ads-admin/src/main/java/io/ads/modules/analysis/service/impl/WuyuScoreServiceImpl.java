package io.ads.modules.analysis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mchange.lang.IntegerUtils;
import io.ads.common.exception.RenException;
import io.ads.common.service.impl.CrudServiceImpl;
import io.ads.modules.analysis.dao.WuyuAnalysisResultDao;
import io.ads.modules.analysis.dao.WuyuScoreDao;
import io.ads.modules.analysis.dto.WuyuAnalysisResultDTO;
import io.ads.modules.analysis.dto.WuyuScoreDTO;
import io.ads.modules.analysis.dto.WuyuWeightDTO;
import io.ads.modules.analysis.entity.WuyuAnalysisResultEntity;
import io.ads.modules.analysis.entity.WuyuScoreEntity;
import io.ads.modules.analysis.entity.WuyuWeightEntity;
import io.ads.modules.analysis.service.WuyuAnalysisResultService;
import io.ads.modules.analysis.service.WuyuScoreService;
import cn.hutool.core.util.StrUtil;
import io.ads.modules.analysis.service.WuyuWeightService;
import io.ads.modules.security.user.SecurityUser;
import io.ads.modules.security.user.UserDetail;
import io.ads.modules.sys.dto.SysUserDTO;
import io.ads.modules.sys.enums.SuperAdminEnum;
import io.ads.modules.sys.service.SysUserService;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

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
    RestTemplate restTemplate;

    private final String ANALYSIS_URL = "https://chatglm2.tocmcc.cn:443";


    @Override
    public QueryWrapper<WuyuScoreEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");
        String schoolId = (String)params.get("schoolId");
        String comprehensiveLevel = (String)params.get("comprehensiveLevel");
        String studentNameOrNo = (String)params.get("studentNameOrNo");

        QueryWrapper<WuyuScoreEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id)
                .eq(StrUtil.isNotBlank(schoolId), "school_id", schoolId)
                .eq(StrUtil.isNotBlank(comprehensiveLevel), "comprehensive_level", comprehensiveLevel)
                .like(StrUtil.isNotBlank(studentNameOrNo), "student_name", studentNameOrNo)
                .or()
                .like(StrUtil.isNotBlank(studentNameOrNo), "student_no", studentNameOrNo);

        return wrapper;
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(WuyuScoreDTO dto) {

        dto = calComprehensiveScoreAndLevel(dto);
        // todo 学业等级怎么确定待商榷，先固定
        dto.setAcademicLevel(1);

        super.save(dto);
    }

    @Override
    public void update(WuyuScoreDTO dto) {
        super.update(calComprehensiveScoreAndLevel(dto));
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

        // 根据五育分数id查询 五育诊断报告是否已存在
        LambdaQueryWrapper<WuyuAnalysisResultEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(WuyuAnalysisResultEntity::getScoreId, id);
        WuyuAnalysisResultEntity reportEntity = wuyuAnalysisResultDao.selectOne(lqw);
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
        } else {
            // 如果存在直接返回
            BeanUtils.copyProperties(reportEntity, reportDto);
        }
        // 返回
        return reportDto;

    }


    // 根据 五育分数 id 重新生成 个人诊断报告
    @Override
    public void reGenAnalysisReport(Long id) {
        // 根据五育分数id 查询 五育诊断报告是否已存在
        LambdaQueryWrapper<WuyuAnalysisResultEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(WuyuAnalysisResultEntity::getScoreId, id);
        WuyuAnalysisResultEntity reportEntity = wuyuAnalysisResultDao.selectOne(lqw);
        if (reportEntity == null) { //如果不存在，直接调用 生成函数

            genOrGetAnalysisReport(id);

        } else {
            // 如果存在，才重新生成
            // 查询 五育分数对象
            WuyuScoreDTO scoreDto = get(id);
            // 更新 报告内容
            reportEntity.setResponse(genAnalysisReport(scoreDto).getResponse());
            // 更新 自动根据报告id更新
            wuyuAnalysisResultDao.update(reportEntity, new LambdaQueryWrapper<>());
        }
    }

    // 根据五育分数 生成诊断报告
    private WuyuAnalysisResultDTO genAnalysisReport(WuyuScoreDTO dto) {

        Map<String, String> requestBody = new HashMap<>();
        StringBuilder evaluatePrompt = new StringBuilder("该生 类型#个人评价*");
        StringBuilder suggestPrompt = new StringBuilder("该生 类型#个人建议*");

        String prompt = evaluatePrompt.append(createPrompt(dto)).toString();
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




}
