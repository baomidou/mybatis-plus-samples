package com.baomidou.samples.metainfo;

import com.baomidou.samples.metainfo.entity.User;
import com.baomidou.samples.metainfo.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 自动填充测试
 * @author nieqiurong 2018-08-10 23:47:02.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AutoFillTest {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AutoFillTest.class);
    
    @Autowired
    private UserMapper userMapper;
    
    @Test
    public void test(){
        User user = new User(null,"Tom",1,"tom@qq.com",null);
        userMapper.insert(user);
        LOGGER.info("query user:{}",userMapper.selectById(user.getId()));
        User beforeUser = userMapper.selectById(1L);
        LOGGER.info("before user:{}",beforeUser);
        beforeUser.setAge(12);
        userMapper.updateById(beforeUser);
        LOGGER.info("query user:{}",userMapper.selectById(1L));
    }
}
