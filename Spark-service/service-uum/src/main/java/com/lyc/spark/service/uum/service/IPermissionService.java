package com.lyc.spark.service.uum.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lyc.spark.service.uum.entity.*;
import java.util.List;

/**
 * 权限管理Service
 */
public interface IPermissionService extends IService<Permission> {

    /**
     *  增加或是修改
     * @param permission
     * @return
     */
    public boolean submit(Permission permission) ;

    /**
     * 通过角色权限中间表获取关联权限
     * @param rolePermissions
     * @return
     */
    public List<Permission> list(List<RolePermission> rolePermissions);

    /**
     * 通过角色获取关联权限
     * @param roles
     * @return
     */
    public List<Permission> listPermissionByRoles(List<Role> roles);

    /**
     * 通过角色i获取关联权限
     * @param roleIds
     * @return
     */
    public List<Permission> listPermissionByRoleIds(List<Long> roleIds) ;

}
