package com.lyc.spark.service.demo2.feign;

import com.lyc.spark.common.api.CommonResult;
import com.lyc.spark.service.demo2.bean.Dog;
import java.util.List;

public class DogFeignClientFallback implements DogFeignClient{

    @Override
    public CommonResult<Dog> getDogByName(String name) {
        return CommonResult.failed("查询失败");
    }

    @Override
    public CommonResult<List<Dog>> getList() {
        return CommonResult.failed("查询失败");
    }

}
