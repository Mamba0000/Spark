
package com.lyc.spark.service.uum.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.spark.core.auth.util.SecureUtil;
import com.lyc.spark.core.constant.RoleConstant;
import com.lyc.spark.core.node.ForestNodeMerger;
import com.lyc.spark.core.tool.BeanUtil;
import com.lyc.spark.core.tool.CollectionUtil;
import com.lyc.spark.core.tool.Func;
import com.lyc.spark.service.uum.entity.*;
import com.lyc.spark.service.uum.mapper.RoleMapper;
import com.lyc.spark.service.uum.service.*;
import com.lyc.spark.service.uum.vo.MenuVO;
import com.lyc.spark.service.uum.vo.PermissionVO;
import com.lyc.spark.service.uum.vo.RoleVO;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 服务实现类
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    @Lazy
    public IRoleMenuService roleMenuService;

    @Autowired
    @Lazy
    public IRolePermissionService rolePermissionService;

    @Autowired
    @Lazy
    public IMenuService menuService;

    @Autowired
    @Lazy
    public IPermissionService permissionService;

//
//	@Override
//    public IPage<RoleVO> selectRolePage(IPage<RoleVO> page, RoleVO role) {
//        return page.setRecords(baseMapper.selectRolePage(page, role));
//    }

    @Override
    public List<RoleVO> tree(String tenantId) {
        String userRole = SecureUtil.getUserRole();
        String excludeRole = null;
        if (!CollectionUtil.contains(Func.toStrArray(userRole), RoleConstant.ADMIN)) {
            excludeRole = RoleConstant.ADMIN;
        }
        return ForestNodeMerger.merge(baseMapper.tree(tenantId, excludeRole));
    }

    @Override
    public boolean grantRoleMenu(@NotEmpty List<Long> roleIds, @NotEmpty List<Long> menuIds) {
        // 删除角色配置的菜单集合
        roleMenuService.remove(Wrappers.<RoleMenu>update().lambda().in(RoleMenu::getRoleId, roleIds));
        // 组装配置
        List<RoleMenu> roleMenus = new ArrayList<>();
        roleIds.forEach(roleId -> menuIds.forEach(menuId -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenus.add(roleMenu);
        }));
        // 新增配置
        return roleMenuService.saveBatch(roleMenus);
    }

    @Override
    public List<Menu> listMenuByRoleIds(List<Long> roleIds) {
        return menuService.listMenuByRoleIds(roleIds);
    }

    @Override
    public List<MenuVO> listAllTreeMenuByRoleIds(List<Long> roleIds) {
        List<Menu> allMenus = menuService.list();
        List<Menu> menus = listMenuByRoleIds(roleIds);
        List<MenuVO> menuVOS = allMenus.stream().map(menu -> {
                    MenuVO menuVO = BeanUtil.copy(menu, MenuVO.class);
                    menus.forEach(menuTemp -> {
                        if(menuVO.getId().equals(menuTemp.getId())) {
                            menuVO.setCheck(true);
                        }
                    });
                    return  menuVO;
                }
        ).collect(Collectors.toList());
        return ForestNodeMerger.merge(menuVOS);
    }

    public boolean grantRolePermission(@NotNull List<Long> roleIds, @NotEmpty List<Long> permission) {
		// 删除角色配置的菜单集合
		rolePermissionService.remove(Wrappers.<RolePermission>update().lambda().in(RolePermission::getRoleId, roleIds));
		// 组装配置
		List<RolePermission> rolePermissions = new ArrayList<>();
		roleIds.forEach(roleId -> permission.forEach(permissionId -> {
			RolePermission rolePermission = new RolePermission();
			rolePermission.setRoleId(roleId);
			rolePermission.setPermissionId(permissionId);
			rolePermissions.add(rolePermission);
		}));
		// 新增配置
		return rolePermissionService.saveBatch(rolePermissions);
    }


    @Override
    public List<Permission> listPermissionByRoleIds(List<Long> roleIds) {
        return permissionService.listPermissionByRoleIds(roleIds);
    }


    public List<PermissionVO> listAllTreePermissionByRoleIds(List<Long> roleIds) {
        List<Permission> allPermissions = permissionService.list();
        List<Permission> permissions = listPermissionByRoleIds(roleIds);
        List<PermissionVO> permissionVOS = allPermissions.stream().map(menu -> {
            PermissionVO permissionVO = BeanUtil.copy(menu, PermissionVO.class);
            permissions.forEach(permission -> {
                        if(permissionVO.getId().equals(permission.getId())) {
                            permissionVO.setCheck(true);
                        }
                    });
                    return  permissionVO;
                }
        ).collect(Collectors.toList());
//        return permissionVOS;
        return ForestNodeMerger.merge(permissionVOS);
    }

    @Override
    public String getRoleIds(String tenantId, String roleNames) {
        List<Role> roleList = baseMapper.selectList(Wrappers.<Role>query().lambda().eq(Role::getTenantId, tenantId).in(Role::getRoleName, Func.toStrList(roleNames)));
        if (roleList != null && roleList.size() > 0) {
            return roleList.stream().map(role -> Func.toStr(role.getId())).distinct().collect(Collectors.joining(","));
        }
        return null;
    }

    @Override
    public List<String> getRoleNames(String roleIds) {
        return baseMapper.getRoleNames(Func.toLongArray(roleIds));
    }

}
