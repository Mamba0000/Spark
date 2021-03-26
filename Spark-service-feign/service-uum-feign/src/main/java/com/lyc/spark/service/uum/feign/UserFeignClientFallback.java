package com.lyc.spark.service.uum.feign;


import com.lyc.spark.core.common.api.CommonResult;
import com.lyc.spark.service.uum.entity.User;
import com.lyc.spark.service.uum.entity.UserInfo;
import org.springframework.stereotype.Component;

/**
 * Feign失败配置
 *
 * @author Chill
 */
@Component
public class UserFeignClientFallback implements UserFeignClient {

    @Override
    public CommonResult<UserInfo> userInfo(Long userId) {
        return CommonResult.fail("未获取到账号信息");
    }

    @Override
    public CommonResult<UserInfo> userInfo(String tenantId, String account, String password) {
        return CommonResult.fail("未获取到账号信息");
    }

//    @Override
//    public CommonResult<UserInfo> userAuthInfo(UserOauth userOauth) {
//        return CommonResult.fail("未获取到账号信息");
//    }

    @Override
    public CommonResult<Boolean> saveUser(User user) {
        return CommonResult.fail("创建用户失败");
    }
}
