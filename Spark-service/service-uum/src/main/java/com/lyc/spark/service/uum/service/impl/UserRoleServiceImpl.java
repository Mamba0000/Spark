package com.lyc.spark.service.uum.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.lyc.spark.service.uum.entity.UserRole;
import com.lyc.spark.service.uum.mapper.RoleUserMapper;
import com.lyc.spark.service.uum.service.IUserRoleService;
import org.springframework.stereotype.Service;

/**
 * 管理员角色关系管理Service实现类
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<RoleUserMapper, UserRole> implements IUserRoleService {


}
