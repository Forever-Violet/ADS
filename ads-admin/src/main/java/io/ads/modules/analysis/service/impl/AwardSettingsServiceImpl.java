package io.ads.modules.analysis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qiniu.util.StringUtils;
import io.ads.common.constant.Constant;
import io.ads.common.page.PageData;
import io.ads.common.service.impl.CrudServiceImpl;
import io.ads.common.utils.ConvertUtils;
import io.ads.modules.analysis.dao.AwardSettingsDao;
import io.ads.modules.analysis.dto.AwardSettingsDTO;
import io.ads.modules.analysis.entity.AwardSettingsEntity;
import io.ads.modules.analysis.service.AwardSettingsService;
import cn.hutool.core.util.StrUtil;
import io.ads.modules.security.password.PasswordUtils;
import io.ads.modules.security.user.SecurityUser;
import io.ads.modules.security.user.UserDetail;
import io.ads.modules.sys.dto.SysUserDTO;
import io.ads.modules.sys.entity.SysUserEntity;
import io.ads.modules.sys.enums.SuperAdminEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 奖项设置
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-03
 */
@Service
public class AwardSettingsServiceImpl extends CrudServiceImpl<AwardSettingsDao, AwardSettingsEntity, AwardSettingsDTO> implements AwardSettingsService {

    @Override
    public QueryWrapper<AwardSettingsEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<AwardSettingsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }
    @Override
    public PageData<AwardSettingsDTO> page(Map<String, Object> params) {


        //分页
        IPage<AwardSettingsEntity> page = getPage(params, Constant.CREATE_DATE, false);

        return new PageData<>(getList(params), page.getTotal());
    }

    private List<AwardSettingsDTO> getList(Map<String, Object> params) {
        //普通管理员，只能查询所属学校的用户
        UserDetail user = SecurityUser.getUser();
        if (user.getSuperAdmin() == SuperAdminEnum.NO.value()) {
            String schoolId = user.getSchoolId().toString();
            params.put("schoolId", schoolId);
        }

        List<AwardSettingsEntity> list = baseDao.getList(params);
        List<AwardSettingsDTO> dtolist = new ArrayList<>(list.size());

        list.forEach(entity -> {
            AwardSettingsDTO dto = new AwardSettingsDTO();
            BeanUtils.copyProperties(entity, dto);
            dtolist.add(dto);
        });

        return dtolist;
    }

    @Override
    public List<AwardSettingsDTO> list(Map<String, Object> params) {
        //普通管理员，只能查询所属学校的数据
        return getList(params);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(AwardSettingsDTO dto) {
        AwardSettingsEntity entity = ConvertUtils.sourceToTarget(dto, AwardSettingsEntity.class);

        UserDetail user = SecurityUser.getUser();
        if (user.getSuperAdmin() == SuperAdminEnum.NO.value()) {
            // 如果不是超级管理员，按照用户的学校id填入
            entity.setSchoolId(user.getSchoolId());
        } else {
            entity.setSchoolId(0L); //专门给超级管理员留的后门，主要是测试用
        }

        insert(entity);

    }


}
