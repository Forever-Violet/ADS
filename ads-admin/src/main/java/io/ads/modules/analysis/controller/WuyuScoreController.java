package io.ads.modules.analysis.controller;

import cn.hutool.core.io.file.FileNameUtil;
import io.ads.common.annotation.LogOperation;
import io.ads.common.constant.Constant;
import io.ads.common.exception.ErrorCode;
import io.ads.common.exception.RenException;
import io.ads.common.page.PageData;
import io.ads.common.utils.ExcelUtils;
import io.ads.common.utils.Result;
import io.ads.common.utils.ValidDtoUtils;
import io.ads.common.validator.AssertUtils;
import io.ads.modules.analysis.dto.WuyuAnalysisResultDTO;
import io.ads.modules.analysis.dto.WuyuScoreDTO;
import io.ads.modules.analysis.excel.WuyuScoreExcel;
import io.ads.modules.analysis.excel.WuyuScoreImportExcel;
import io.ads.modules.analysis.service.WuyuScoreService;
import io.ads.modules.oss.cloud.OSSFactory;
import io.ads.modules.oss.entity.SysOssEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;


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
    public Result save(@Valid @RequestBody WuyuScoreDTO dto, BindingResult result){
        ValidDtoUtils.throwValidateException(result);
/*        if (result.hasErrors()) {
            // dto校验出异常，在这里处理
            List<String> errMessages = new ArrayList<>();
            for (ObjectError error : result.getAllErrors()) {
                errMessages.add(error.getDefaultMessage());
            }
            throw new RenException(errMessages.toString());
        }*/

        //效验数据
        //ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        wuyuScoreService.save(dto);

        return new Result();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("analysis:wuyuscore:update")
    public Result update(@Valid @RequestBody WuyuScoreDTO dto, BindingResult result){
        ValidDtoUtils.throwValidateException(result);

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


    @GetMapping("individual/{id}")
    @ApiOperation("个人诊断报告")
    @LogOperation("个人诊断")
    @RequiresPermissions("analysis:wuyuscore:individual")
    public Result individualAnalysis(@PathVariable("id") Long id) {
        WuyuAnalysisResultDTO dto = wuyuScoreService.genOrGetAnalysisReport(id);

        return new Result<WuyuAnalysisResultDTO>().ok(dto);
    }

    @PutMapping("individual/re/{id}")
    @ApiOperation("重新生成个人诊断报告")
    @LogOperation("re个人诊断")
    @RequiresPermissions("analysis:wuyuscore:individual")
    public Result reIndividualAnalysis(@PathVariable("id") Long id) {
        wuyuScoreService.reGenAnalysisReport(id);

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

    @GetMapping("exportTemplate")
    @ApiOperation("导出空模板")
    @LogOperation("导出空模板")
    @RequiresPermissions("analysis:wuyuscore:export")
    public void exportTemplate(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {


        ExcelUtils.exportExcelToTarget(response, "五育成绩导入模板", "五育成绩导入模板", new ArrayList<>(), WuyuScoreImportExcel.class);
    }


    @PostMapping("upload")
    @ApiOperation(value = "上传Excel文件")
    @RequiresPermissions("analysis:wuyuscore:save")
    public Result<Map<String, Object>> uploadAndReadExcel(@RequestParam("file") MultipartFile file) throws Exception {
        // 通过MIME 类型判断是否为excel文件
        String contentType = file.getContentType();
        if (!ExcelUtils.isValidExcelContentType(contentType)) {
            throw new RenException("文件不是有效的Excel格式: " + contentType);
        }
        // 通过文件扩展名判断是否为excel文件
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !ExcelUtils.isExcelFile(originalFilename)) {
            throw new RenException("文件不是有效的Excel格式: " + originalFilename);
        }

        if (file.isEmpty()) {
            throw new RenException("文件不能为空");
        }

        return new Result<Map<String, Object>>().ok(wuyuScoreService.readExcel(file));
    }

}
