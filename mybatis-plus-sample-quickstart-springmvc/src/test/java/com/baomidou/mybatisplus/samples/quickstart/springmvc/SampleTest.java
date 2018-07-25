package com.baomidou.mybatisplus.samples.quickstart.springmvc;

import com.baomidou.mybatisplus.samples.quickstart.springmvc.entity.User;
import com.baomidou.mybatisplus.samples.quickstart.springmvc.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring.xml"})
public class SampleTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectAll() {
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }

}
