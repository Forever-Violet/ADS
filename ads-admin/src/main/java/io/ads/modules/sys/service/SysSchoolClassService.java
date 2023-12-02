package io.ads.modules.sys.service;

import io.ads.common.service.CrudService;
import io.ads.modules.sys.dto.SysSchoolClassDTO;
import io.ads.modules.sys.dto.SysUserDTO;
import io.ads.modules.sys.entity.SysSchoolClassEntity;
import org.springframework.transaction.annotation.Transactional;

/**
 * 班级管理
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-29
 */
public interface SysSchoolClassService extends CrudService<SysSchoolClassEntity, SysSchoolClassDTO> {

    /**
     * 根据学号获取班级id
     */
    Long getClassIdByStudentNo(String studentNo);
}
