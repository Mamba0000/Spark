package com.lyc.spark.service.demo2;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ApplicationServiceDemo2 {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationServiceDemo2.class, args);
    }
}


