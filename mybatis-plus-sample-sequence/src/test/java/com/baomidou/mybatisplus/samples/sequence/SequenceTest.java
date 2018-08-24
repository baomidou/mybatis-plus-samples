package com.baomidou.mybatisplus.samples.sequence;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.samples.sequence.entity.User;
import com.baomidou.mybatisplus.samples.sequence.mapper.UserMapper;

/**
 * <p>
 * </p>
 *
 * @author yuxiaobin
 * @date 2018/8/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SequenceTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void testInsert() {
        User user = new User();
        user.setAge(18);
        user.setEmail("test@baomidou.com");
        user.setName("sequence");
        userMapper.insert(user);
        Long id1 = user.getId();
        System.out.println(id1);
        Assert.assertTrue("sequence start with 1000", id1 >= 1000);
        user = new User();
        user.setAge(19);
        user.setEmail("test2@baomidou.com");
        user.setName("sequence2");
        userMapper.insert(user);
        Long id2 = user.getId();
        Assert.assertTrue("squence increment by 1", id2 - id1 == 1);
    }

}
