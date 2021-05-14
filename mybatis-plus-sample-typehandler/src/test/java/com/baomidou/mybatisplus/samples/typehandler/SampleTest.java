package com.baomidou.mybatisplus.samples.typehandler;

import javax.annotation.Resource;

import org.junit.Assert;
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
     * 自定义类型处理器演示
     */
    @Test
    public void test() {
        User Jone = userMapper.selectById(1);
        Assert.assertEquals("Jone", Jone.getName());
        Assert.assertEquals(2, Jone.getWallets().size());
        Assert.assertEquals("微信钱包", Jone.getWallets().get(1).getName());

        User Jack = userMapper.selectById(2);
        Assert.assertEquals("Jack", Jack.getName());
        Assert.assertEquals(1, Jack.getWallets().size());
        Assert.assertEquals("银联钱包", Jack.getWallets().get(0).getName());
    }
}
