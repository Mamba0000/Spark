package com.lyc.spark.service.demo2.feign;

import com.lyc.spark.core.common.api.CommonResult;
import com.lyc.spark.service.demo2.entity.Dog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DogFeignClientImpl implements DogFeignClient{

    @GetMapping("/getDogByName")
    @Override
    public CommonResult<Dog> getDogByName(String name) {
        return  CommonResult.data(new Dog("哈士奇"));
    }

    @GetMapping("/getDogList")
    @Override
    public CommonResult<List<Dog>> getList() {
        List<Dog> dogList = new ArrayList<>();
        dogList.add(new Dog("中国田园犬"));
        dogList.add(new Dog("拉布拉多"));
        return CommonResult.data(dogList);
    }
}
