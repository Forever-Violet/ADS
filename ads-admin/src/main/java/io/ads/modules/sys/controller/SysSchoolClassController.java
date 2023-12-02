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
import io.ads.modules.sys.dto.SysSchoolClassDTO;
import io.ads.modules.sys.dto.SysSchoolGradeDTO;
import io.ads.modules.sys.excel.SysSchoolClassExcel;
import io.ads.modules.sys.service.SysSchoolClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * 班级管理
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-29
 */
@RestController
@RequestMapping("sys/class")
@Api(tags="班级管理")
public class SysSchoolClassController {
    @Autowired
    private SysSchoolClassService sysSchoolClassService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
    })
    @RequiresPermissions("sys:class:page")
    public Result<PageData<SysSchoolClassDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<SysSchoolClassDTO> page = sysSchoolClassService.page(params);

        return new Result<PageData<SysSchoolClassDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("sys:class:info")
    public Result<SysSchoolClassDTO> get(@PathVariable("id") Long id){
        SysSchoolClassDTO data = sysSchoolClassService.get(id);

        return new Result<SysSchoolClassDTO>().ok(data);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("sys:class:save")
    public Result save(@RequestBody SysSchoolClassDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        sysSchoolClassService.save(dto);

        return new Result();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("sys:class:update")
    public Result update(@RequestBody SysSchoolClassDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        sysSchoolClassService.update(dto);

        return new Result();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("sys:class:delete")
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        sysSchoolClassService.delete(ids);

        return new Result();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("sys:class:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<SysSchoolClassDTO> list = sysSchoolClassService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, "班级管理", list, SysSchoolClassExcel.class);
    }

    @GetMapping("list")
    @ApiOperation("获取班级列表")
    @RequiresPermissions("sys:grade:list")
    public Result<List<SysSchoolClassDTO>> list(@ApiIgnore @RequestParam Map<String, Object> params){
        List<SysSchoolClassDTO> list = sysSchoolClassService.list(params);

        return new Result<List<SysSchoolClassDTO>>().ok(list);
    }

}
