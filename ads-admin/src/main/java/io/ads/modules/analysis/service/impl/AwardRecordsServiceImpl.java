package io.ads.modules.analysis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.ads.common.constant.Constant;
import io.ads.common.page.PageData;
import io.ads.common.service.impl.CrudServiceImpl;
import io.ads.common.utils.ConvertUtils;
import io.ads.modules.analysis.dao.AwardRecordsDao;
import io.ads.modules.analysis.dto.AwardRecordsDTO;
import io.ads.modules.analysis.dto.AwardSettingsDTO;
import io.ads.modules.analysis.entity.AwardRecordsEntity;
import io.ads.modules.analysis.entity.AwardSettingsEntity;
import io.ads.modules.analysis.service.AwardRecordsService;
import cn.hutool.core.util.StrUtil;
import io.ads.modules.security.user.SecurityUser;
import io.ads.modules.security.user.UserDetail;
import io.ads.modules.sys.dto.SysUserDTO;
import io.ads.modules.sys.entity.SysUserEntity;
import io.ads.modules.sys.enums.SuperAdminEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 获奖记录设置
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-03
 */
@Service
public class AwardRecordsServiceImpl extends CrudServiceImpl<AwardRecordsDao, AwardRecordsEntity, AwardRecordsDTO> implements AwardRecordsService {

    @Override
    public QueryWrapper<AwardRecordsEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<AwardRecordsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Override
    public PageData<AwardRecordsDTO> page(Map<String, Object> params) {
        //转换成like
        paramsToLike(params, "studentNo");
        //分页 由于底层的sql将两个表赋别名t1, t2，直接使用"create_date"会造成歧义，这里直接指定t1
        IPage<AwardRecordsEntity> page = getPage(params, "t1.create_date", false);

        return new PageData<>(getList(params), page.getTotal());
    }

    private List<AwardRecordsDTO> getList(Map<String, Object> params) {
        //普通管理员，只能查询所属学校的用户
        UserDetail user = SecurityUser.getUser();
        if (user.getSuperAdmin() == SuperAdminEnum.NO.value()) {
            String schoolId = user.getSchoolId().toString();
            params.put("schoolId", schoolId);
        }

        List<AwardRecordsEntity> list = baseDao.getList(params);
        List<AwardRecordsDTO> dtolist = new ArrayList<>(list.size());

        list.forEach(entity -> {
            AwardRecordsDTO dto = new AwardRecordsDTO();
            BeanUtils.copyProperties(entity, dto);
            dtolist.add(dto);
        });

        return dtolist;
    }

    @Override
    public List<AwardRecordsDTO> list(Map<String, Object> params) {
        //普通管理员，只能查询所属学校的数据
        return getList(params);
    }

    // 重写getById方法，用于填充额外字段回显到修改页面
    @Override
    public AwardRecordsDTO get(Long id) {
        AwardRecordsEntity entity = baseDao.getById(id);

        return ConvertUtils.sourceToTarget(entity, AwardRecordsDTO.class);
    }



}
