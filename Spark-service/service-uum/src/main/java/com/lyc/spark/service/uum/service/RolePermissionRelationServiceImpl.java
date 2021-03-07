package com.lyc.spark.service.uum.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.lyc.spark.service.uum.entity.RolePermissionRelation;
import com.lyc.spark.service.uum.mapper.RolePermissionRelationMapper;
import com.lyc.spark.service.uum.service.impl.RolePermissionRelationService;
import org.springframework.stereotype.Service;

/**
 * 角色权限关系管理Service实现类
 */
@Service
public class RolePermissionRelationServiceImpl extends ServiceImpl<RolePermissionRelationMapper, RolePermissionRelation> implements RolePermissionRelationService {
}
