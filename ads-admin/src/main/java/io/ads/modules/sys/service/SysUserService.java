/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.ads.modules.sys.service;

import io.ads.common.page.PageData;
import io.ads.common.service.BaseService;
import io.ads.modules.sys.dto.SysUserDTO;
import io.ads.modules.sys.entity.SysUserEntity;

import java.util.List;
import java.util.Map;


/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface SysUserService extends BaseService<SysUserEntity> {

	PageData<SysUserDTO> page(Map<String, Object> params);

	List<SysUserDTO> list(Map<String, Object> params);

	SysUserDTO get(Long id);

	SysUserDTO getByUsername(String username);

	void save(SysUserDTO dto);

	void update(SysUserDTO dto);

	void delete(Long[] ids);

	/**
	 * 修改密码
	 * @param id           用户ID
	 * @param newPassword  新密码
	 */
	void updatePassword(Long id, String newPassword);

	/**
	 * 根据学校ID，查询用户数
	 */
	int getCountBySchoolId(Long schoolId);

	/**
	 * 根据学校ID,查询用户Id列表
	 */
	List<Long> getUserIdListBySchoolId(List<Long> schoolIdList);

	/**
	 * 获取学生列表，通过联动角色、用户角色表，且用户只能查询其学校的学生
	 */
	List<SysUserDTO> getStudentList(Map<String, Object> params);
}
