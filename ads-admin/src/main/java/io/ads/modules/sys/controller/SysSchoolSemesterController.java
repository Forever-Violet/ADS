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
import io.ads.modules.sys.dto.SysSchoolGradeDTO;
import io.ads.modules.sys.dto.SysSchoolSemesterDTO;
import io.ads.modules.sys.excel.SysSchoolSemesterExcel;
import io.ads.modules.sys.service.SysSchoolSemesterService;
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
 * 学期管理
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-12-01
 */
@RestController
@RequestMapping("sys/semester")
@Api(tags="学期管理")
public class SysSchoolSemesterController {
    @Autowired
    private SysSchoolSemesterService sysSchoolSemesterService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
        @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
    })
    @RequiresPermissions("sys:semester:page")
    public Result<PageData<SysSchoolSemesterDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<SysSchoolSemesterDTO> page = sysSchoolSemesterService.page(params);

        return new Result<PageData<SysSchoolSemesterDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("sys:semester:info")
    public Result<SysSchoolSemesterDTO> get(@PathVariable("id") Long id){
        SysSchoolSemesterDTO data = sysSchoolSemesterService.get(id);

        return new Result<SysSchoolSemesterDTO>().ok(data);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("sys:semester:save")
    public Result save(@RequestBody SysSchoolSemesterDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        sysSchoolSemesterService.save(dto);

        return new Result();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("sys:semester:update")
    public Result update(@RequestBody SysSchoolSemesterDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        sysSchoolSemesterService.update(dto);

        return new Result();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("sys:semester:delete")
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        sysSchoolSemesterService.delete(ids);

        return new Result();
    }

    @GetMapping("list")
    @ApiOperation("获取年级列表")
    @RequiresPermissions("sys:grade:list")
    public Result<List<SysSchoolSemesterDTO>> list(@ApiIgnore @RequestParam Map<String, Object> params){
        List<SysSchoolSemesterDTO> list = sysSchoolSemesterService.list(params);

        return new Result<List<SysSchoolSemesterDTO>>().ok(list);
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("sys:semester:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<SysSchoolSemesterDTO> list = sysSchoolSemesterService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, "学期管理", list, SysSchoolSemesterExcel.class);
    }

}
