package com.lyc.spark.service.uum.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.spark.service.uum.entity.UserRoleRelation;
import com.lyc.spark.service.uum.mapper.RoleUserRelationMapper;
import com.lyc.spark.service.uum.service.impl.UserRoleRelationService;
import org.springframework.stereotype.Service;

/**
 * 管理员角色关系管理Service实现类
 */
@Service
public class UserRoleRelationServiceImpl extends ServiceImpl<RoleUserRelationMapper, UserRoleRelation> implements UserRoleRelationService {


}
