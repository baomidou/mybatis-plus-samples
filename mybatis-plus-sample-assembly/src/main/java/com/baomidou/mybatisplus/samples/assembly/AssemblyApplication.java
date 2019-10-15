package com.baomidou.mybatisplus.samples.assembly;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.samples.assembly.entity.User;
import com.baomidou.mybatisplus.samples.assembly.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 1. cd target
 * 2. java -jar mybatis-plus-sample-assembly-0.0.1-SNAPSHOT.jar
 * 3. curl http://127.0.0.1:8080/test
 * @author nieqiuqiu
 */
@RestController
@SpringBootApplication
public class AssemblyApplication {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/")
    public Integer test(){
        User user = new User();
        return userMapper.selectCount(new LambdaQueryWrapper<>(user).select(User::getId, User::getName));
    }

    public static void main(String[] args) {
        SpringApplication.run(AssemblyApplication.class, args);
    }

}
