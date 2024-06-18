package com.eelex.usercenterbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eelex.usercenterbackend.service.UserService;
import com.eelex.usercenterbackend.domain.User;
import com.eelex.usercenterbackend.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sun.javafx.font.FontResource.SALT;

/**
 * 用户服务实现类
* @author Ee1ex
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2024-06-18 02:29:18
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Resource
    private UserMapper UserMapper; //注入可以直接用userMapper来操作数据库

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1. 校验
        //非空
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return -1;
        }
        //账户不小于4位
        if (userAccount.length() < 4) {
            return -1;
        }
        //密码不小于8位
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            return -1;
        }

        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return -1;
        }

        //密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            return -1;
        }

        //账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        Long count = UserMapper.selectCount(queryWrapper);
        if (count > 0) {  //已经有人注册了
            return -1;
        }

        //2.对密码进行加密
        final String SALT = "eelex";
        userPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        checkPassword = DigestUtils.md5DigestAsHex((SALT + checkPassword).getBytes());


        //3.向数据库插入用户数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(userPassword);
        boolean save = this.save(user);
        if (!save) {
            return -1;
        }
        return user.getId();




    }
}




