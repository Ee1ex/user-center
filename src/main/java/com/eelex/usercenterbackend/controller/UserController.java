package com.eelex.usercenterbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eelex.usercenterbackend.domain.User;
import com.eelex.usercenterbackend.model.request.UserRegisterRequest;
import com.eelex.usercenterbackend.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.eelex.usercenterbackend.constant.UserConstant.ADMIN_ROLE;
import static com.eelex.usercenterbackend.constant.UserConstant.USER_LOGIN_STATE;

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
        String planetCode = userRegisterRequest.getPlanetCode();
        if (userAccount == null || userPassword == null || checkPassword == null|| planetCode == null) {
            return null;
        }//controller层校验 倾向于对请求本身的校验 不设计业务逻辑
        return userService.userRegister(userAccount, userPassword, checkPassword, planetCode);



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

    //查询接口
    @GetMapping( "/search")//比较复杂
     public List<User> searchUser(String username, HttpServletRequest request){
        //仅管理员可查询
        if (!isAdmin(request)){
            return new ArrayList<>();
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)){
            queryWrapper.like("username",username);
        }
        List<User> userList = userService.list(queryWrapper);
        return userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());


    }

    //删除接口
    @PostMapping( "/delete")
    public boolean deleteUser(@RequestBody Long id, HttpServletRequest request){
        //仅管理员可查询
        if (!isAdmin(request)){
            return false;
        }

        if (id <= 0){
            throw new RuntimeException("id错误");
        }
        return userService.removeById(id);
    }


    /**
     * 是否为管理员
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request){
        //仅管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User loginUser = (User) userObj;

        return loginUser != null && loginUser.getUserRole() == ADMIN_ROLE;

    }

    //用户注销接口
    @PostMapping( "/logout")
    public Integer userLogout(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return userService.userLogout(request);
    }



}
