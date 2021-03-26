package com.lyc.spark.service.uum.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyc.spark.core.common.api.CommonResult;
import com.lyc.spark.core.mybatisplus.page.CommonPage;
import com.lyc.spark.service.uum.entity.Permission;
import com.lyc.spark.service.uum.service.IPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * 权限管理Controller
 */
@Controller
@Api(value = "权限管理", tags = "权限管理")
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private IPermissionService resourceService;


    @ApiOperation("添加权限")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(@RequestBody Permission permission) {
        boolean success = resourceService.create(permission);
        if (success) {
            return CommonResult.status(true);
        } else {
            return CommonResult.status(false);
        }
    }

    @ApiOperation("修改权限")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable Long id,
                               @RequestBody Permission permission) {
        return CommonResult.status(resourceService.update(id, permission));
    }

    @ApiOperation("根据ID获取权限详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Permission> getItem(@PathVariable Long id) {
        Permission permission = resourceService.getById(id);
        return CommonResult.data(permission);
    }

    @ApiOperation("根据ID删除权限")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@PathVariable Long id) {
        return CommonResult.status(resourceService.delete(id));
    }

    @ApiOperation("分页模糊查询权限")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<Permission>> list(@RequestParam(required = false) Long categoryId,
                                                     @RequestParam(required = false) String nameKeyword,
                                                     @RequestParam(required = false) String urlKeyword,
                                                     @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<Permission> resourceList = resourceService.list(categoryId, nameKeyword, urlKeyword, pageSize, pageNum);
        return CommonResult.data(CommonPage.restPage(resourceList));
    }

    @ApiOperation("查询所有权限")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Permission>> listAll() {
        List<Permission> resourceList = resourceService.list();
        return CommonResult.data(resourceList);
    }
}
