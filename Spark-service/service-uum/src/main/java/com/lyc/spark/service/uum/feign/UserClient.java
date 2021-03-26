package com.lyc.spark.service.uum.feign;

import com.lyc.spark.core.common.api.CommonResult;
import com.lyc.spark.service.uum.entity.User;
import com.lyc.spark.service.uum.entity.UserInfo;
import com.lyc.spark.service.uum.service.IUserService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 用户服务Feign实现类
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@ApiIgnore
public class UserClient implements UserFeignClient {

    private IUserService service;

    @Override
    public CommonResult<UserInfo> userInfo(Long userId) {
        return CommonResult.data(service.userInfo(userId));
    }

    @Override
    @GetMapping(API_PREFIX + "/user-info")
    public CommonResult<UserInfo> userInfo(String tenantId, String account, String password) {
        return CommonResult.data(service.userInfo(tenantId, account, password));
    }

//    @Override
//    @PostMapping(API_PREFIX + "/user-auth-info")
//    public CommonResult<UserInfo> userAuthInfo(UserOauth userOauth) {
//        return CommonResult.success(service.userInfo(userOauth));
//    }

    @Override
    @PostMapping(API_PREFIX + "/save-user")
    public CommonResult<Boolean> saveUser(User user) {
        return CommonResult.data(service.save(user));
    }

}
