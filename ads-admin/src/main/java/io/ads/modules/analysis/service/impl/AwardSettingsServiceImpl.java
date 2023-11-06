package io.ads.modules.analysis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.ads.common.service.impl.CrudServiceImpl;
import io.ads.modules.analysis.dao.AwardSettingsDao;
import io.ads.modules.analysis.dto.AwardSettingsDTO;
import io.ads.modules.analysis.entity.AwardSettingsEntity;
import io.ads.modules.analysis.service.AwardSettingsService;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 奖项设置
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-03
 */
@Service
public class AwardSettingsServiceImpl extends CrudServiceImpl<AwardSettingsDao, AwardSettingsEntity, AwardSettingsDTO> implements AwardSettingsService {

    @Override
    public QueryWrapper<AwardSettingsEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<AwardSettingsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


}