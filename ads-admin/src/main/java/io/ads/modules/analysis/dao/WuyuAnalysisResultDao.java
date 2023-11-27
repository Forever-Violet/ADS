package io.ads.modules.analysis.dao;

import io.ads.common.dao.BaseDao;
import io.ads.modules.analysis.entity.WuyuAnalysisResultEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 五育分析 结果（报告）表
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-25
 */
@Mapper
public interface WuyuAnalysisResultDao extends BaseDao<WuyuAnalysisResultEntity> {
	
}