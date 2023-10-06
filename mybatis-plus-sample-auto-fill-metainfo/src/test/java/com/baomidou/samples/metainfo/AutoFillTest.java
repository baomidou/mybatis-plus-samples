package com.baomidou.samples.metainfo;

import com.baomidou.samples.metainfo.entity.User;
import com.baomidou.samples.metainfo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

/**
 * 自动填充测试
 *
 * @author nieqiurong 2018-08-10 23:47:02.
 */
@Slf4j
@SpringBootTest
public class AutoFillTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test() {
        User user = new User(null, "Tom", 1, "tom@qq.com", null);
        userMapper.insert(user);
        log.info("query user:{}", userMapper.selectById(user.getId()));
        User beforeUser = userMapper.selectById(1L);
        log.info("before user:{}", beforeUser);
        beforeUser.setAge(12);
        userMapper.updateById(beforeUser);
        log.info("query user:{}", userMapper.selectById(1L));
    }

    @Test
    void testCustomMethod() {
        List<User> userList = Arrays.asList(new User(null, "testMyMethod1", 12, "aa@qq.com", null),
                new User(null, "testMyMethod1", 12, "aa@qq.com", null));
        userMapper.testMyMethod1(userList);
        userList = Arrays.asList(new User(null, "testMyMethod2", 12, "aa@qq.com", null),
                new User(null, "testMyMethod2", 12, "aa@qq.com", null));
        userMapper.testMyMethod2(userList);
        userList = Arrays.asList(new User(null, "testMyMethod3", 12, "aa@qq.com", null),
                new User(null, "testMyMethod3", 12, "aa@qq.com", null));
        userMapper.testMyMethod3(userList);
        userList = Arrays.asList(new User(null, "testMyMethod4", 12, "aa@qq.com", null),
                new User(null, "testMyMethod4", 12, "aa@qq.com", null));
        userMapper.testMyMethod4(userList);
        //----- 后面这里是3.5.4正式版后才开始支持的.....
        userList = Arrays.asList(new User(null, "testMyMethod5", 12, "aa@qq.com", null),
                new User(null, "testMyMethod5", 12, "aa@qq.com", null));
        userMapper.testMyMethod5(userList);
    }

}
