package com.lyc.spark.service.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ApplicationServiceDemo {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationServiceDemo.class, args);
    }
}


