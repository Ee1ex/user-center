package com.eelex.usercenterbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eelex.usercenterbackend.common.BaseResponse;
import com.eelex.usercenterbackend.common.ErrorCode;
import com.eelex.usercenterbackend.common.ResultUtils;
import com.eelex.usercenterbackend.domain.User;
import com.eelex.usercenterbackend.model.request.UserRegisterRequest;
import com.eelex.usercenterbackend.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    public BaseResponse<Long> userRegister(@RequestBody  UserRegisterRequest userRegisterRequest)//能和前端的数据对得上
    {
        if (userRegisterRequest == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();
        if (userAccount == null || userPassword == null || checkPassword == null|| planetCode == null) {
            return null;
        }//controller层校验 倾向于对请求本身的校验 不设计业务逻辑
        long result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        //return new BaseResponse<>( 200, result, "注册成功", null);
        return ResultUtils.success(result);



    }

    @PostMapping( "/login")
    public BaseResponse<User> userLogin(@RequestBody  UserRegisterRequest UserLoginRequest, HttpServletRequest request)//能和前端的数据对得上
    {
        if (UserLoginRequest == null) {
            return null;
        }
        String userAccount = UserLoginRequest.getUserAccount();
        String userPassword = UserLoginRequest.getUserPassword();
        if (userAccount == null || userPassword == null ) {
            return null;
        }//controller层校验 倾向于对请求本身的校验 不设计业务逻辑

        User user = userService.userLogin(userAccount, userPassword, request);
        //return new BaseResponse<>( 200, user, "登录成功", null);
        return ResultUtils.success(user);

    }

    //查询接口
    @GetMapping( "/search")//比较复杂
     public BaseResponse<List<User>> searchUser(String username, HttpServletRequest request){
        //仅管理员可查询
        if (request == null){
            return null;
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)){
            queryWrapper.like("username",username);
        }
        List<User> userList = userService.list(queryWrapper);
        //return userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        return ResultUtils.success(userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList()));


    }

    //删除接口
    @PostMapping( "/delete")
    public BaseResponse<Boolean>  deleteUser(@RequestBody Long id, HttpServletRequest request){
        //仅管理员可查询
        if (!isAdmin(request).getData()){
            return null;
        }

        if (id <= 0){
            throw new RuntimeException("id错误");
        }
        //return userService.removeById(id);
        return ResultUtils.success(userService.removeById(id));
    }


    /**
     * 是否为管理员
     * @param request
     * @return true
     */
    private BaseResponse<Boolean> isAdmin(HttpServletRequest request){
        //仅管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User loginUser = (User) userObj;

        //return loginUser != null && loginUser.getUserRole() == ADMIN_ROLE;
        return ResultUtils.success(loginUser != null && loginUser.getUserRole() == ADMIN_ROLE);

    }

    //用户注销接口
    @PostMapping( "/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        //return userService.userLogout(request);
        return ResultUtils.success(userService.userLogout(request));
    }



}
