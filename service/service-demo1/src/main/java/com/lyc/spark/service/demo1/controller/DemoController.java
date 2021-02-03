package com.lyc.spark.service.demo1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/liveness")
    public ResponseEntity liveness() {
        return ResponseEntity.ok().body("OK----->>");
    }
}
