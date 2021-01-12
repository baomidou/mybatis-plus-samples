package com.baomidou.samples;

import javax.annotation.Resource;

import com.baomidou.samples.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.samples.entity.User;
import com.baomidou.samples.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nieqiuqiu 2019/11/30
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class IdGeneratorTest {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserService userService;

    @Test
    public void test() {
        User user = new User();
        user.setName("靓仔");
        user.setAge(18);
        userMapper.insert(user);
        Assert.assertEquals(Long.valueOf(1L), user.getId());
    }

    /**
     * 批量插入
     */
    @Test
    public void testBatch() {
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            User user = new User();
            user.setName("靓仔" + i);
            user.setAge(18 + i);
            users.add(user);
        }
        boolean result = userService.saveBatch(users);
        Assert.assertEquals(true, result);
    }
}
