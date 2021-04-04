
package com.lyc.spark.auth.util;

import com.lyc.spark.core.auth.info.AuthInfo;
import com.lyc.spark.core.auth.info.TokenInfo;
import com.lyc.spark.core.auth.util.SecureUtil;
import com.lyc.spark.core.common.constant.TokenConstant;
import com.lyc.spark.core.tool.Func;
import com.lyc.spark.service.uum.entity.User;
import com.lyc.spark.service.uum.entity.UserInfo;
import java.util.HashMap;
import java.util.Map;

/**
 * 认证工具类
 *
 */
public class TokenUtil {

	public final static String CAPTCHA_HEADER_KEY = "Captcha-Key";
	public final static String CAPTCHA_HEADER_CODE = "Captcha-Code";
	public final static String CAPTCHA_NOT_CORRECT = "验证码不正确";
	public final static String TENANT_HEADER_KEY = "Tenant-Id";
	public final static String DEFAULT_TENANT_ID = "000000";
	public final static String USER_TYPE_HEADER_KEY = "User-Type";
	public final static String DEFAULT_USER_TYPE = "web";
	public final static String USER_NOT_FOUND = "用户名或密码错误";
	public final static String HEADER_KEY = "Authorization";
	public final static String HEADER_PREFIX = "Basic ";
	public final static String DEFAULT_AVATAR = "https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png";

	/**
	 * 创建认证token
	 *
	 * @param userInfo 用户信息
	 * @return token
	 */
	public static AuthInfo createAuthInfo(UserInfo userInfo) {
		User user = userInfo.getUser();

		//设置jwt参数
		Map<String, String> param = new HashMap<>(16);
		param.put(TokenConstant.TOKEN_TYPE, TokenConstant.ACCESS_TOKEN);
		param.put(TokenConstant.TENANT_ID, user.getTenantId());
		param.put(TokenConstant.OAUTH_ID, userInfo.getOauthId());
		param.put(TokenConstant.USER_ID, Func.toStr(user.getId()));
		param.put(TokenConstant.ACCOUNT, user.getAccount());
		param.put(TokenConstant.USER_NAME, user.getAccount());
		param.put(TokenConstant.ROLE_NAME, Func.join(userInfo.getRoles()));
		// 目前用这个做权限判断
		param.put(TokenConstant.PERMISSIONS, Func.join(userInfo.getPermissions(),"$"));

		TokenInfo accessToken = SecureUtil.createJWT(param, "audience", "issuser", TokenConstant.ACCESS_TOKEN);
		AuthInfo authInfo = new AuthInfo();
		authInfo.setUserId(user.getId());
		authInfo.setTenantId(user.getTenantId());
		authInfo.setOauthId(userInfo.getOauthId());
		authInfo.setAccount(user.getAccount());
		authInfo.setUserName(user.getRealName());
		authInfo.setAuthority(Func.join(userInfo.getRoles()));
		System.out.println("->> 生成的token --> " + accessToken.getToken());
		authInfo.setAccessToken(accessToken.getToken());
		authInfo.setExpiresIn(accessToken.getExpire());
		authInfo.setRefreshToken(createRefreshToken(userInfo).getToken());
		authInfo.setTokenType(TokenConstant.BEARER);
		authInfo.setLicense(TokenConstant.LICENSE_NAME);

		return authInfo;
	}

	/**
	 * 创建refreshToken
	 *
	 * @param userInfo 用户信息
	 * @return refreshToken
	 */
	private static TokenInfo createRefreshToken(UserInfo userInfo) {
		User user = userInfo.getUser();
		Map<String, String> param = new HashMap<>(16);
		param.put(TokenConstant.TOKEN_TYPE, TokenConstant.REFRESH_TOKEN);
		param.put(TokenConstant.USER_ID, Func.toStr(user.getId()));
		return SecureUtil.createJWT(param, "audience", "issuser", TokenConstant.REFRESH_TOKEN);
	}

}
