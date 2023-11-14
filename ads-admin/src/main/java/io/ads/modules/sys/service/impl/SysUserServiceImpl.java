/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.ads.modules.sys.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.ads.common.constant.Constant;
import io.ads.common.page.PageData;
import io.ads.common.service.impl.BaseServiceImpl;
import io.ads.common.utils.ConvertUtils;
import io.ads.modules.security.password.PasswordUtils;
import io.ads.modules.security.user.SecurityUser;
import io.ads.modules.security.user.UserDetail;
import io.ads.modules.sys.dao.SysUserDao;
import io.ads.modules.sys.dto.SysUserDTO;
import io.ads.modules.sys.entity.SysUserEntity;
import io.ads.modules.sys.enums.SuperAdminEnum;
import io.ads.modules.sys.service.SysSchoolService;

import io.ads.modules.sys.service.SysRoleUserService;
import io.ads.modules.sys.service.SysUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;


/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service
@AllArgsConstructor
public class SysUserServiceImpl extends BaseServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
    private final SysRoleUserService sysRoleUserService;
    private final SysSchoolService sysSchoolService;


    @Override
    public PageData<SysUserDTO> page(Map<String, Object> params) {
        //转换成like
        paramsToLike(params, "username");

        //分页
        IPage<SysUserEntity> page = getPage(params, Constant.CREATE_DATE, false);


        return new PageData<>(getList(params), page.getTotal());
        //return getPageData(dtoList, page.getTotal(), SysUserDTO.class);
    }

    private List<SysUserDTO> getList(Map<String, Object> params) {
        //普通管理员，只能查询所属学校的用户
        UserDetail user = SecurityUser.getUser();
        if (user.getSuperAdmin() == SuperAdminEnum.NO.value()) {
            String schoolId = user.getSchoolId().toString();
            params.put("schoolId", schoolId);
        }

        List<SysUserEntity> list = baseDao.getList(params);
        List<SysUserDTO> dtoList = new ArrayList<>(list.size());

        list.forEach(entity -> {
            SysUserDTO sysUserDTO = new SysUserDTO();
            BeanUtils.copyProperties(entity, sysUserDTO);
            List<Long> roleIdList = sysRoleUserService.getRoleIdList(entity.getId());
            // 填充角色id列表
            sysUserDTO.setRoleIdList(roleIdList);
            // 填充角色名称列表
            sysUserDTO.setRoleNameList(sysRoleUserService.getRoleNameList(roleIdList));
            dtoList.add(sysUserDTO);
        });
        return dtoList;
    }

    @Override
    public List<SysUserDTO> list(Map<String, Object> params) {
        //普通管理员，只能查询所属学校的数据
        return getList(params);
        //return ConvertUtils.sourceToTarget(entityList, SysUserDTO.class);
    }



    @Override
    public SysUserDTO get(Long id) {
        SysUserEntity entity = baseDao.getById(id);

        return ConvertUtils.sourceToTarget(entity, SysUserDTO.class);
    }

    @Override
    public SysUserDTO getByUsername(String username) {
        SysUserEntity entity = baseDao.getByUsername(username);
        return ConvertUtils.sourceToTarget(entity, SysUserDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysUserDTO dto) {
        SysUserEntity entity = ConvertUtils.sourceToTarget(dto, SysUserEntity.class);

        //密码加密
        String password = PasswordUtils.encode(entity.getPassword());
        entity.setPassword(password);

        //保存用户
        entity.setSuperAdmin(SuperAdminEnum.NO.value());

        //普通管理员，只能插入其所属学校的用户
        if (entity.getSchoolId() == null) { //这里只是做个校验，因为前端已经把schoolId填进去了，后端再进行一次校验
            UserDetail user = SecurityUser.getUser();
            if (user.getSuperAdmin() == SuperAdminEnum.NO.value()) { //如果用户不是superAdmin
                entity.setSchoolId(user.getSchoolId());
            }
        }
        insert(entity);

        //保存角色用户关系
        sysRoleUserService.saveOrUpdate(entity.getId(), dto.getRoleIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysUserDTO dto) {
        SysUserEntity entity = ConvertUtils.sourceToTarget(dto, SysUserEntity.class);

        //密码加密
        if (StrUtil.isBlank(dto.getPassword())) {
            entity.setPassword(null);
        } else {
            String password = PasswordUtils.encode(entity.getPassword());
            entity.setPassword(password);
        }

        //更新用户
        updateById(entity);

        //更新角色用户关系
        sysRoleUserService.saveOrUpdate(entity.getId(), dto.getRoleIdList());
    }

    @Override
    public void delete(Long[] ids) {
        //删除用户
        baseDao.deleteBatchIds(Arrays.asList(ids));

        //删除角色用户关系
        sysRoleUserService.deleteByUserIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(Long id, String newPassword) {
        newPassword = PasswordUtils.encode(newPassword);

        baseDao.updatePassword(id, newPassword);
    }

    @Override
    public int getCountBySchoolId(Long schoolId) {
        return baseDao.getCountBySchoolId(schoolId);
    }

    @Override
    public List<Long> getUserIdListBySchoolId(List<Long> schoolIdList) {
        return baseDao.getUserIdListBySchoolId(schoolIdList);
    }

    @Override
    public List<SysUserDTO> getStudentList() {
        Map<String, Object> params = new HashMap<>();
        UserDetail user = SecurityUser.getUser();
        if (user.getSuperAdmin() == SuperAdminEnum.NO.value()) {
            // 如果用户非超级管理员，只能查询其学校的学生列表
            String schoolId = user.getSchoolId().toString();
            params.put("schoolId", schoolId);
        }
        return baseDao.getStudentList(params);
    }
}
