package com.baomidou.mybatisplus.samples.sequence;

import com.baomidou.mybatisplus.samples.sequence.entity.User;
import com.baomidou.mybatisplus.samples.sequence.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <p>
 * </p>
 *
 * @author yuxiaobin
 * @date 2018/8/24
 */
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
        Assertions.assertTrue(id1 >= 1000, "sequence start with 1000");
        user = new User();
        user.setAge(19);
        user.setEmail("test2@baomidou.com");
        user.setName("sequence2");
        userMapper.insert(user);
        Long id2 = user.getId();
        Assertions.assertTrue(id2 - id1 == 1, "squence increment by 1");
    }

}
