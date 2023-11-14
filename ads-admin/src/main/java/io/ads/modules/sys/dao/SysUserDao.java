/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.ads.modules.sys.dao;

import io.ads.common.dao.BaseDao;
import io.ads.modules.sys.dto.SysUserDTO;
import io.ads.modules.sys.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
public interface SysUserDao extends BaseDao<SysUserEntity> {

	List<SysUserEntity> getList(Map<String, Object> params);

	SysUserEntity getById(Long id);

	SysUserEntity getByUsername(String username);

	int updatePassword(@Param("id") Long id, @Param("newPassword") String newPassword);

	/**
	 * 根据学校ID，查询用户数
	 */
	int getCountBySchoolId(Long schoolId);

	/**
	 * 根据学校ID,查询学校ID列表
	 */
	List<Long> getUserIdListBySchoolId(List<Long> schoolIdList);

	/**
	 * 获取学生列表
	 */
    List<SysUserDTO> getStudentList(Map<String, Object> params);
}
