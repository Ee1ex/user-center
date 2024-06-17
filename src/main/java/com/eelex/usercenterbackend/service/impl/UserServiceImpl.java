package com.eelex.usercenterbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eelex.usercenterbackend.service.UserService;
import com.eelex.usercenterbackend.domain.User;
import com.eelex.usercenterbackend.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author Ee1ex
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2024-06-18 02:29:18
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

}




