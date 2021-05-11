
package com.lyc.spark.service.uum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.spark.service.uum.entity.Role;
import com.lyc.spark.service.uum.entity.RoleMenu;
import com.lyc.spark.service.uum.mapper.RoleMenuMapper;
import com.lyc.spark.service.uum.service.IRoleMenuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务实现类
 *
 * 
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

    public List<RoleMenu> listByRoleIds(List<Long> roleIds) {
       return baseMapper.selectList(Wrappers.<RoleMenu>query().lambda().in(RoleMenu::getRoleId, roleIds));
    }
}
