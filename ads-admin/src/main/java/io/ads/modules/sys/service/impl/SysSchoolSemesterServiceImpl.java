package io.ads.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.ads.common.service.impl.CrudServiceImpl;
import io.ads.common.utils.ConvertUtils;
import io.ads.modules.security.user.SecurityUser;
import io.ads.modules.security.user.UserDetail;
import io.ads.modules.sys.dao.SysSchoolSemesterDao;
import io.ads.modules.sys.dto.SysSchoolGradeDTO;
import io.ads.modules.sys.dto.SysSchoolSemesterDTO;
import io.ads.modules.sys.entity.SysSchoolGradeEntity;
import io.ads.modules.sys.entity.SysSchoolSemesterEntity;
import io.ads.modules.sys.enums.SuperAdminEnum;
import io.ads.modules.sys.service.SysSchoolSemesterService;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 学期管理
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-12-01
 */
@Service
public class SysSchoolSemesterServiceImpl extends CrudServiceImpl<SysSchoolSemesterDao, SysSchoolSemesterEntity, SysSchoolSemesterDTO> implements SysSchoolSemesterService {

    @Override
    public QueryWrapper<SysSchoolSemesterEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");
        String schoolId = (String)params.get("schoolId");

        // 普通用户或普通管理员，在这里填充其学校id
        UserDetail user = SecurityUser.getUser();
        if (user.getSuperAdmin() == SuperAdminEnum.NO.value()) {
            schoolId = user.getSchoolId().toString();
            params.put("schoolId", schoolId);
        }
        QueryWrapper<SysSchoolSemesterEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id)
                .eq(StrUtil.isNotBlank(schoolId), "school_id", schoolId)
                .orderByDesc("end_date"); // 根据学期结束时间降序排序

        return wrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysSchoolSemesterDTO dto) {
        SysSchoolSemesterEntity entity = ConvertUtils.sourceToTarget(dto, SysSchoolSemesterEntity.class);

        //普通管理员，只能插入其所属学校的用户
        if (entity.getSchoolId() == null) { //非超级管理员只能后端填入学校id
            UserDetail user = SecurityUser.getUser();
            if (user.getSuperAdmin() == SuperAdminEnum.NO.value()) { //如果用户不是superAdmin
                entity.setSchoolId(user.getSchoolId());
            }
        }
        insert(entity);

    }

    // 根据学校id查询最新的学期
    @Override
    public Long getLatestSemesterId(Long schoolId) {
        LambdaQueryWrapper<SysSchoolSemesterEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysSchoolSemesterEntity::getSchoolId, schoolId) // 根据学校 ID 查询
                .orderByDesc(SysSchoolSemesterEntity::getEndDate) // 根据学期结束时间降序排序
                .last("LIMIT 1"); // 获取第一个结果

        SysSchoolSemesterEntity latestSemester = baseDao.selectOne(queryWrapper);

        // 如果找到了符合条件的最新学期，返回其 ID，否则返回 null
        return latestSemester != null ? latestSemester.getId() : null;
    }
}
