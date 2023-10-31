package io.ads.modules.sys.service;

import io.ads.common.service.CrudService;
import io.ads.modules.sys.dto.SysSchoolDTO;
import io.ads.modules.sys.entity.SysSchoolEntity;

/**
 * 学校管理
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2023-10-28
 */
public interface SysSchoolService extends CrudService<SysSchoolEntity, SysSchoolDTO> {

    SysSchoolDTO get(Long id);

    void update(SysSchoolDTO dto);

}
