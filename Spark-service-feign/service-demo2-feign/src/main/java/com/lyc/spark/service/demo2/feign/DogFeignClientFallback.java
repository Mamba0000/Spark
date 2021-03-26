package com.lyc.spark.service.demo2.feign;

import com.lyc.spark.service.demo2.entity.Dog;
import java.util.List;
import com.lyc.spark.core.common.api.*;

public class DogFeignClientFallback implements DogFeignClient{

    @Override
    public CommonResult<Dog> getDogByName(String name) {
        return CommonResult.fail("查询失败");
    }

    @Override
    public CommonResult<List<Dog>> getList() {
        return CommonResult.fail("查询失败");
    }

}
