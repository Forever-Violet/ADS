package io.ads.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.ads.common.service.impl.CrudServiceImpl;
import io.ads.common.utils.ConvertUtils;
import io.ads.modules.security.user.SecurityUser;
import io.ads.modules.security.user.UserDetail;
import io.ads.modules.sys.dao.SysSchoolGradeDao;
import io.ads.modules.sys.dto.SysSchoolClassDTO;
import io.ads.modules.sys.dto.SysSchoolGradeDTO;
import io.ads.modules.sys.entity.SysSchoolClassEntity;
import io.ads.modules.sys.entity.SysSchoolGradeEntity;
import io.ads.modules.sys.enums.SuperAdminEnum;
import io.ads.modules.sys.service.SysSchoolGradeService;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 年级管理
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-29
 */
@Service
public class SysSchoolGradeServiceImpl extends CrudServiceImpl<SysSchoolGradeDao, SysSchoolGradeEntity, SysSchoolGradeDTO> implements SysSchoolGradeService {

    @Override
    public QueryWrapper<SysSchoolGradeEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");
        String schoolId = (String)params.get("schoolId");

        // 普通用户或普通管理员，在这里填充其学校id
        UserDetail user = SecurityUser.getUser();
        if (user.getSuperAdmin() == SuperAdminEnum.NO.value()) {
            schoolId = user.getSchoolId().toString();
            params.put("schoolId", schoolId);
        }

        QueryWrapper<SysSchoolGradeEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id)
                .eq(StrUtil.isNotBlank(schoolId), "school_id", schoolId);

        return wrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysSchoolGradeDTO dto) {
        SysSchoolGradeEntity entity = ConvertUtils.sourceToTarget(dto, SysSchoolGradeEntity.class);

        //普通管理员，只能插入其所属学校的用户
        if (entity.getSchoolId() == null) { //非超级管理员只能后端填入学校id
            UserDetail user = SecurityUser.getUser();
            if (user.getSuperAdmin() == SuperAdminEnum.NO.value()) { //如果用户不是superAdmin
                entity.setSchoolId(user.getSchoolId());
            }
        }
        insert(entity);

    }

    @Override
    public List<Long> getGradeIdListByStudentNo(String studentNo) {
        return baseDao.getGradeIdByStudentNo(studentNo);
    }
}
