package io.ads.modules.analysis.service;

import io.ads.common.service.CrudService;
import io.ads.modules.analysis.dto.AwardSettingsDTO;
import io.ads.modules.analysis.entity.AwardSettingsEntity;
import io.ads.modules.sys.dto.SysUserDTO;
import org.springframework.transaction.annotation.Transactional;

/**
 * 奖项设置
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-03
 */
public interface AwardSettingsService extends CrudService<AwardSettingsEntity, AwardSettingsDTO> {

    void save(AwardSettingsDTO dto);
}
