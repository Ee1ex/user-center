package com.eelex.usercenterbackend;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@SpringBootTest
class UserCenterBackendApplicationTests {

//    @Resource
//    private UserMapper userMapper;
    @Test
    void contextLoads() {

    }

    @Test
    void testInsert() throws NoSuchAlgorithmException {
        //测试对密码进行加密
        String password = "123456";
        String encryptPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        System.out.println(encryptPassword);

    }


}
