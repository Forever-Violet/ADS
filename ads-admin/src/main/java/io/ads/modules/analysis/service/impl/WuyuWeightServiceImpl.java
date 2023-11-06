package io.ads.modules.analysis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.ads.common.service.impl.CrudServiceImpl;
import io.ads.modules.analysis.dao.WuyuWeightDao;
import io.ads.modules.analysis.dto.WuyuWeightDTO;
import io.ads.modules.analysis.entity.WuyuWeightEntity;
import io.ads.modules.analysis.service.WuyuWeightService;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 五育设置（五育权重表）
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-03
 */
@Service
public class WuyuWeightServiceImpl extends CrudServiceImpl<WuyuWeightDao, WuyuWeightEntity, WuyuWeightDTO> implements WuyuWeightService {

    @Override
    public QueryWrapper<WuyuWeightEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<WuyuWeightEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


}