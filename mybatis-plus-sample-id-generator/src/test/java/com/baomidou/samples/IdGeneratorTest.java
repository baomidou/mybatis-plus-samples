package com.baomidou.samples;

import com.baomidou.samples.entity.User;
import com.baomidou.samples.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author nieqiuqiu 2019/11/30
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class IdGeneratorTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test(){
        User user = new User();
        user.setName("靓仔");
        user.setAge(18);
        userMapper.insert(user);
        Assert.assertEquals(Long.valueOf(66666L),user.getId());
    }

}
