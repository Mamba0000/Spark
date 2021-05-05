
package com.lyc.spark.service.uum.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lyc.spark.core.auth.util.TokenUser;
import com.lyc.spark.core.auth.util.SecureUtil;
import com.lyc.spark.core.common.api.CommonResult;
import com.lyc.spark.core.constant.BladeConstant;
import com.lyc.spark.core.mybatisplus.support.Condition;
import com.lyc.spark.core.mybatisplus.support.Query;
import com.lyc.spark.core.tool.Func;
import com.lyc.spark.service.uum.entity.User;
import com.lyc.spark.service.uum.service.IUserService;
import com.lyc.spark.service.uum.vo.UserVO;
import com.lyc.spark.service.uum.wrapper.UserWrapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.codec.Charsets;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 控制器
 *
 */
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

	private IUserService userService;

	/**
	 * 查询单条
	 */
	@ApiOperation(value = "查看详情", notes = "传入id")
	@GetMapping("/detail")
	public CommonResult<UserVO> detail(User user) {
		User detail = userService.getById(user.getId());
		return CommonResult.data(UserWrapper.build().entityVO(detail));
	}

	/**
	 * 用户列表 OK
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "account", value = "账号名", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "realName", value = "姓名", paramType = "query", dataType = "string")
	})
	@ApiOperation(value = "列表", notes = "传入account和realName")
	public CommonResult<IPage<UserVO>> list(@ApiIgnore @RequestParam Map<String, Object> user, Query query) {
		QueryWrapper<User> queryWrapper = Condition.getQueryWrapper(user, User.class);
		TokenUser tokenUser =  SecureUtil.getUser();
		IPage<User> pages = userService.page(Condition.getPage(query), (!tokenUser.getTenantId().equals(BladeConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(User::getTenantId, tokenUser.getTenantId()) : queryWrapper);
		return CommonResult.data(UserWrapper.build().pageVO(pages));
	}

	/**
	 * 新增或修改 OK
	 */
	@PostMapping("/addOrUpdate")
	@ApiOperation(value = "新增或修改", notes = "传入User")
	public CommonResult addOrUpdate(@Valid @RequestBody User user) {
		return CommonResult.status(userService.submit(user));
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入User")
	public CommonResult update(@Valid @RequestBody User user) {
		return CommonResult.status(userService.updateById(user));
	}

	/**
	 * 删除
	 */
	@PostMapping("/deleteLogic")
	@ApiOperation(value = "删除", notes = "传入ids")
	public CommonResult deleteLogic(@RequestParam String ids) {
		return CommonResult.status(userService.deleteLogic(Func.toLongList(ids)));
	}


	/**
	 * 设置菜单权限
	 *
	 * @param userIds
	 * @param roleIds
	 * @return
	 */
	@PostMapping("/grant")
	@ApiOperation(value = "权限设置", notes = "传入roleId集合以及menuId集合")
	public CommonResult grant(@ApiParam(value = "userId集合", required = true) @RequestParam String userIds,
				   @ApiParam(value = "roleId集合", required = true) @RequestParam String roleIds) {
		boolean temp = userService.grant(userIds, roleIds);
		return CommonResult.status(temp);
	}


	@PostMapping("/resetPassword")
	@ApiOperation(value = "初始化密码", notes = "传入userId集合")
	public CommonResult resetPassword(@ApiParam(value = "userId集合", required = true) @RequestParam String userIds) {
		boolean temp = userService.resetPassword(userIds);
		return CommonResult.status(temp);
	}

	/**
	 * 修改密码
	 *
	 * @param oldPassword
	 * @param newPassword
	 * @param newPassword1
	 * @return
	 */
	@PostMapping("/updatePassword")
	@ApiOperation(value = "修改密码", notes = "传入密码")
	public CommonResult updatePassword(TokenUser user, @ApiParam(value = "旧密码", required = true) @RequestParam String oldPassword,
									   @ApiParam(value = "新密码", required = true) @RequestParam String newPassword,
									   @ApiParam(value = "新密码", required = true) @RequestParam String newPassword1) {
		boolean temp = userService.updatePassword(user.getUserId(), oldPassword, newPassword, newPassword1);
		return CommonResult.status(temp);
	}

}
