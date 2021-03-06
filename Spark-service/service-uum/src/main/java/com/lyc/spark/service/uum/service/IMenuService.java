
package com.lyc.spark.service.uum.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyc.spark.core.auth.util.TokenUser;
import com.lyc.spark.core.support.Kv;
import com.lyc.spark.service.uum.entity.Menu;
import com.lyc.spark.service.uum.entity.Role;
import com.lyc.spark.service.uum.entity.RoleMenu;
import com.lyc.spark.service.uum.vo.MenuVO;


import java.util.List;

/**
 * 服务类
 *
 * 
 */
public interface IMenuService extends IService<Menu> {

//	/**
//	 * 自定义分页
//	 *
//	 * @param page
//	 * @param menu
//	 * @return
//	 */
//	IPage<MenuVO> selectMenuPage(IPage<MenuVO> page, MenuVO menu);

//	/**
//	 * 菜单树形结构
//	 *
//	 * @param roleId
//	 * @return
//	 */
//	List<MenuVO> routes(String roleId);

//	/**
//	 * 按钮树形结构
//	 *
//	 * @param roleId
//	 * @return
//	 */
//	List<MenuVO> buttons(String roleId);

	/**
	 * 通过角色获取关联菜单
	 * @param roles
	 * @return
	 */
	public List<Menu> listMenuByRoles(List<Role> roles);

	/**
	 * 通过角色id获取关联菜单
	 * @param roleIds
	 * @return
	 */
	public List<Menu> listMenuByRoleIds(List<Long> roleIds);

	/**
	 * 通过角色菜单中间表获取菜单
	 * @param roleMenus
	 * @return
	 */
	List<Menu> list(List<RoleMenu> roleMenus);


	/**
	 * 树形结构
	 *
	 * @return
	 */
	List<MenuVO> tree();

//	/**
//	 * 授权树形结构
//	 *
//	 * @param user
//	 * @return
//	 */
//	List<MenuVO> grantTree(TokenUser user);

//	/**
//	 * 默认选中节点
//	 *
//	 * @param roleIds
//	 * @return
//	 */
//	List<String> roleTreeKeys(String roleIds);

//	/**
//	 * 获取配置的角色权限
//	 *
//	 * @param user
//	 * @return
//	 */
//	List<Kv> authRoutes(TokenUser user);

	/**
	 *  通过 List<RoleMenu> 关系表查询关联的菜单
	 * @param roleMenus
	 * @return
	 */

}
