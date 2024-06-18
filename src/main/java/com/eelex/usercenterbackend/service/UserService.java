package com.eelex.usercenterbackend.service;

import com.eelex.usercenterbackend.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Ee1ex
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2024-06-18 02:29:18
*/



public interface UserService extends IService<User> {
    /**
     *用户注释
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @param checkPassword 确认密码
     * @return
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);


}
