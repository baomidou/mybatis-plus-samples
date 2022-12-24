package com.baomidou.mybatisplus.samples.tenant;

import com.baomidou.mybatisplus.samples.tenant.entity.User;
import com.baomidou.mybatisplus.samples.tenant.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * <p>
 * 多租户 Tenant 演示
 * </p>
 *
 * @author hubin
 * @since 2018-08-11
 */
@SpringBootTest
public class TenantTest {
    @Autowired
    private UserMapper mapper;

    @Test
    public void aInsert() {
        User user = new User();
        user.setName("一一");
        Assertions.assertTrue(mapper.insert(user) > 0);
        user = mapper.selectById(user.getId());
        Assertions.assertTrue(1 == user.getTenantId());
    }


    @Test
    public void bDelete() {
        Assertions.assertTrue(mapper.deleteById(3L) > 0);
    }


    @Test
    public void cUpdate() {
        Assertions.assertTrue(mapper.updateById(new User().setId(1L).setName("mp")) > 0);
    }

    @Test
    public void dSelect() {
        List<User> userList = mapper.selectList(null);
        userList.forEach(u -> Assertions.assertTrue(1 == u.getTenantId()));
    }

    /**
     * 自定义SQL：默认也会增加多租户条件
     * 参考打印的SQL
     */
    @Test
    public void manualSqlTenantFilterTest() {
        System.out.println(mapper.myCount());
    }

    @Test
    public void testTenantFilter(){
        mapper.getAddrAndUser(null).forEach(System.out::println);
        mapper.getAddrAndUser("add").forEach(System.out::println);
        mapper.getUserAndAddr(null).forEach(System.out::println);
        mapper.getUserAndAddr("J").forEach(System.out::println);
    }
}
