package com.baomidou.mybatisplus.samples.quickstart.springmvc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.samples.quickstart.springmvc.entity.User;
import com.baomidou.mybatisplus.samples.quickstart.springmvc.mapper.UserMapper;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring.xml"})
public class SpringMvcTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assertions.assertTrue(userList.size() >= 5);
        userList.forEach(System.out::println);
    }

    @Test
    public void testSequence() {
        User u = new User();
        u.setName("Tomcat");
        u.setAge(18);
        u.setEmail("kellylake@qq.com");
        userMapper.insert(u);

        Long id1 = u.getId();
        u = new User();
        u.setName("Tomcat2");
        userMapper.insert(u);
        Assertions.assertEquals(id1 + 1, u.getId().longValue());
    }

    @Test
    public void testCustomizedSql() {
        System.out.println("maxAge=" + userMapper.selectMaxAge());
    }

    @Test
    public void testPagination() {
        Page<User> page = new Page<>(1, 3);
        userMapper.selectPage(page, null);
        Assertions.assertTrue(page.getTotal() != 0, "total should not be 0");
        for (User u : page.getRecords()) {
            System.out.println(u);
        }
        Assertions.assertEquals(3, page.getRecords().size());
    }

}
