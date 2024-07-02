package com.eelex.usercenterbackend.service;

import com.eelex.usercenterbackend.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author Ee1ex
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2024-06-18 02:29:18
*/





public interface UserService extends IService<User> {
    /**
     *用户注册
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @param checkPassword 确认密码
     *                        planetCode
     * @return
     */
    long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode);

    /**
     * 用户登录
     *
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @return 脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户登录态键
     * @createDate 2024-06-18 02:29:18
     */
    String USER_LOGIN_STATE = "UserLoginState";

    /**
     * 获取脱敏后的用户信息
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);

    /**
     * 用户注销
     * @createDate 2024-06-18 02:29:18
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);




}
