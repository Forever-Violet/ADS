package io.ads.modules.analysis.controller;

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
import io.ads.modules.analysis.dto.AwardSettingsDTO;
import io.ads.modules.analysis.excel.AwardSettingsExcel;
import io.ads.modules.analysis.service.AwardSettingsService;
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
 * 奖项设置
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-03
 */
@RestController
@RequestMapping("analysis/awardsettings")
@Api(tags="奖项设置")
public class AwardSettingsController {
    @Autowired
    private AwardSettingsService awardSettingsService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
        @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
    })
    @RequiresPermissions("analysis:awardsettings:page")
    public Result<PageData<AwardSettingsDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<AwardSettingsDTO> page = awardSettingsService.page(params);

        return new Result<PageData<AwardSettingsDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("analysis:awardsettings:info")
    public Result<AwardSettingsDTO> get(@PathVariable("id") Long id){
        AwardSettingsDTO data = awardSettingsService.get(id);

        return new Result<AwardSettingsDTO>().ok(data);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("analysis:awardsettings:save")
    public Result save(@RequestBody AwardSettingsDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        awardSettingsService.save(dto);

        return new Result();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("analysis:awardsettings:update")
    public Result update(@RequestBody AwardSettingsDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        awardSettingsService.update(dto);

        return new Result();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("analysis:awardsettings:delete")
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        awardSettingsService.delete(ids);

        return new Result();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("analysis:awardsettings:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<AwardSettingsDTO> list = awardSettingsService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, "奖项设置", list, AwardSettingsExcel.class);
    }

}