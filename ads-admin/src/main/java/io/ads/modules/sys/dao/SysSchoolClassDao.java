package io.ads.modules.sys.dao;

import io.ads.common.dao.BaseDao;
import io.ads.modules.sys.entity.SysSchoolClassEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 班级管理
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-29
 */
@Mapper
public interface SysSchoolClassDao extends BaseDao<SysSchoolClassEntity> {

    List<SysSchoolClassEntity> getList(Map<String, Object> params);

    /**
     * 根据学号获取班级id
     */
    Long getClassIdByStudentNo(String studentNo);

    /**
     * 根据班级id获取学校id
     * @param classId 班级id
     * @return 学校id
     */
    Long getSchoolIdByClassId(Long classId);
}
