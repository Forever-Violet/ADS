package io.ads.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.ads.common.exception.RenException;
import io.ads.common.service.impl.CrudServiceImpl;
import io.ads.common.utils.ConvertUtils;
import io.ads.modules.sys.dao.SysSchoolDao;
import io.ads.modules.sys.dao.SysSchoolGradeDao;
import io.ads.modules.sys.dao.SysSchoolSemesterDao;
import io.ads.modules.sys.dto.SysSchoolDTO;
import io.ads.modules.sys.entity.SysSchoolEntity;
import io.ads.modules.sys.entity.SysSchoolGradeEntity;
import io.ads.modules.sys.entity.SysSchoolSemesterEntity;
import io.ads.modules.sys.service.SysSchoolGradeService;
import io.ads.modules.sys.service.SysSchoolService;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 学校管理
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2023-10-28
 */
@Service
public class SysSchoolServiceImpl extends CrudServiceImpl<SysSchoolDao, SysSchoolEntity, SysSchoolDTO> implements SysSchoolService {

    @Resource
    SysSchoolDao sysSchoolDao;

    @Resource
    SysSchoolGradeDao sysSchoolGradeDao;
    @Resource
    SysSchoolSemesterDao sysSchoolSemesterDao;

    @Override
    public QueryWrapper<SysSchoolEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");
        String schoolName = (String)params.get("schoolName");
        String unitType = (String)params.get("unitType");

        QueryWrapper<SysSchoolEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id)
                .like(StrUtil.isNotBlank(schoolName), "school_name", schoolName) //模糊
                .or()
                .like(StrUtil.isNotBlank(schoolName), "full_name", schoolName)
                .or()
                .like(StrUtil.isNotBlank(schoolName), "alias", schoolName)
                .eq(StrUtil.isNotBlank(unitType), "unit_type", unitType);


        return wrapper;
    }


    @Override
    public SysSchoolDTO get(Long id) {
        //超级管理员，学校ID为null
        if (id == null) {
            return null;
        }

        SysSchoolEntity entity = baseDao.getById(id);

        return ConvertUtils.sourceToTarget(entity, SysSchoolDTO.class);
    }

    @Override
    public void update(SysSchoolDTO dto) {
        SysSchoolEntity entity = ConvertUtils.sourceToTarget(dto, SysSchoolEntity.class);

        baseDao.updateById(entity);
    }

    @Override
    public void delete(Long[] ids) {
        for (Long schoolId : ids) {
            QueryWrapper<SysSchoolGradeEntity> qw1 = new QueryWrapper<>();
            QueryWrapper<SysSchoolSemesterEntity> qw2 = new QueryWrapper<>();
            // 限制数量1
            qw1.eq("school_id", schoolId)
                    .last("LIMIT 1");
            qw2.eq("school_id", schoolId)
                    .last("LIMIT 1");
            if (sysSchoolGradeDao.selectOne(qw1) != null ) {
                throw new RenException("学校下有年级，无法删除，请先删除年级信息");
            }
            if (sysSchoolSemesterDao.selectOne(qw2) != null ) {
                throw new RenException("学校下有学期信息，无法删除，请先删除学期信息");
            }
        }
        super.delete(ids);
    }
}
