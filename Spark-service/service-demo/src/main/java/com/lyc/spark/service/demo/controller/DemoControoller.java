package com.lyc.spark.service.demo.controller;

import com.lyc.spark.core.auth.annotation.PreAuth;
import com.lyc.spark.core.redis.util.RedisUtil;
import com.lyc.spark.service.demo2.entity.Dog;
import com.lyc.spark.service.demo2.feign.DogFeignClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import com.lyc.spark.core.common.api.CommonResult;

@RestController
@RefreshScope
public class DemoControoller {

    @Resource
    RedisUtil redisUtil;

    @Resource
    DogFeignClient dogFeignClient;


    @RequestMapping("/test0")
    @PreAuth(hasPerm = "user:大范德萨发生的")
    public String test0() {
        return "test robin hood...";
    }


    @RequestMapping("/test")
    @PreAuth(hasPerm = "user:insert")
    public String test() {
        CommonResult<Dog> dog = dogFeignClient.getDogByName("mini dog");
        return "test robin"  + redisUtil.get("name");
    }



    @RequestMapping("/test2")
    @PreAuth(hasPerm = "user:大范德萨发生的")
    public String test2() {
        return "test robin hood...x";
    }

}
