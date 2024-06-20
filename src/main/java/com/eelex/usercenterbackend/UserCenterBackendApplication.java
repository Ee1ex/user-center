package com.eelex.usercenterbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 主类（项目启动入口）
 *
 */
@SpringBootApplication
@MapperScan("com.eelex.usercenterbackend.mapper")
public class UserCenterBackendApplication {

    public static void main(String[] args) {

        SpringApplication.run(UserCenterBackendApplication.class, args);
    }

}
