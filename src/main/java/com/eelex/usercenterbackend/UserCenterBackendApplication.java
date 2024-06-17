package com.eelex.usercenterbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.eelex.usercenterbackend.mapper")
public class UserCenterBackendApplication {

    public static void main(String[] args) {
        System.out.println("hello world");
        SpringApplication.run(UserCenterBackendApplication.class, args);
    }

}
