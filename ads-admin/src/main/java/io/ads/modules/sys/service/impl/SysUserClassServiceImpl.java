package io.ads.modules.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.ads.common.service.impl.BaseServiceImpl;
import io.ads.common.service.impl.CrudServiceImpl;
import io.ads.modules.sys.dao.SysUserClassDao;
import io.ads.modules.sys.dto.SysUserClassDTO;
import io.ads.modules.sys.entity.SysUserClassEntity;
import io.ads.modules.sys.service.SysUserClassService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 班级管理
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-29
 */
@Service
public class SysUserClassServiceImpl extends BaseServiceImpl<SysUserClassDao, SysUserClassEntity> implements SysUserClassService {

    @Override
    @Transactional
    public void saveOrUpdate(Long userId, List<Long> classIdList) {
        //先删除班级用户关系
        deleteByUserIds(new Long[]{userId});

        //用户没有一个角色权限的情况
        if(CollUtil.isEmpty(classIdList)){
            return ;
        }

        //保存班级用户关系
        for(Long classId : classIdList){
            SysUserClassEntity sysUserClassEntity = new SysUserClassEntity();
            sysUserClassEntity.setUserId(userId);
            sysUserClassEntity.setClassId(classId);

            //保存
            insert(sysUserClassEntity);
        }
    }

    @Override
    public void deleteByClassIds(Long[] classIds) {
        baseDao.deleteByClassIds(classIds);
    }

    @Override
    public void deleteByUserIds(Long[] userIds) {
        baseDao.deleteByUserIds(userIds);
    }

    @Override
    public List<Long> getClassIdList(Long userId) {

        return baseDao.getClassIdList(userId);
    }

    @Override
    public List<String> getClassNameList(List<Long> classIdList) {
        List<String> classNameList = new ArrayList<>(classIdList.size());
        for(Long classId : classIdList){
            // 组装班级名称， 年级 - 班级
            String className = baseDao.getGradeNameById(classId) +
                    "-" +
                    baseDao.getClassNameById(classId);
/*            // 查询班级名称
            String className = baseDao.getClassNameById(classId);
            // 查询班级的年级名称
            String gradeName = baseDao.getGradeNameById(classId);*/
            classNameList.add(className);
        }
        return classNameList;
    }

}
