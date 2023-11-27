package io.ads.modules.analysis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.ads.common.exception.RenException;
import io.ads.common.service.impl.CrudServiceImpl;
import io.ads.modules.analysis.dao.WuyuWeightDao;
import io.ads.modules.analysis.dto.WuyuWeightDTO;
import io.ads.modules.analysis.entity.WuyuWeightEntity;
import io.ads.modules.analysis.service.WuyuWeightService;
import cn.hutool.core.util.StrUtil;
import io.ads.modules.security.user.SecurityUser;
import io.ads.modules.security.user.UserDetail;
import io.ads.modules.sys.enums.SuperAdminEnum;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 五育设置（五育权重表）
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-03
 */
@Service
public class WuyuWeightServiceImpl extends CrudServiceImpl<WuyuWeightDao, WuyuWeightEntity, WuyuWeightDTO> implements WuyuWeightService {

    private static final float EPSILON = 0.0001f; //float运算可接受的最大误差

    @Override
    public QueryWrapper<WuyuWeightEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<WuyuWeightEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Override
    public WuyuWeightDTO getBySchoolId(Map<String, Object> params) {
        String schoolId = params.get("schoolId").toString();
        LambdaQueryWrapper<WuyuWeightEntity> lqw = new LambdaQueryWrapper<>();

        if (schoolId == null || "".equals(schoolId)) { //非管理员用户查询，或超级管理员未选中学校, 这里得加上判断空字符串，不然转换类型会报错
            UserDetail user = SecurityUser.getUser();
            if (user.getSuperAdmin() == SuperAdminEnum.NO.value()) { //如果不是当前用户不是超级管理员
                schoolId = user.getSchoolId().toString(); //填充学校id
            }
        }
        // 填充查询条件
        lqw.eq(WuyuWeightEntity::getSchoolId, schoolId);
        // 查询
        WuyuWeightEntity entity = baseDao.selectOne(lqw);

        if (entity == null) {
            if (schoolId != null && !"".equals(schoolId)) { //如果学校id不为空, 才插入该学校的初始权重数据
                insertInitialWeightSettings(Long.parseLong(schoolId));
            }
            entity = baseDao.selectOne(lqw);
        }

        WuyuWeightDTO dto = new WuyuWeightDTO();
        if (entity != null) {
            BeanUtils.copyProperties(entity, dto);
        }

        return dto;
    }


    @Override
    public void update(WuyuWeightDTO dto) {

        // 校验权重总和
        validateWeightSum(dto);

        super.update(dto);
    }

    private void validateWeightSum(WuyuWeightDTO dto) {
        // 因为float运算会出现误差，所以设定一个可接受的最大误差，只要误差小于这个值，就认为等于1，如果大于，就认为不等于1，那么抛出异常
        if ( Math.abs((dto.getMoral() + dto.getIntellectual() + dto.getPhysical() + dto.getArtistic() + dto.getLabor()) - 1.0f) > EPSILON) {
            throw new RenException("修改失败，一级权重总和必须为1");
        } else if (Math.abs((dto.getCharacterEthics() + dto.getRewardsPunishments() + dto.getMoralEducationCourses()
                + dto.getPracticalActivities() + dto.getOnlineCulture() + dto.getInterpersonalRelationships()) - 1.0f) > EPSILON) {
            System.out.println(dto.getCharacterEthics() + dto.getRewardsPunishments() + dto.getMoralEducationCourses()
                    + dto.getPracticalActivities() + dto.getOnlineCulture() + dto.getInterpersonalRelationships());
            throw new RenException("修改失败，德育二级权重总和必须为1");
        } else if (Math.abs((dto.getPrepManagement() + dto.getPlanManagement() + dto.getClassroomBehavior()
                + dto.getClassroomAttendance() + dto.getHomeworkManagement() + dto.getReviewManagement()
                + dto.getPersonalAbilities() + dto.getAcademicPerformance() + dto.getExperimentalCompetitions()) - 1.0f) > EPSILON) {
            throw new RenException("修改失败，智育二级权重总和必须为1");
        } else if (Math.abs((dto.getExaminationMetrics() + dto.getPhysicalFitnessScores() + dto.getSportingSpecialties()
                + dto.getHealthyLiving() + dto.getMentalQualities() + dto.getPhysicalEducationCourses()) - 1.0f) > EPSILON) {
            throw new RenException("修改失败，体育二级权重总和必须为1");
        } else if (Math.abs((dto.getArtsCourses() + dto.getArtsAchievements() + dto.getArtsActivities()) - 1.0f) > EPSILON) {
            throw new RenException("修改失败，美育二级权重总和必须为1");
        } else if (Math.abs((dto.getLaborPractices() + dto.getLaborCourses()) - 1.0f) > EPSILON) {
            throw new RenException("修改失败，劳育二级权重总和必须为1");
        }
    }

    private void insertInitialWeightSettings(Long schoolId) {
        WuyuWeightEntity entity = new WuyuWeightEntity();
        entity.setSchoolId(schoolId);

        entity.setMoral(0.20F);
        entity.setIntellectual(0.40F);
        entity.setPhysical(0.20F);
        entity.setArtistic(0.10F);
        entity.setLabor(0.10F);

        entity.setCharacterEthics(0.20F);
        entity.setRewardsPunishments(0.15F);
        entity.setMoralEducationCourses(0.30F);
        entity.setPracticalActivities(0.15F);
        entity.setOnlineCulture(0.10F);
        entity.setInterpersonalRelationships(0.10F);

        entity.setPrepManagement(0.05F);
        entity.setPlanManagement(0.05F);
        entity.setClassroomBehavior(0.15F);
        entity.setClassroomAttendance(0.05F);
        entity.setHomeworkManagement(0.10F);
        entity.setReviewManagement(0.10F);
        entity.setPersonalAbilities(0.15F);
        entity.setAcademicPerformance(0.25F);
        entity.setExperimentalCompetitions(0.10F);

        entity.setExaminationMetrics(0.15F);
        entity.setPhysicalFitnessScores(0.15F);
        entity.setSportingSpecialties(0.10F);
        entity.setHealthyLiving(0.10F);
        entity.setMentalQualities(0.10F);
        entity.setPhysicalEducationCourses(0.40F);

        entity.setArtsCourses(0.50F);
        entity.setArtsAchievements(0.25F);
        entity.setArtsActivities(0.25F);

        entity.setLaborPractices(0.50F);
        entity.setLaborCourses(0.50F);

        insert(entity);


    }
}
