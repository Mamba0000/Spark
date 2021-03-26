package com.lyc.spark.service.demo2.feign;

import com.lyc.spark.common.constant.ApplicationConstant;
import com.lyc.spark.service.demo2.entity.Dog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import com.lyc.spark.core.common.api.*;

/**
 * Feign接口类
 */
@FeignClient(
        // 调用哪个服务
        value = ApplicationConstant.APPLICATION_SPARK_SERVICE_DEMO2,
        // 失败回调
        fallback = DogFeignClientFallback.class
)
public interface DogFeignClient {

    // 服务路径
    String API_PREFIX = "/";

    /**
     * 拿到一条狗通过名字
     * @param name 狗名字
     * @return 一条狗
     */
    @GetMapping(API_PREFIX + "/getDogByName")
    CommonResult<Dog> getDogByName(@RequestParam("name") String name);

    /**
     * 获取所有的狗集合
     *
     * @return
     */
    @GetMapping(API_PREFIX + "/getDogList")
    CommonResult<List<Dog>> getList();

}
