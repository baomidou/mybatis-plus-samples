package com.baomidou.mybatisplus.samples.assembly.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.samples.assembly.entity.User;
import com.baomidou.mybatisplus.samples.assembly.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author nieqiuqiu
 */
@RestController
@RequestMapping(value = "/")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    // 测试地址 http://localhost:8080/test
    @RequestMapping(value = "test")
    public String test(){
        User user = new User();
        user.setEmail("papapapap@qq.com");
        user.setAge(18);
        user.setName("啪啪啪");
        userService.save(user);
        List<User> list = userService.list(new LambdaQueryWrapper<>(new User()).select(User::getId, User::getName));
        list.forEach(u -> LOGGER.info("当前用户数据:{}", u));
        return "papapapap@qq.com";
    }

}
