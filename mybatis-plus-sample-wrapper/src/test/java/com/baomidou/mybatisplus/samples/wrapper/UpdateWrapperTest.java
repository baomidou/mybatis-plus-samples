package com.baomidou.mybatisplus.samples.wrapper;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.samples.wrapper.entity.User;
import com.baomidou.mybatisplus.samples.wrapper.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author sundongkai
 * @since 2021-02-04
 */
@SpringBootTest
public class UpdateWrapperTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * UPDATE user SET age=?, email=? WHERE (name = ?)
     */
    @Test
    public void tests() {

        //方式一：
        User user = new User();
        user.setAge(29);
        user.setEmail("test3update@baomidou.com");

        userMapper.update(user,new UpdateWrapper<User>().eq("name","Tom"));

        //方式二：
        //不创建User对象
        userMapper.update(null,new UpdateWrapper<User>()
                .set("age",29).set("email","test3update@baomidou.com").eq("name","Tom"));

    }

    /**
     * 使用lambda条件构造器
     * UPDATE user SET age=?, email=? WHERE (name = ?)
     */
    @Test
    public void testLambda() {

        //方式一：
        User user = new User();
        user.setAge(29);
        user.setEmail("test3update@baomidou.com");

        userMapper.update(user,new LambdaUpdateWrapper<User>().eq(User::getName,"Tom"));

        //方式二：
        //不创建User对象
        userMapper.update(null,new LambdaUpdateWrapper<User>()
                .set(User::getAge,29).set(User::getEmail,"test3update@baomidou.com").eq(User::getName,"Tom"));

    }


}
