package com.lyc.spark.service.demo2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Demo2Controoller {

    @GetMapping("/hello")
    public String hello() {
        return "hello word 2";
    }
}
