package com.lyc.spark.service.uum.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyc.spark.service.uum.entity.RolePermission;

import java.util.List;

/**
 * 角色权限关系管理Service
 */
public interface IRolePermissionService extends IService<RolePermission> {
    /**
     *  通过roleIds 查询关联 中间表
     * @param roleIds
     * @return
     */
    public List<RolePermission> listByRoleIds(List<Long> roleIds);
}
