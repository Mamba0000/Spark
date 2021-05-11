package com.lyc.spark.service.uum.controller;
import com.lyc.spark.core.auth.annotation.PreAuth;
import com.lyc.spark.core.auth.util.SecureUtil;
import com.lyc.spark.core.auth.util.TokenUser;
import com.lyc.spark.core.common.api.CommonResult;
import com.lyc.spark.core.mybatisplus.support.Condition;
import com.lyc.spark.core.support.Kv;
import com.lyc.spark.core.tool.Func;
import com.lyc.spark.service.uum.entity.Menu;
import com.lyc.spark.service.uum.service.IMenuService;
import com.lyc.spark.service.uum.vo.MenuVO;
import com.lyc.spark.service.uum.wrapper.MenuWrapper;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 控制器
 *
 * 
 */
@RestController
@AllArgsConstructor
@RequestMapping("/menu")
@Api(value = "菜单", tags = "菜单")
public class MenuController  {
	private IMenuService menuService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入menu")
	public CommonResult<MenuVO> detail(Menu menu) {
		Menu detail = menuService.getOne(Condition.getQueryWrapper(menu));
		return CommonResult.data(MenuWrapper.build().entityVO(detail));
	}

	/**
	 * 列表 ok
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "code", value = "菜单编号", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "name", value = "菜单名称", paramType = "query", dataType = "string")
	})
	@ApiOperation(value = "列表", notes = "传入menu")
	public CommonResult<List<MenuVO>> list(@ApiIgnore @RequestParam Map<String, Object> menu) {
		@SuppressWarnings("unchecked")
		List<Menu> list = menuService.list(Condition.getQueryWrapper(menu, Menu.class).lambda().orderByAsc(Menu::getSort));
		return CommonResult.data(MenuWrapper.build().listNodeVO(list));
	}

	/**
	 * 新增或修改 ok
	 */
	@PostMapping("/addOrUpdate")
	@ApiOperation(value = "新增或修改", notes = "传入menu")
	public CommonResult addOrUpdate(@Valid @RequestBody Menu menu) {
		return CommonResult.status(menuService.saveOrUpdate(menu));
	}

	/**
	 * 删除
	 */
	@PostMapping("/deleteLogic")
	@ApiOperation(value = "删除", notes = "传入ids")
	public CommonResult deleteLogic(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return CommonResult.status(menuService.removeByIds(Func.toLongList(ids)));
	}

//	/**
//	 * 前端菜单数据
//	 */
//	@GetMapping("/routes")
//	@ApiOperation(value = "前端菜单数据", notes = "前端菜单数据")
//	public CommonResult<List<MenuVO>> routes() {
//		TokenUser tokenUser = SecureUtil.getUser();
//		List<MenuVO> list = menuService.routes((tokenUser == null || tokenUser.getUserId() == 0L) ? null : tokenUser.getRoleId());
//		return CommonResult.data(list);
//	}

//	/**
//	 * 前端按钮数据
//	 */
//	@GetMapping("/buttons")
//	@ApiOperation(value = "前端按钮数据", notes = "前端按钮数据")
//	public CommonResult<List<MenuVO>> buttons() {
//		TokenUser tokenUser = SecureUtil.getUser();
//		List<MenuVO> list = menuService.buttons(tokenUser.getRoleId());
//		return CommonResult.data(list);
//	}

	/**
	 * 获取菜单树形结构
	 */
	@GetMapping("/tree")
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public CommonResult<List<MenuVO>> tree() {
		List<MenuVO> tree = menuService.tree();
		tree = tree.stream().sorted(MenuVO::compare).collect(Collectors.toList());
		return CommonResult.data(tree);
	}

//	/**
//	 * 获取权限分配树形结构
//	 */
//	@GetMapping("/grantTree")
//	@ApiOperation(value = "权限分配树形结构", notes = "权限分配树形结构")
//	public CommonResult<List<MenuVO>> grantTree() {
//		TokenUser tokenUser = SecureUtil.getUser();
//
//		return CommonResult.data(menuService.grantTree(tokenUser));
//	}

//	/**
//	 * 获取权限分配树形结构
//	 */
//	@GetMapping("/roleTreeKeys")
//	@ApiOperation(value = "角色所分配的树", notes = "角色所分配的树")
//	public CommonResult<List<String>> roleTreeKeys(String roleIds) {
//		return CommonResult.data(menuService.roleTreeKeys(roleIds));
//	}

//	/**
//	 * 获取配置的角色权限
//	 */
//	@GetMapping("authRoutes")
//	@ApiOperation(value = "菜单的角色权限")
//	public CommonResult<List<Kv>> authRoutes() {
//		TokenUser tokenUser = SecureUtil.getUser();
//		if (Func.isEmpty(tokenUser) || tokenUser.getUserId() == 0L) {
//			return null;
//		}
//		return CommonResult.data(menuService.authRoutes(tokenUser));
//	}

}
