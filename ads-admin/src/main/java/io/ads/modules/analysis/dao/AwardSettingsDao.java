package io.ads.modules.analysis.dao;

import io.ads.common.dao.BaseDao;
import io.ads.modules.analysis.entity.AwardSettingsEntity;
import io.ads.modules.sys.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 奖项设置
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-03
 */
@Mapper
public interface AwardSettingsDao extends BaseDao<AwardSettingsEntity> {

    List<AwardSettingsEntity> getList(Map<String, Object> params);

}
