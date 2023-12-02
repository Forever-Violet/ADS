package io.ads.modules.sys.service;

import io.ads.common.service.CrudService;
import io.ads.modules.sys.dto.SysSchoolGradeDTO;
import io.ads.modules.sys.entity.SysSchoolGradeEntity;

import java.util.List;

/**
 * 年级管理
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-29
 */
public interface SysSchoolGradeService extends CrudService<SysSchoolGradeEntity, SysSchoolGradeDTO> {

    /**
     * 根据学号查询年级id列表
     */
    List<Long> getGradeIdListByStudentNo(String studentNo);
}
