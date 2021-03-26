
package com.lyc.spark.auth.controller;

import com.lyc.spark.auth.granter.ITokenGranter;
import com.lyc.spark.auth.granter.TokenGranterBuilder;
import com.lyc.spark.auth.granter.TokenParameter;
import com.lyc.spark.auth.util.TokenUtil;
import com.lyc.spark.core.auth.info.AuthInfo;
import com.lyc.spark.core.common.api.CommonResult;
import com.lyc.spark.core.common.constant.CacheNames;
import com.lyc.spark.core.redis.util.RedisUtil;
import com.lyc.spark.core.tool.Func;
import com.lyc.spark.core.tool.WebUtil;
import com.lyc.spark.service.uum.entity.UserInfo;
import com.wf.captcha.SpecCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.UUID;

/**
 * 认证模块
 *
 */
@RestController
@AllArgsConstructor
@Api(value = "用户授权认证", tags = "授权接口")
public class AuthController {

	private RedisUtil redisUtil;

	@PostMapping("token")
	@ApiOperation(value = "获取认证token", notes = "传入租户ID:tenantId,账号:account,密码:password")
	public CommonResult<AuthInfo> token(@ApiParam(value = "授权类型", required = true) @RequestParam(defaultValue = "password", required = false) String grantType,
										@ApiParam(value = "刷新令牌") @RequestParam(required = false) String refreshToken,
										@ApiParam(value = "租户ID", required = true) @RequestParam(defaultValue = "000000", required = false) String tenantId,
										@ApiParam(value = "账号") @RequestParam(required = false) String account,
										@ApiParam(value = "密码") @RequestParam(required = false) String password) {

		String userType = Func.toStr(WebUtil.getRequest().getHeader(TokenUtil.USER_TYPE_HEADER_KEY), TokenUtil.DEFAULT_USER_TYPE);

		TokenParameter tokenParameter = new TokenParameter();
		tokenParameter.getArgs().set("tenantId", tenantId)
			.set("account", account)
			.set("password", password)
			.set("grantType", grantType)
			.set("refreshToken", refreshToken)
			.set("userType", userType);
		ITokenGranter granter = TokenGranterBuilder.getGranter(grantType);
		//  用户信息 权限 导航
		UserInfo userInfo = granter.grant(tokenParameter);
		
		if (userInfo == null || userInfo.getUser() == null || userInfo.getUser().getId() == null) {
			return CommonResult.fail(TokenUtil.USER_NOT_FOUND);
		}

		return CommonResult.data(TokenUtil.createAuthInfo(userInfo));
	}

	@GetMapping("/captcha")
	@ApiOperation(value = "获取验证码")
	public CommonResult<HashMap<String, String>> captcha() {
		SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
		String verCode = specCaptcha.text().toLowerCase();
		String key = UUID.randomUUID().toString();
		// 存入redis并设置过期时间为30分钟
		redisUtil.set(CacheNames.CAPTCHA_KEY + key, verCode, 30L * 60 );
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("key",key);
		data.put("image",specCaptcha.toBase64());
		// 将key和base64返回给前端
		return CommonResult.data(data);
	}

}
