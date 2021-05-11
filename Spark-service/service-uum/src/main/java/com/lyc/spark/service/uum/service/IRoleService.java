
package com.lyc.spark.service.uum.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyc.spark.service.uum.entity.Menu;
import com.lyc.spark.service.uum.entity.Permission;
import com.lyc.spark.service.uum.entity.Role;
import com.lyc.spark.service.uum.vo.MenuVO;
import com.lyc.spark.service.uum.vo.PermissionVO;
import com.lyc.spark.service.uum.vo.RoleVO;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 服务类
 *
 * 
 */
public interface IRoleService extends IService<Role> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param role
	 * @return
	 */
//	IPage<RoleVO> selectRolePage(IPage<RoleVO> page, RoleVO role);

	/**
	 * 树形结构
	 *
	 * @param tenantId
	 * @return
	 */
	List<RoleVO> tree(String tenantId);

	/**
	 * 权限配置菜单
	 *
	 * @param roleIds 角色id集合
	 * @param menuIds 菜单id集合
	 * @return 是否成功
	 */
	boolean grantRoleMenu(@NotNull List<Long> roleIds, @NotEmpty List<Long> menuIds);

	/**
	 * 获取角色相关的菜单
	 * @param roleIds
	 * @return
	 */
	List<Menu> listMenuByRoleIds(List<Long> roleIds);

	/**
	 * 获取角色关联的菜单 包含全部的菜单 并且设置已经关联的菜单为check状态
	 * @param roleIds
	 * @return
	 */
	List<MenuVO> listAllTreeMenuByRoleIds(List<Long> roleIds);

	/**
	 * 设置角色关联的权限
 	 * @param roleIds
	 * @param permission
	 * @return
	 */
	boolean grantRolePermission(@NotNull List<Long> roleIds, @NotEmpty List<Long> permission);


	/**
	 * 获取角色相关的权限 非树结构
	 * @param roleIds
	 * @return
	 */
	public List<Permission> listPermissionByRoleIds(List<Long> roleIds);

	/**
	 * 获取角色关联的权限 树结构
	 * @param roleIds
	 * @return
	 */
	List<PermissionVO> listAllTreePermissionByRoleIds(List<Long> roleIds);

	/**
	 * 获取角色ID
	 *
	 * @param tenantId
	 * @param roleNames
	 * @return
	 */
	String getRoleIds(String tenantId, String roleNames);

	/**
	 * 获取角色名
	 *
	 * @param roleIds
	 * @return
	 */
	List<String> getRoleNames(String roleIds);


}
