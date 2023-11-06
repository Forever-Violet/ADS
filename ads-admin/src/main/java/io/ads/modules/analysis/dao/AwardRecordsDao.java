package io.ads.modules.analysis.dao;

import io.ads.common.dao.BaseDao;
import io.ads.modules.analysis.entity.AwardRecordsEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 获奖记录设置
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-03
 */
@Mapper
public interface AwardRecordsDao extends BaseDao<AwardRecordsEntity> {
	
}