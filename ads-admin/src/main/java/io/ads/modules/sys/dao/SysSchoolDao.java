package io.ads.modules.sys.dao;

import io.ads.common.dao.BaseDao;

import io.ads.modules.sys.entity.SysSchoolEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 学校管理
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2023-10-28
 */
@Mapper
public interface SysSchoolDao extends BaseDao<SysSchoolEntity> {


    SysSchoolEntity getById(Long id);


}
