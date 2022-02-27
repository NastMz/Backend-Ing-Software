package com.zhopy.authjwtservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableConfigurationProperties
@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
public class AuthJwtServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthJwtServiceApplication.class, args);
    }

}
