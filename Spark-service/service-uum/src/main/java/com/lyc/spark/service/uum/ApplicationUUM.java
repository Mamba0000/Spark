package com.lyc.spark.service.uum;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

// feign 实例化
@EnableFeignClients({"com.lyc.spark.service.*.*"})
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan(basePackages = "com.**.mapper")
public class ApplicationUUM {

    public static void main(String[] args) {
        try {
            SpringApplication.run(ApplicationUUM.class, args);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}


