
package com.lyc.spark.auth.granter;

import com.lyc.spark.core.auth.util.SecureUtil;
import com.lyc.spark.core.common.api.CommonResult;
import com.lyc.spark.core.common.constant.TokenConstant;
import com.lyc.spark.core.tool.Func;
import com.lyc.spark.service.uum.entity.UserInfo;
import com.lyc.spark.service.uum.feign.UserFeignClient;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * RefreshTokenGranter
 *
 */
@Component
@AllArgsConstructor
public class RefreshTokenGranter implements ITokenGranter {

	public static final String GRANT_TYPE = "refresh_token";

	private UserFeignClient userClient;

	@Override
	public UserInfo grant(TokenParameter tokenParameter) {
		String grantType = tokenParameter.getArgs().getStr("grantType");
		String refreshToken = tokenParameter.getArgs().getStr("refreshToken");
		UserInfo userInfo = null;
		if (Func.isNoneBlank(grantType, refreshToken) && grantType.equals(TokenConstant.REFRESH_TOKEN)) {
			Claims claims = SecureUtil.parseJWT(refreshToken);
			String tokenType = Func.toStr(Objects.requireNonNull(claims).get(TokenConstant.TOKEN_TYPE));
			if (tokenType.equals(TokenConstant.REFRESH_TOKEN)) {
				CommonResult<UserInfo> result = userClient.userInfo(Func.toLong(claims.get(TokenConstant.USER_ID)));
				userInfo = result.getData() ;
			}
		}
		return userInfo;
	}
}
