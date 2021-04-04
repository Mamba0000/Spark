package com.lyc.spark.service.sentinel.demo.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.websocket.server.PathParam;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class DemoControoller {

    @GetMapping("/hello")
    // 定义热点规则时 需要注解埋点
    @SentinelResource("aa")
    public String hello(@RequestParam String id) {
        try {
            Thread.sleep(1000);
        }catch (Exception e) {

        }
//        int a = 10/0;
        System.out.println("---->"+id);
        return "hello word";
    }

    @GetMapping("/hello2")
    public String hello2(String id) {
        return "hello word2";
    }


}
