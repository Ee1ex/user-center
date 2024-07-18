package com.eelex.usercenterbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eelex.usercenterbackend.service.UserService;
import com.eelex.usercenterbackend.domain.User;
import com.eelex.usercenterbackend.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用户服务实现类
* @author Ee1ex
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2024-06-18 02:29:18
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Resource
    private UserMapper UserMapper; //注入可以直接用userMapper来操作数据库


    //盐值,混淆密码
    private static final String SALT = "eelex";

    //用户的登录态键
    public static final String USER_LOGIN_STATE = "userLoginState";


    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode) {
        // 1. 校验
        //非空
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, planetCode)) {
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

        //校验星球编号
        if (planetCode.length() > 5 ) {
            return -1;
        }


        // 账户不能包含特殊字符

        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return -1;
        }
        //不能包含空格
        if (userAccount.contains(" ")) {
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
        //星球编号不能重复
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("planetCode", planetCode);
        count = UserMapper.selectCount(queryWrapper);
        if (count > 0) {  //已经有人注册了
            return -1;
        }


        //2.对密码进行加密
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



    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1. 校验
        //修改为自定义异常
        //非空
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        //账户不小于4位
        if (userAccount.length() < 4) {
            return null;
        }
        //密码不小于8位
        if (userPassword.length() < 8 ) {
            return null;
        }

        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        //不能包含空格
        if (userAccount.contains(" ")) {
            return null;
        }
        if (matcher.find( )) {
            return null;
        }



        //2.对密码进行加密
        //这里和checkpassword做了区分
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        //账户不能重复,查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = UserMapper.selectOne(queryWrapper);
        //用户不存在
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            return null;
        }

        //3.用户脱敏
        User safetyUser = getSafetyUser(user);
        //4.记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);
        return user;


    }

    /**
     * 获取脱敏的用户信息
     * @param originUser
     * @return
     */
    @Override
 public User getSafetyUser(User originUser) {
     User safetyUser = new User();
     safetyUser.setId(originUser.getId());
     safetyUser.setUsername(originUser.getUsername());
     safetyUser.setUserAccount(originUser.getUserAccount());
     safetyUser.setAvatarUrl(originUser.getAvatarUrl());
     safetyUser.setGender(originUser.getGender());

     safetyUser.setPhone(originUser.getPhone());
     safetyUser.setEmail(originUser.getEmail());
     safetyUser.setUserStatus(originUser.getUserStatus());
     safetyUser.setCreateTime(originUser.getCreateTime());
     safetyUser.setUserRole(originUser.getUserRole());
     safetyUser.setPlanetCode(originUser.getPlanetCode());
     return safetyUser;
    }
    /**
     * 用户注销
     * @createDate 2024-06-18 02:29:18
     * @param request
     * @return
     */
    @Override
    public int userLogout(HttpServletRequest request) {
        //移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;

    }


}






