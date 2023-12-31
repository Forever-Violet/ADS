package io.ads.modules.analysis.controller;

import io.ads.common.annotation.LogOperation;
import io.ads.common.constant.Constant;
import io.ads.common.page.PageData;
import io.ads.common.utils.ExcelUtils;
import io.ads.common.utils.Result;
import io.ads.common.utils.ValidDtoUtils;
import io.ads.common.validator.AssertUtils;
import io.ads.common.validator.ValidatorUtils;
import io.ads.common.validator.group.AddGroup;
import io.ads.common.validator.group.DefaultGroup;
import io.ads.common.validator.group.UpdateGroup;
import io.ads.modules.analysis.dto.WuyuWeightDTO;
import io.ads.modules.analysis.excel.WuyuWeightExcel;
import io.ads.modules.analysis.service.WuyuWeightService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;


/**
 * 五育设置（五育权重表）
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-03
 */
@RestController
@RequestMapping("analysis/wuyuweight")
@Api(tags="五育设置（五育权重表）")
public class WuyuWeightController {
    @Autowired
    private WuyuWeightService wuyuWeightService;

/*    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
        @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
    })
    @RequiresPermissions("analysis:wuyuweight:page")
    public Result<PageData<WuyuWeightDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<WuyuWeightDTO> page = wuyuWeightService.page(params);

        return new Result<PageData<WuyuWeightDTO>>().ok(page);
    }*/

/*    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("analysis:wuyuweight:info")
    public Result<WuyuWeightDTO> get(@PathVariable("id") Long id){
        WuyuWeightDTO data = wuyuWeightService.get(id);

        return new Result<WuyuWeightDTO>().ok(data);
    }*/

    @GetMapping("info")
    @ApiOperation("根据学校id获取信息")
    @RequiresPermissions("analysis:wuyuweight:info")
    public Result<WuyuWeightDTO> getBySchoolId(@ApiIgnore @RequestParam Map<String, Object> params){
        return new Result<WuyuWeightDTO>().ok(wuyuWeightService.getBySchoolId(params));
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("analysis:wuyuweight:save")
    public Result save(@Valid @RequestBody WuyuWeightDTO dto, BindingResult result){
        ValidDtoUtils.throwValidateException(result);
        //效验数据
        //ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        wuyuWeightService.save(dto);

        return new Result();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("analysis:wuyuweight:update")
    public Result update(@Valid @RequestBody WuyuWeightDTO dto, BindingResult result){
        ValidDtoUtils.throwValidateException(result);
        //效验数据
        //ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        wuyuWeightService.update(dto);

        return new Result();
    }

/*
    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("analysis:wuyuweight:delete")
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        wuyuWeightService.delete(ids);

        return new Result();
    }
*/

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("analysis:wuyuweight:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<WuyuWeightDTO> list = wuyuWeightService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, "五育设置（五育权重表）", list, WuyuWeightExcel.class);
    }

}
