package io.ads.modules.analysis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.ads.common.service.impl.CrudServiceImpl;
import io.ads.modules.analysis.dao.WuyuScoreDao;
import io.ads.modules.analysis.dto.WuyuScoreDTO;
import io.ads.modules.analysis.entity.WuyuScoreEntity;
import io.ads.modules.analysis.service.WuyuScoreService;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 五育分析（五育成绩表）
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-03
 */
@Service
public class WuyuScoreServiceImpl extends CrudServiceImpl<WuyuScoreDao, WuyuScoreEntity, WuyuScoreDTO> implements WuyuScoreService {

    @Override
    public QueryWrapper<WuyuScoreEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<WuyuScoreEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


}