package io.ads.modules.analysis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.ads.common.service.impl.CrudServiceImpl;
import io.ads.modules.analysis.dao.AwardRecordsDao;
import io.ads.modules.analysis.dto.AwardRecordsDTO;
import io.ads.modules.analysis.entity.AwardRecordsEntity;
import io.ads.modules.analysis.service.AwardRecordsService;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 获奖记录设置
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-03
 */
@Service
public class AwardRecordsServiceImpl extends CrudServiceImpl<AwardRecordsDao, AwardRecordsEntity, AwardRecordsDTO> implements AwardRecordsService {

    @Override
    public QueryWrapper<AwardRecordsEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<AwardRecordsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


}