package com.lyc.spark.service.uum.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.lyc.spark.service.uum.entity.RolePermission;
import com.lyc.spark.service.uum.mapper.RolePermissionMapper;
import com.lyc.spark.service.uum.service.IRolePermissionService;
import org.springframework.stereotype.Service;

/**
 * 角色权限关系管理Service实现类
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements IRolePermissionService {
}
