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
import io.ads.modules.sys.dto.SysSchoolGradeDTO;
import io.ads.modules.sys.excel.SysSchoolGradeExcel;
import io.ads.modules.sys.service.SysSchoolGradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 年级管理
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-29
 */
@RestController
@RequestMapping("sys/grade")
@Api(tags="年级管理")
public class SysSchoolGradeController {
    @Autowired
    private SysSchoolGradeService sysSchoolGradeService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
        @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
    })
    @RequiresPermissions("sys:grade:page")
    public Result<PageData<SysSchoolGradeDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<SysSchoolGradeDTO> page = sysSchoolGradeService.page(params);

        return new Result<PageData<SysSchoolGradeDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("sys:grade:info")
    public Result<SysSchoolGradeDTO> get(@PathVariable("id") Long id){
        SysSchoolGradeDTO data = sysSchoolGradeService.get(id);

        return new Result<SysSchoolGradeDTO>().ok(data);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("sys:grade:save")
    public Result save(@RequestBody SysSchoolGradeDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        sysSchoolGradeService.save(dto);

        return new Result();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("sys:grade:update")
    public Result update(@RequestBody SysSchoolGradeDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        sysSchoolGradeService.update(dto);

        return new Result();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("sys:grade:delete")
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        sysSchoolGradeService.delete(ids);

        return new Result();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("sys:grade:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<SysSchoolGradeDTO> list = sysSchoolGradeService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, "年级管理", list, SysSchoolGradeExcel.class);
    }

    @GetMapping("list")
    @ApiOperation("获取年级列表")
    @RequiresPermissions("sys:grade:list")
    public Result<List<SysSchoolGradeDTO>> list(@ApiIgnore @RequestParam Map<String, Object> params){
        List<SysSchoolGradeDTO> list = sysSchoolGradeService.list(params);

        return new Result<List<SysSchoolGradeDTO>>().ok(list);
    }


}
