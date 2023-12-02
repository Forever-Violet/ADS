package io.ads.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.ads.common.constant.Constant;
import io.ads.common.page.PageData;
import io.ads.common.service.impl.CrudServiceImpl;
import io.ads.common.utils.ConvertUtils;
import io.ads.modules.security.password.PasswordUtils;
import io.ads.modules.security.user.SecurityUser;
import io.ads.modules.security.user.UserDetail;
import io.ads.modules.sys.dao.SysSchoolClassDao;
import io.ads.modules.sys.dto.SysSchoolClassDTO;
import io.ads.modules.sys.dto.SysSchoolClassDTO;
import io.ads.modules.sys.entity.SysSchoolClassEntity;
import io.ads.modules.sys.entity.SysSchoolClassEntity;
import io.ads.modules.sys.enums.SuperAdminEnum;
import io.ads.modules.sys.service.SysSchoolClassService;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 班级管理
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-29
 */
@Service
public class SysSchoolClassServiceImpl extends CrudServiceImpl<SysSchoolClassDao, SysSchoolClassEntity, SysSchoolClassDTO> implements SysSchoolClassService {

    @Override
    public QueryWrapper<SysSchoolClassEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");
        String schoolId = (String)params.get("schoolId");
        String gradeId = (String)params.get("gradeId");
        QueryWrapper<SysSchoolClassEntity> wrapper = new QueryWrapper<>();

        // 普通用户或普通管理员，在这里填充其学校id
        UserDetail user = SecurityUser.getUser();
        if (user.getSuperAdmin() == SuperAdminEnum.NO.value()) {
            schoolId = user.getSchoolId().toString();
            params.put("schoolId", schoolId);
        }

        wrapper.eq(StrUtil.isNotBlank(id), "id", id)
                .eq(StrUtil.isNotBlank(schoolId), "school_id", schoolId)
                .eq(StrUtil.isNotBlank(gradeId), "grade_id", gradeId);

        return wrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysSchoolClassDTO dto) {
        SysSchoolClassEntity entity = ConvertUtils.sourceToTarget(dto, SysSchoolClassEntity.class);

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
    public PageData<SysSchoolClassDTO> page(Map<String, Object> params) {

        //分页
        IPage<SysSchoolClassEntity> page = getPage(params, Constant.CREATE_DATE, false);


        return new PageData<>(getList(params), page.getTotal());

    }

    private List<SysSchoolClassDTO> getList(Map<String, Object> params) {
        //普通管理员，只能查询所属学校的用户
        UserDetail user = SecurityUser.getUser();
        if (user.getSuperAdmin() == SuperAdminEnum.NO.value()) {
            String schoolId = user.getSchoolId().toString();
            params.put("schoolId", schoolId);
        }

        List<SysSchoolClassEntity> list = baseDao.getList(params);
        List<SysSchoolClassDTO> dtoList = new ArrayList<>(list.size());

        list.forEach(entity -> {
            SysSchoolClassDTO sysUserDTO = new SysSchoolClassDTO();
            BeanUtils.copyProperties(entity, sysUserDTO);
            dtoList.add(sysUserDTO);
        });
        return dtoList;
    }

    @Override
    public List<SysSchoolClassDTO> list(Map<String, Object> params) {
        //普通管理员，只能查询所属学校的数据
        return getList(params);

    }

    @Override
    public Long getClassIdByStudentNo(String studentNo) {
        return baseDao.getClassIdByStudentNo(studentNo);
    }
}
