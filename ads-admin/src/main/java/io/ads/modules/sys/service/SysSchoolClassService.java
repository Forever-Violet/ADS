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

    /**
     * 根据班级id获取学校id
     * @param classId 班级id
     */
    Long getSchoolIdByClassId(Long classId);

    /**
     * 根据班级id获取班级名称
     * @param classId 班级id
     * @return 班级名称
     */
    String getClassNameByClassId(Long classId);

    /**
     * 根据班级id获取年级名称
     * @param classId 班级id
     * @return 年级名称
     */
    String getGradeNameByClassId(Long classId);
}
