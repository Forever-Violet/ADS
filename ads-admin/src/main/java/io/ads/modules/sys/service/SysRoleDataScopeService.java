/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.ads.modules.sys.service;

import io.ads.common.service.BaseService;
import io.ads.modules.sys.entity.SysRoleDataScopeEntity;

import java.util.List;

/**
 * 角色数据权限
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0
 */
public interface SysRoleDataScopeService extends BaseService<SysRoleDataScopeEntity> {

    /**
     * 根据角色ID，获取学校ID列表
     */
    List<Long> getSchoolIdList(Long roleId);

    /**
     * 保存或修改
     * @param roleId      角色ID
     * @param schoolIdList  部门ID列表
     */
    void saveOrUpdate(Long roleId, List<Long> schoolIdList);

    /**
     * 根据角色id，删除角色数据权限关系
     * @param roleId 角色ids
     */
    void deleteByRoleIds(Long[] roleId);
}
