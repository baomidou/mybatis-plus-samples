package com.baomidou.mybatisplus.samples.typehandler;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.samples.typehandler.entity.User;
import com.baomidou.mybatisplus.samples.typehandler.mapper.UserMapper;

/**
 * <p>
 * 内置 类型处理器 演示
 * </p>
 *
 * @author hubin
 * @since 2018-08-11
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleTest {

    @Resource
    private UserMapper userMapper;

    /**
     * 自定义类型处理器演示参考 mybatis-plus-sample-deluxe 模块
     */
    @Test
    public void test() {
        // 自己去观察打印 SQL 目前随机访问 user_2018  user_2019 表
        User Jone = userMapper.selectById(1);
        System.err.println(Jone.getName());

        User Jack = userMapper.selectById(1);
        System.err.println(Jack.getName());
    }
}
