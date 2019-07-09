package com.baomidou.mybaitsplus.samples.customizebasemapper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybaitsplus.samples.customizebasemapper.entity.User;
import com.baomidou.mybaitsplus.samples.customizebasemapper.mapper.UserMapper;

/**
 * <p>
 * </p>
 *
 * @author K
 * @date 2019/7/9
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomizeSuperMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void test() {
        User user = userMapper.findOne(1L);
        Assert.assertNotNull(user);
        System.out.println(user);
    }
}
