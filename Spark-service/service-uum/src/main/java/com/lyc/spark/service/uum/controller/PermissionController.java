package com.lyc.spark.service.uum.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyc.spark.core.auth.util.SecureUtil;
import com.lyc.spark.core.auth.util.TokenUser;
import com.lyc.spark.core.common.api.CommonResult;
import com.lyc.spark.core.constant.BladeConstant;
import com.lyc.spark.core.mybatisplus.page.CommonPage;
import com.lyc.spark.core.mybatisplus.support.Condition;
import com.lyc.spark.core.mybatisplus.support.Query;
import com.lyc.spark.core.tool.Func;
import com.lyc.spark.service.uum.entity.Permission;
import com.lyc.spark.service.uum.entity.User;
import com.lyc.spark.service.uum.service.IPermissionService;
import com.lyc.spark.service.uum.vo.PermissionVO;
import com.lyc.spark.service.uum.vo.UserVO;
import com.lyc.spark.service.uum.wrapper.MenuWrapper;
import com.lyc.spark.service.uum.wrapper.PermissionWrapper;
import com.lyc.spark.service.uum.wrapper.UserWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 权限管理Controller
 */
@RestController
@Api(value = "权限管理", tags = "权限管理")
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private IPermissionService permissionService;

    /**
     * 列表 OK
     */
    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "权限名字", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "value", value = "权限值", paramType = "query", dataType = "string")
    })
    @ApiOperation(value = "列表", notes = "传入account和realName")
    public CommonResult<List<PermissionVO>> list(@ApiIgnore @RequestParam Map<String, Object> permission, Query query) {
        QueryWrapper<Permission> queryWrapper = Condition.getQueryWrapper(permission, Permission.class);
        TokenUser tokenUser =  SecureUtil.getUser();
        final List<Permission> list = permissionService.list(queryWrapper);
        return CommonResult.data(PermissionWrapper.build().listNodeVO(list));
    }

    /**
     * 新增或修改 OK
     */
    @PostMapping("/addOrUpdate")
    @ApiOperation(value = "新增或修改", notes = "传入Permission")
    public CommonResult addOrUpdate(@Valid @RequestBody Permission permission) {
        return CommonResult.status(permissionService.submit(permission));
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改", notes = "传入Permission")
    public CommonResult update(@Valid @RequestBody Permission permission) {
        return CommonResult.status(permissionService.updateById(permission));
    }

    /**
     * 删除
     */
    @PostMapping("/deleteLogic")
    @ApiOperation(value = "删除", notes = "传入ids")
    public CommonResult deleteLogic(@RequestParam String ids) {
        return CommonResult.status(permissionService.removeByIds(Func.toLongList(ids)));
    }

}
