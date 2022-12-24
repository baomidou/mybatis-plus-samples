package com.baomidou.mybatisplus.samples.reduce.springmvc;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.samples.reduce.springmvc.entity.City;
import com.baomidou.mybatisplus.samples.reduce.springmvc.entity.User;
import com.baomidou.mybatisplus.samples.reduce.springmvc.mapper.UserMapper;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring.xml"})
public class ReduceTest {
    @Autowired
    ApplicationContext context;
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assertions.assertEquals(5, userList.size());
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

    @Test
    public void testGeneratedMapper() {

        String cityMapperClassName = "cityMapper";
        BaseMapper cityMapper =(BaseMapper) context.getBean(cityMapperClassName);

        City city = (City) cityMapper.selectById(1);

        Assertions.assertEquals(city.getId().longValue(),1L);


        String districtMapperClassName = "districtMapper";
        boolean isContained = context.containsBean(districtMapperClassName);
        Assertions.assertFalse(isContained);

        //BaseMapper districtMapper =(BaseMapper) context.getBean(districtMapperClassName);
        //District district = (District) districtMapper.selectById(1);
        //Assertions.assertEquals(district.getId().longValue(),1L);

        String userMapperClassName = "userMapper";
        BaseMapper userMapper =(BaseMapper) context.getBean(userMapperClassName);

        userMapper.selectById(1);
        User user = (User) userMapper.selectById(1);
        Assertions.assertEquals(user.getId().longValue(),1L);

    }

}
