package com.lyc.spark.service.uum.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyc.spark.service.uum.entity.Permission;

/**
 * 权限管理Service
 */
public interface IPermissionService extends IService<Permission> {
    /**
     * 添加权限
     */
    boolean create(Permission permission);

    /**
     * 修改权限
     */
    boolean update(Long id, Permission permission);

    /**
     * 删除权限
     */
    boolean delete(Long id);

    /**
     * 分页查询权限
     */
    Page<Permission> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum);
}
