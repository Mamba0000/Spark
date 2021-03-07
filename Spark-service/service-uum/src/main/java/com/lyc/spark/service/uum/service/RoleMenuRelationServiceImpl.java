package com.lyc.spark.service.uum.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.lyc.spark.service.uum.entity.RoleMenuRelation;
import com.lyc.spark.service.uum.mapper.RoleMenuRelationMapper;
import com.lyc.spark.service.uum.service.impl.RoleMenuRelationService;
import org.springframework.stereotype.Service;

/**
 * 角色菜单关系管理Service实现类
 */
@Service
public class RoleMenuRelationServiceImpl extends ServiceImpl<RoleMenuRelationMapper, RoleMenuRelation> implements RoleMenuRelationService {
}
