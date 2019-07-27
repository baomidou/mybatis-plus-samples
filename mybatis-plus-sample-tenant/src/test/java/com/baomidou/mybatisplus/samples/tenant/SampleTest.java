package com.baomidou.mybatisplus.samples.tenant;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.samples.tenant.entity.User;
import com.baomidou.mybatisplus.samples.tenant.mapper.UserMapper;

/**
 * <p>
 * 多租户 Tenant 演示
 * </p>
 *
 * @author hubin
 * @since 2018-08-11
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleTest {

    @Resource
    private UserMapper mapper;

    @Test
    public void aInsert() {
        User user = new User();
        user.setName("一一");
        Assert.assertTrue(mapper.insert(user) > 0);
        user = mapper.selectById(user.getId());
        Assert.assertTrue(1 == user.getTenantId());
    }


    @Test
    public void bDelete() {
        Assert.assertTrue(mapper.deleteById(3L) > 0);
    }


    @Test
    public void cUpdate() {
        Assert.assertTrue(mapper.updateById(new User().setId(1L).setName("mp")) > 0);
    }

    @Test
    public void dSelect() {
        List<User> userList = mapper.selectList(null);
        userList.forEach(u -> Assert.assertTrue(1 == u.getTenantId()));
    }

    /**
     * 自定义SQL：默认也会增加多租户条件
     * 参考打印的SQL
     */
    @Test
    public void manualSqlTenantFilterTest() {
        System.out.println(mapper.myCount());
    }
}
