package com.eelex.usercenterbackend.controller;

import com.eelex.usercenterbackend.domain.User;
import com.eelex.usercenterbackend.model.request.UserRegisterRequest;
import com.eelex.usercenterbackend.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户接口
 *
 * @author Eelex
 */
@RestController
@RequestMapping( "/user")
public class UserController {

    @Resource  //引用到类里
    private UserService userService;

    @PostMapping( "/register")
    public Long userRegister(@RequestBody  UserRegisterRequest userRegisterRequest)//能和前端的数据对得上
    {
        if (userRegisterRequest == null) {
            return null;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (userAccount == null || userPassword == null || checkPassword == null) {
            return null;
        }//controller层校验 倾向于对请求本身的校验 不设计业务逻辑
        return userService.userRegister(userAccount, userPassword, checkPassword);



    }

    @PostMapping( "/login")
    public User userLogin(@RequestBody  UserRegisterRequest UserLoginRequest, HttpServletRequest request)//能和前端的数据对得上
    {
        if (UserLoginRequest == null) {
            return null;
        }
        String userAccount = UserLoginRequest.getUserAccount();
        String userPassword = UserLoginRequest.getUserPassword();
        if (userAccount == null || userPassword == null ) {
            return null;
        }//controller层校验 倾向于对请求本身的校验 不设计业务逻辑

        return userService.userLogin(userAccount, userPassword, request);



    }





}
