
package com.lyc.spark.service.uum.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.spark.core.auth.util.TokenUser;
import com.lyc.spark.core.constant.BladeConstant;
import com.lyc.spark.core.node.ForestNodeMerger;
import com.lyc.spark.service.uum.entity.Menu;
import com.lyc.spark.service.uum.entity.Role;
import com.lyc.spark.service.uum.entity.RoleMenu;
import com.lyc.spark.service.uum.mapper.MenuMapper;
import com.lyc.spark.service.uum.service.IMenuService;
import com.lyc.spark.service.uum.service.IRoleMenuService;
import com.lyc.spark.service.uum.vo.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * 
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

	@Autowired
	@Lazy
	IRoleMenuService roleMenuService;


//	@Override
//	public IPage<MenuVO> selectMenuPage(IPage<MenuVO> page, MenuVO menu) {
//		return page.setRecords(baseMapper.selectMenuPage(page, menu));
//	}

	public List<Menu> list(List<RoleMenu> roleMenus) {
		if(roleMenus.isEmpty()) return new ArrayList<Menu>();
		return listByIds(roleMenus.stream().map(roleMenu -> roleMenu.getMenuId()).collect(Collectors.toList()));
	}

	public List<Menu> listMenuByRoles(List<Role> roles) {
		return listMenuByRoleIds(roles.stream().map(role -> role.getId()).collect(Collectors.toList()));
	}

	public List<Menu> listMenuByRoleIds(List<Long> roleIds) {
		List<RoleMenu> roleMenus = roleMenuService.listByRoleIds(roleIds);
		return list(roleMenus);
	}

//	@Override
//	public List<MenuVO> routes(String roleId) {
//		if (StringUtil.isBlank(roleId)) {
//			return null;
//		}
//		List<Menu> allMenus = baseMapper.allMenu();
//		List<Menu> roleMenus = baseMapper.roleMenu(Func.toLongList(roleId));
//		List<Menu> routes = new LinkedList<>(roleMenus);
//		roleMenus.forEach(roleMenu -> recursion(allMenus, routes, roleMenu));
//		routes.sort(Comparator.comparing(Menu::getSort));
//		MenuWrapper menuWrapper = new MenuWrapper();
//		List<Menu> collect = routes.stream().filter(x -> Func.equals(x.getCategory(), 1)).collect(Collectors.toList());
//		return menuWrapper.listNodeVO(collect);
//	}

//	public void recursion(List<Menu> allMenus, List<Menu> routes, Menu roleMenu) {
//		Optional<Menu> menu = allMenus.stream().filter(x -> Func.equals(x.getId(), roleMenu.getParentId())).findFirst();
//		if (menu.isPresent() && !routes.contains(menu.get())) {
//			routes.add(menu.get());
//			recursion(allMenus, routes, menu.get());
//		}
//	}

//	@Override
//	public List<MenuVO> buttons(String roleId) {
//		List<Menu> buttons = baseMapper.buttons(Func.toLongList(roleId));
//		MenuWrapper menuWrapper = new MenuWrapper();
//		return menuWrapper.listNodeVO(buttons);
//	}

	@Override
	public List<MenuVO> tree() {
		return ForestNodeMerger.merge(baseMapper.tree());
	}

//	@Override
//	public List<MenuVO> grantTree(TokenUser user) {
//		return ForestNodeMerger.merge(user.getTenantId().equals(BladeConstant.ADMIN_TENANT_ID) ? baseMapper.grantTree() : baseMapper.grantTreeByRole(Func.toLongList(user.getRoleId())));
//	}

//	@Override
//	public List<String> roleTreeKeys(String roleIds) {
//		List<RoleMenu> roleMenus = roleMenuService.list(Wrappers.<RoleMenu>query().lambda().in(RoleMenu::getRoleId, Func.toLongList(roleIds)));
//		return roleMenus.stream().map(roleMenu -> Func.toStr(roleMenu.getMenuId())).collect(Collectors.toList());
//	}

//	@Override
//	public List<Kv> authRoutes(TokenUser user) {
//		if (Func.isEmpty(user)) {
//			return null;
//		}
//		List<MenuDTO> routes = baseMapper.authRoutes(Func.toLongList(user.getRoleId()));
//		List<Kv> list = new ArrayList<>();
//		routes.forEach(route -> list.add(Kv.init().set(route.getPath(), Kv.init().set("authority", Func.toStrArray(route.getAlias())))));
//		return list;
//	}

}
