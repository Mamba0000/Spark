package com.lyc.spark.service.uum.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyc.spark.core.auth.util.SecureUtil;
import com.lyc.spark.core.auth.util.TokenUser;
import com.lyc.spark.core.common.api.CommonResult;
import com.lyc.spark.core.mybatisplus.support.Condition;
import com.lyc.spark.core.node.INode;
import com.lyc.spark.core.tool.Func;
import com.lyc.spark.service.uum.entity.Menu;
import com.lyc.spark.service.uum.entity.Permission;
import com.lyc.spark.service.uum.entity.Role;
import com.lyc.spark.service.uum.service.IMenuService;
import com.lyc.spark.service.uum.service.IRoleService;
import com.lyc.spark.service.uum.vo.*;
import com.lyc.spark.service.uum.wrapper.RoleWrapper;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 控制器
 *
 * 
 */
@RestController
@AllArgsConstructor
@RequestMapping("/role")
@Api(value = "角色", tags = "角色")
public class RoleController  {

	private IMenuService menuService;

	private IRoleService roleService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入role")
	public CommonResult<RoleVO> detail(Role role) {
		Role detail = roleService.getOne(Condition.getQueryWrapper(role));
		return CommonResult.data(RoleWrapper.build().entityVO(detail));
	}

	/**
	 * 列表 权限列表是一颗树 不分页 OK
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "roleName", value = "角色名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "roleAlias", value = "角色别名", paramType = "query", dataType = "string")
	})
	@ApiOperation(value = "列表", notes = "传入role")
	public CommonResult<List<INode>> list(@ApiIgnore @RequestParam Map<String, Object> role) {
		TokenUser tokenUser = SecureUtil.getUser();
		QueryWrapper<Role> queryWrapper = Condition.getQueryWrapper(role, Role.class);
		//
		List<Role> list = roleService.list(queryWrapper.lambda().eq(Role::getTenantId, tokenUser.getTenantId()));
		return CommonResult.data(RoleWrapper.build().listNodeVO(list));
	}

	/**
	 * 获取角色树形结构 OK
	 */
	@GetMapping("/tree")
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public CommonResult<List<RoleVO>> tree(String tenantId) {
		TokenUser tokenUser = SecureUtil.getUser();
		List<RoleVO> tree = roleService.tree(Func.toStr(tenantId, tokenUser.getTenantId()));
		return CommonResult.data(tree);
	}

	/**
	 * 新增或修改 OK
	 */
	@PostMapping("/addOrUpdate")
	@ApiOperation(value = "新增或修改", notes = "传入role")
	public CommonResult addOrUpdate(@Valid @RequestBody Role role) {
		TokenUser tokenUser = SecureUtil.getUser();
		if (Func.isEmpty(role.getId())) {
			role.setTenantId(tokenUser.getTenantId());
		}
		return CommonResult.status(roleService.saveOrUpdate(role));
	}

	/**
	 * 删除 OK
	 */
	@PostMapping("/deleteLogic")
	@ApiOperation(value = "删除", notes = "传入ids")
	public CommonResult remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return CommonResult.status(roleService.removeByIds(Func.toLongList(ids)));
	}

	/**
	 * 设置角色关联的菜单 OK
	 */
	@PostMapping("/grantRoleMenu")
	@ApiOperation(value = "角色设置权限", notes = "传入roleId集合以及menuId集合")
	public CommonResult grantRoleMenu(@RequestBody GrantRoleMenuVO grantRoleMenuVo) {
		boolean temp = roleService.grantRoleMenu(grantRoleMenuVo.getRoleIds(), grantRoleMenuVo.getMenuIds());
		return CommonResult.status(temp);
	}

//	/**
//	 * 获取角色关联的菜单 OK
//	 */
//	@PostMapping("/listMenuByRoleIds")
//	@ApiOperation(value = "获取角色权限", notes = "")
//	public CommonResult listMenuByRoleIds(String ids) {
//		List<Menu> menus = roleService.listMenuByRoleIds(Func.toLongList(ids));
//		return CommonResult.data(menus);
//	}

	/**
	 * 获取角色关联的菜单 包含全部的菜单 并且设置已经关联的菜单为check状态 ids 用,分开 OK
	 */
	@GetMapping("/listAllTreeMenuByRoleIds")
	@ApiOperation(value = "获取角色权限", notes = "")
	public CommonResult listAllMenuByRoleIds(String ids) {
		List<MenuVO> menuVOS = roleService.listAllTreeMenuByRoleIds(Func.toLongList(ids));
		return CommonResult.data(menuVOS);
	}



	/**
	 * 设置角色关联的权限 OK
	 */
	@PostMapping("/grantRolePermission")
	@ApiOperation(value = "权限设置", notes = "传入roleId集合以及menuId集合")
	public CommonResult grantRolePermission(@RequestBody GrantRolePermissionVO grantRolePermission) {
		boolean temp = roleService.grantRolePermission(grantRolePermission.getRoleIds(), grantRolePermission.getPermissionIds());
		return CommonResult.status(temp);
	}


//	/**
//	 * 获取角色关联的权限 OK 不存在树结构
//	 */
//	@GetMapping("/listPermissionByRoleIds")
//	@ApiOperation(value = "获取角色权限", notes = "")
//	public CommonResult listPermissionByRoleIds(String ids) {
//		final List<Permission> permissions = roleService.listPermissionByRoleIds(Func.toLongList(ids));
//		return CommonResult.data(permissions);
//	}


	/**
	 * 获取角色关联的权限 OK 树结构 目前未能支持
	 */
	@GetMapping("/listAllTreePermissionByRoleIds")
	@ApiOperation(value = "获取角色权限", notes = "")
	public CommonResult listAllTreePermissionByRoleIds(String ids) {
		final List<PermissionVO> permissions = roleService.listAllTreePermissionByRoleIds(Func.toLongList(ids));
		return CommonResult.data(permissions);
	}

}
