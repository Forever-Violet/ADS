package io.ads.modules.analysis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.ads.common.service.impl.CrudServiceImpl;
import io.ads.modules.analysis.dao.WuyuAnalysisResultDao;
import io.ads.modules.analysis.dto.WuyuAnalysisResultDTO;
import io.ads.modules.analysis.entity.WuyuAnalysisResultEntity;
import io.ads.modules.analysis.service.WuyuAnalysisResultService;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 五育分析 结果（报告）表
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-25
 */
@Service
public class WuyuAnalysisResultServiceImpl extends CrudServiceImpl<WuyuAnalysisResultDao, WuyuAnalysisResultEntity, WuyuAnalysisResultDTO> implements WuyuAnalysisResultService {

    @Override
    public QueryWrapper<WuyuAnalysisResultEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<WuyuAnalysisResultEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


}