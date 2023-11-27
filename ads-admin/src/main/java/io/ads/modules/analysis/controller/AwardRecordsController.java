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
import io.ads.modules.analysis.dto.AwardRecordsDTO;
import io.ads.modules.analysis.excel.AwardRecordsExcel;
import io.ads.modules.analysis.service.AwardRecordsService;
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
 * 获奖记录设置
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-03
 */
@RestController
@RequestMapping("analysis/awardrecords")
@Api(tags="获奖记录设置")
public class AwardRecordsController {
    @Autowired
    private AwardRecordsService awardRecordsService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
        @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
    })
    @RequiresPermissions("analysis:awardrecords:page")
    public Result<PageData<AwardRecordsDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<AwardRecordsDTO> page = awardRecordsService.page(params);

        return new Result<PageData<AwardRecordsDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("analysis:awardrecords:info")
    public Result<AwardRecordsDTO> get(@PathVariable("id") Long id){
        AwardRecordsDTO data = awardRecordsService.get(id);

        return new Result<AwardRecordsDTO>().ok(data);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("analysis:awardrecords:save")
    public Result save(@Valid @RequestBody AwardRecordsDTO dto, BindingResult result){ //@Valid开启校验
        ValidDtoUtils.throwValidateException(result);
        //效验数据
        //ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        awardRecordsService.save(dto);

        return new Result();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("analysis:awardrecords:update")
    public Result update(@Valid @RequestBody AwardRecordsDTO dto, BindingResult result){
        ValidDtoUtils.throwValidateException(result);
        //效验数据
        //ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        awardRecordsService.update(dto);

        return new Result();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("analysis:awardrecords:delete")
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        awardRecordsService.delete(ids);

        return new Result();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("analysis:awardrecords:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<AwardRecordsDTO> list = awardRecordsService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, "获奖记录设置", list, AwardRecordsExcel.class);
    }

}
