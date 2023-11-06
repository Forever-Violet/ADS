package io.ads.modules.analysis.dao;

import io.ads.common.dao.BaseDao;
import io.ads.modules.analysis.entity.WuyuScoreEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 五育分析（五育成绩表）
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-03
 */
@Mapper
public interface WuyuScoreDao extends BaseDao<WuyuScoreEntity> {
	
}