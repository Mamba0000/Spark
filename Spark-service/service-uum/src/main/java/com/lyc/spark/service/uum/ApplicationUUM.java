package com.lyc.spark.service.uum;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

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


