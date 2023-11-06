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
import io.ads.modules.analysis.dto.WuyuScoreDTO;
import io.ads.modules.analysis.excel.WuyuScoreExcel;
import io.ads.modules.analysis.service.WuyuScoreService;
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
 * 五育分析（五育成绩表）
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-03
 */
@RestController
@RequestMapping("analysis/wuyuscore")
@Api(tags="五育分析（五育成绩表）")
public class WuyuScoreController {
    @Autowired
    private WuyuScoreService wuyuScoreService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
        @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
    })
    @RequiresPermissions("analysis:wuyuscore:page")
    public Result<PageData<WuyuScoreDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<WuyuScoreDTO> page = wuyuScoreService.page(params);

        return new Result<PageData<WuyuScoreDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("analysis:wuyuscore:info")
    public Result<WuyuScoreDTO> get(@PathVariable("id") Long id){
        WuyuScoreDTO data = wuyuScoreService.get(id);

        return new Result<WuyuScoreDTO>().ok(data);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("analysis:wuyuscore:save")
    public Result save(@RequestBody WuyuScoreDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        wuyuScoreService.save(dto);

        return new Result();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("analysis:wuyuscore:update")
    public Result update(@RequestBody WuyuScoreDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        wuyuScoreService.update(dto);

        return new Result();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("analysis:wuyuscore:delete")
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        wuyuScoreService.delete(ids);

        return new Result();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("analysis:wuyuscore:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<WuyuScoreDTO> list = wuyuScoreService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, "五育分析（五育成绩表）", list, WuyuScoreExcel.class);
    }

}