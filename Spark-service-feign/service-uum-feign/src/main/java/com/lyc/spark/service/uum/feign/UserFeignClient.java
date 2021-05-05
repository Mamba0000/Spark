package com.lyc.spark.service.uum.feign;


import com.lyc.spark.common.constant.ApplicationConstant;
import com.lyc.spark.core.common.api.CommonResult;
import com.lyc.spark.service.uum.entity.User;
import com.lyc.spark.service.uum.entity.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * User Feign接口类
 *
 */
@FeignClient(
        value = ApplicationConstant.APPLICATION_SPARK_SERVICE_UUM,
        fallback = UserFeignClientFallback.class
)
public interface UserFeignClient {

    String API_PREFIX = "/user";

    /**
     * 获取用户信息
     *
     * @param userId 用户id
     * @return
     */
    @GetMapping(API_PREFIX + "/user-info-by-id")
    CommonResult<UserInfo> userInfo(@RequestParam("userId") Long userId);

    /**
     * 获取用户信息
     *
     * @param tenantId 租户ID
     * @param account    账号
     * @param password   密码
     * @return
     */
    @GetMapping(API_PREFIX + "/user-info")
    CommonResult<UserInfo> userInfo(@RequestParam("tenantId") String tenantId, @RequestParam("account") String account, @RequestParam("password") String password);

    /**
     * 获取第三方平台信息
     *
     * @param userOauth 第三方授权用户信息
     * @return UserInfo
     */
//    @PostMapping(API_PREFIX + "/user-auth-info")
//    CommonResult<UserInfo> userAuthInfo(@RequestBody UserOauth userOauth);

    /**
     * 新建用户
     *
     * @param user 用户实体
     * @return
     */
    @PostMapping(API_PREFIX + "/save-user")
    CommonResult<Boolean> saveUser(@RequestBody User user);

}
