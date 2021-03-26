
package com.lyc.spark.auth.granter;


import com.lyc.spark.service.uum.entity.UserInfo;

/**
 * 授权认证统一接口.
 *
 */
public interface ITokenGranter {

	/**
	 * 获取用户信息
	 *
	 * @param tokenParameter 授权参数
	 * @return UserInfo
	 */
	UserInfo grant(TokenParameter tokenParameter);

}
