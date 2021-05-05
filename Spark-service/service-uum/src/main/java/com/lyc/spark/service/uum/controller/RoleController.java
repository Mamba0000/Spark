package com.lyc.spark.service.uum.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyc.spark.core.auth.util.SecureUtil;
import com.lyc.spark.core.auth.util.TokenUser;
import com.lyc.spark.core.common.api.CommonResult;
import com.lyc.spark.core.constant.BladeConstant;
import com.lyc.spark.core.mybatisplus.support.Condition;
import com.lyc.spark.core.node.INode;
import com.lyc.spark.core.tool.Func;
import com.lyc.spark.service.uum.entity.Role;
import com.lyc.spark.service.uum.service.IRoleService;
import com.lyc.spark.service.uum.vo.GrantVO;
import com.lyc.spark.service.uum.vo.RoleVO;
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
	 * 列表
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "roleName", value = "参数名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "roleAlias", value = "角色别名", paramType = "query", dataType = "string")
	})
	@ApiOperation(value = "列表", notes = "传入role")
	public CommonResult<List<INode>> list(@ApiIgnore @RequestParam Map<String, Object> role) {
		int a  = 10 ;
		TokenUser tokenUser = SecureUtil.getUser();
		QueryWrapper<Role> queryWrapper = Condition.getQueryWrapper(role, Role.class);
		List<Role> list = roleService.list((!tokenUser.getTenantId().equals(BladeConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(Role::getTenantId, tokenUser.getTenantId()) : queryWrapper);
		return CommonResult.data(RoleWrapper.build().listNodeVO(list));
	}

	/**
	 * 获取角色树形结构
	 */
	@GetMapping("/tree")
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public CommonResult<List<RoleVO>> tree(String tenantId) {
		TokenUser tokenUser = SecureUtil.getUser();

		List<RoleVO> tree = roleService.tree(Func.toStr(tenantId, tokenUser.getTenantId()));
		return CommonResult.data(tree);
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入role")
	public CommonResult submit(@Valid @RequestBody Role role) {
		TokenUser tokenUser = SecureUtil.getUser();
		if (Func.isEmpty(role.getId())) {
			role.setTenantId(tokenUser.getTenantId());
		}
		return CommonResult.status(roleService.saveOrUpdate(role));
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "传入ids")
	public CommonResult remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return CommonResult.status(roleService.removeByIds(Func.toLongList(ids)));
	}

	/**
	 * 设置菜单权限
	 */
	@PostMapping("/grant")
	@ApiOperation(value = "权限设置", notes = "传入roleId集合以及menuId集合")
	public CommonResult grant(@RequestBody GrantVO grantVO) {
		boolean temp = roleService.grant(grantVO.getRoleIds(), grantVO.getMenuIds());
		return CommonResult.status(temp);
	}

}
