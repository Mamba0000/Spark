package com.lyc.spark.service.demo.controller;

import com.lyc.spark.common.api.CommonResult;
import com.lyc.spark.core.redis.util.RedisUtil;
import com.lyc.spark.service.demo2.bean.Dog;
import com.lyc.spark.service.demo2.feign.DogFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
@RefreshScope
public class DemoControoller {

    @Resource
    RedisUtil redisUtil;

    @Resource
    DogFeignClient dogFeignClient;

    @RequestMapping("/test")
    public String test() {
        CommonResult<Dog> dog = dogFeignClient.getDogByName("mini dog");
        return "test robin" + dog.getData().getName() + redisUtil.get("name");
    }

}