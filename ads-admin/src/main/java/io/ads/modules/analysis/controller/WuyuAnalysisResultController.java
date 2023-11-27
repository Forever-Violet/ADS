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
import io.ads.modules.analysis.dto.WuyuAnalysisResultDTO;
import io.ads.modules.analysis.excel.WuyuAnalysisResultExcel;
import io.ads.modules.analysis.service.WuyuAnalysisResultService;
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
import java.util.List;
import java.util.Map;


/**
 * 五育分析 结果（报告）表
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-25
 */
@RestController
@RequestMapping("analysis/wuyuanalysisresult")
@Api(tags="五育分析 结果（报告）表")
public class WuyuAnalysisResultController {
    @Autowired
    private WuyuAnalysisResultService wuyuAnalysisResultService;


    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("analysis:wuyuanalysisresult:info")
    public Result<WuyuAnalysisResultDTO> get(@PathVariable("id") Long id){
        WuyuAnalysisResultDTO data = wuyuAnalysisResultService.get(id);

        return new Result<WuyuAnalysisResultDTO>().ok(data);
    }


    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("analysis:wuyuscore:update")
    public Result update(@RequestBody WuyuAnalysisResultDTO dto, BindingResult result){
        ValidDtoUtils.throwValidateException(result);

        wuyuAnalysisResultService.update(dto);

        return new Result();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("analysis:wuyuanalysisresult:delete")
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        wuyuAnalysisResultService.delete(ids);

        return new Result();
    }

}
