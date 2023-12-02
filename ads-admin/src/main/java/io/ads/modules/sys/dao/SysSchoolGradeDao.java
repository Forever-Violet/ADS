package io.ads.modules.sys.dao;

import io.ads.common.dao.BaseDao;
import io.ads.modules.sys.entity.SysSchoolGradeEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 年级管理
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-29
 */
@Mapper
public interface SysSchoolGradeDao extends BaseDao<SysSchoolGradeEntity> {

    /**
     * 根据学号查询年级id列表
     */
    List<Long> getGradeIdByStudentNo(String studentNo);
}
