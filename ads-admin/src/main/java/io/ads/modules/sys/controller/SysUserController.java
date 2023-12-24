/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.ads.modules.sys.controller;

import io.ads.common.annotation.LogOperation;
import io.ads.common.constant.Constant;
import io.ads.common.exception.ErrorCode;
import io.ads.common.page.PageData;
import io.ads.common.utils.ConvertUtils;
import io.ads.common.utils.ExcelUtils;
import io.ads.common.utils.Result;
import io.ads.common.validator.AssertUtils;
import io.ads.common.validator.ValidatorUtils;
import io.ads.common.validator.group.AddGroup;
import io.ads.common.validator.group.DefaultGroup;
import io.ads.common.validator.group.UpdateGroup;
import io.ads.modules.analysis.dto.AwardSettingsDTO;
import io.ads.modules.security.password.PasswordUtils;
import io.ads.modules.security.user.SecurityUser;
import io.ads.modules.security.user.UserDetail;
import io.ads.modules.sys.dto.PasswordDTO;
import io.ads.modules.sys.dto.SysUserDTO;
import io.ads.modules.sys.excel.SysUserExcel;
import io.ads.modules.sys.service.SysRoleUserService;
import io.ads.modules.sys.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 用户管理
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/user")
@Api(tags = "用户管理")
@AllArgsConstructor
public class SysUserController {
    private final SysUserService sysUserService;
    private final SysRoleUserService sysRoleUserService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "username", value = "用户名", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "gender", value = "性别", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "schoolId", value = "学校ID", paramType = "query", dataType = "String")
    })
    @RequiresPermissions("sys:user:page")
    public Result<PageData<SysUserDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<SysUserDTO> page = sysUserService.page(params);

        return new Result<PageData<SysUserDTO>>().ok(page);
    }

    @GetMapping("student")
    @ApiOperation("学生列表")
    @RequiresPermissions("sys:user:student")
    public Result<List<SysUserDTO>> get(@ApiIgnore @RequestParam Map<String, Object> params) {
        List<SysUserDTO> data = sysUserService.getStudentList(params);

        return new Result<List<SysUserDTO>>().ok(data);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("sys:user:info")
    public Result<SysUserDTO> get(@PathVariable("id") Long id) {
        SysUserDTO data = sysUserService.get(id);


        return new Result<SysUserDTO>().ok(data);
    }

    @GetMapping("info")
    @ApiOperation("登录用户信息")
    public Result<SysUserDTO> info() {
        SysUserDTO data = ConvertUtils.sourceToTarget(SecurityUser.getUser(), SysUserDTO.class);
        return new Result<SysUserDTO>().ok(data);
    }

    @PutMapping("password")
    @ApiOperation("修改密码")
    @LogOperation("修改密码")
    public Result password(@RequestBody PasswordDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto);

        UserDetail user = SecurityUser.getUser();

        //原密码不正确
        if (!PasswordUtils.matches(dto.getPassword(), user.getPassword())) {
            return new Result().error(ErrorCode.PASSWORD_ERROR);
        }

        sysUserService.updatePassword(user.getId(), dto.getNewPassword());

        return new Result();
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("sys:user:save")
    public Result save(@RequestBody SysUserDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        sysUserService.save(dto);

        return new Result();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("sys:user:update")
    public Result update(@RequestBody SysUserDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        sysUserService.update(dto);

        return new Result();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("sys:user:delete")
    public Result delete(@RequestBody Long[] ids) {
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        sysUserService.delete(ids);

        return new Result();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("sys:user:export")
    @ApiImplicitParam(name = "username", value = "用户名", paramType = "query", dataType = "String")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<SysUserDTO> list = sysUserService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, "用户管理", list, SysUserExcel.class);
    }
}
