package com.zhopy.authjwtservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class AuthJwtServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthJwtServiceApplication.class, args);
    }

}
