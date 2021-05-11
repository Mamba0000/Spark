package com.lyc.spark.service.uum.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.spark.common.constant.CommonConstant;
import com.lyc.spark.core.common.exception.ServiceException;
import com.lyc.spark.core.tool.DateUtil;
import com.lyc.spark.core.tool.DigestUtil;
import com.lyc.spark.core.tool.Func;
import com.lyc.spark.service.uum.entity.*;
import com.lyc.spark.service.uum.mapper.PermissionMapper;
import com.lyc.spark.service.uum.service.IPermissionService;
import com.lyc.spark.service.uum.service.IRolePermissionService;
import com.lyc.spark.service.uum.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限管理Service实现类
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Autowired
    @Lazy
    IRolePermissionService rolePermissionService;

    @Override
    public boolean submit(Permission permission) {
        return saveOrUpdate(permission);
    }


    public List<Permission> list(List<RolePermission> rolePermissions) {
        if(rolePermissions.isEmpty()) return new ArrayList<Permission>();
        return listByIds(rolePermissions.stream().map(rolePermission -> rolePermission.getPermissionId()).collect(Collectors.toList()));
    }

    public List<Permission> listPermissionByRoles(List<Role> roles) {
        return listPermissionByRoleIds(roles.stream().map(role -> role.getId()).collect(Collectors.toList()));
    }

    public List<Permission> listPermissionByRoleIds(List<Long> roleIds) {
        if(roleIds.isEmpty()) return  new ArrayList<Permission>();
        List<RolePermission> roleMenus = rolePermissionService.listByRoleIds(roleIds);
        return list(roleMenus);
    }

}
