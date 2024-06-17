package com.eelex.usercenterbackend;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.eelex.usercenterbackend.mapper.User;
import com.eelex.usercenterbackend.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class UserCenterBackendApplicationTests {


    @Resource
    private UserMapper userMapper;
    @Test
    void contextLoads() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.isTrue(5 == userList.size(), "");
        userList.forEach(System.out::println);
    }

}
