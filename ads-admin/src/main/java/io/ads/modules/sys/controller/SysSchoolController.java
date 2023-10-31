package io.ads.modules.sys.controller;

import io.ads.common.annotation.LogOperation;
import io.ads.common.constant.Constant;
import io.ads.common.page.PageData;
import io.ads.common.utils.ExcelUtils;
import io.ads.common.utils.Result;
import io.ads.common.validator.AssertUtils;
import io.ads.common.validator.ValidatorUtils;
import io.ads.common.validator.group.AddGroup;
import io.ads.common.validator.group.DefaultGroup;
import io.ads.common.validator.group.UpdateGroup;

import io.ads.modules.sys.dto.SysSchoolDTO;
import io.ads.modules.sys.entity.SysSchoolEntity;
import io.ads.modules.sys.excel.SysSchoolExcel;
import io.ads.modules.sys.service.SysSchoolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 学校管理
 *
 * @author 12508
 * @since 1.0.0 2023-10-28
 */
@RestController
@RequestMapping("sys/school")
@Api(tags="学校管理")
public class SysSchoolController {
    @Autowired
    private SysSchoolService sysSchoolService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
        @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
    })
    @RequiresPermissions("sys:school:page")
    public Result<PageData<SysSchoolDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<SysSchoolDTO> page = sysSchoolService.page(params);

        return new Result<PageData<SysSchoolDTO>>().ok(page);
    }

    @GetMapping("list")
    @ApiOperation("获取学校列表")
    @RequiresPermissions("sys:school:list")
    public Result<List<SysSchoolDTO>> list(){
        List<SysSchoolDTO> list = sysSchoolService.list(new HashMap<>(1));

        return new Result<List<SysSchoolDTO>>().ok(list);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("sys:school:info")
    public Result<SysSchoolDTO> get(@PathVariable("id") Long id){
        SysSchoolDTO data = sysSchoolService.get(id);

        return new Result<SysSchoolDTO>().ok(data);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("sys:school:save")
    public Result save(@RequestBody SysSchoolDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        sysSchoolService.save(dto);

        return new Result();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("sys:school:update")
    public Result update(@RequestBody SysSchoolDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        sysSchoolService.update(dto);

        return new Result();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("sys:school:delete")
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        sysSchoolService.delete(ids);

        return new Result();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("sys:school:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<SysSchoolDTO> list = sysSchoolService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, "学校管理", list, SysSchoolExcel.class);
    }

}
