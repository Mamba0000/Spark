package com.lyc.spark.auth.granter;

import com.lyc.spark.auth.util.TokenUtil;
import com.lyc.spark.core.auth.enums.UserEnum;
import com.lyc.spark.core.common.api.CommonResult;
import com.lyc.spark.core.common.constant.CacheNames;
import com.lyc.spark.core.common.exception.ServiceException;
import com.lyc.spark.core.redis.util.RedisUtil;
import com.lyc.spark.core.tool.DigestUtil;
import com.lyc.spark.core.tool.Func;
import com.lyc.spark.core.tool.StringUtil;
import com.lyc.spark.core.tool.WebUtil;
import com.lyc.spark.service.uum.entity.UserInfo;
import com.lyc.spark.service.uum.feign.UserFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;

/**
 * 验证码TokenGranter
 *
 */
@Component
@AllArgsConstructor
public class CaptchaTokenGranter implements ITokenGranter {

    public static final String GRANT_TYPE = "captcha";

    private UserFeignClient userClient;
    private RedisUtil redisUtil;

    @Override
    public UserInfo grant(TokenParameter tokenParameter) {
        HttpServletRequest request = WebUtil.getRequest();

        String key = request.getHeader(TokenUtil.CAPTCHA_HEADER_KEY);
        String code = request.getHeader(TokenUtil.CAPTCHA_HEADER_CODE);
        // 获取验证码
        String redisCode = String.valueOf(redisUtil.get(CacheNames.CAPTCHA_KEY + key));
        // 判断验证码
        if (code == null || !StringUtil.equalsIgnoreCase(redisCode, code)) {
            throw new ServiceException(TokenUtil.CAPTCHA_NOT_CORRECT);
        }

        String tenantId = tokenParameter.getArgs().getStr("tenantId");
        String account = tokenParameter.getArgs().getStr("account");
        String password = tokenParameter.getArgs().getStr("password");
        UserInfo userInfo = null;
        if (Func.isNoneBlank(account, password)) {
            // 获取用户类型
            String userType = tokenParameter.getArgs().getStr("userType");
            CommonResult<UserInfo> result;
            // 根据不同用户类型调用对应的接口返回数据，用户可自行拓展
            if (userType.equals(UserEnum.WEB.getName())) {
                result = userClient.userInfo(tenantId, account, DigestUtil.encrypt(password));
            } else if (userType.equals(UserEnum.APP.getName())) {
                result = userClient.userInfo(tenantId, account, DigestUtil.encrypt(password));
            } else {
                result = userClient.userInfo(tenantId, account, DigestUtil.encrypt(password));
            }
            userInfo = result.getData();
        }
        return userInfo;
    }

}

