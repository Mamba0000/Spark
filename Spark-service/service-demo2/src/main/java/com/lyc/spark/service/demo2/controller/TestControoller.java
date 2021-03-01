package com.lyc.spark.service.demo2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestControoller {

    @RequestMapping("/hello")
    public String f() {
        return "hello word 2";
    }
}
