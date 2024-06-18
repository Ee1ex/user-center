package com.eelex.usercenterbackend.service;
import java.util.Date;

import com.eelex.usercenterbackend.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {


    @Resource
    private UserService userService;

    @Test
    public void testAddUser() {
        User user = new User();
        user.setId(0L);
        user.setUsername("Eelex");
        user.setUserAccount("123");
        user.setAvatarUrl("");
        user.setGender(0);
        user.setUserPassword("123456");
        user.setPhone("1212");
        user.setEmail("2121@qq.com");
        user.setUserStatus(0);
        user.setIsDelete(0);
        user.setUserRole(0);


        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertEquals(true, result);


    }


    @Test
    void userRegister() {
        String userAccount = "Eelex";
        String userPassword = "";
        String checkPassword = "123456789";
        long result =userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);//断言

        userAccount = "Ee";
        result =userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);//断言

        userAccount = "Eelex";
        userPassword = "123456";
        result =userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);//断言

        userAccount = "y pi";
        userPassword = "12345678";
        result =userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);//断言


        checkPassword = "123456789";
        result =userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);//断言

        userAccount = "EelexXX";
        checkPassword = "12345678";
        result =userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);//断言

        userAccount = "Eelex";
        result =userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);//断言

    }
}