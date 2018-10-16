package com.devloper.joker.mybatisplus.cascade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.devloper.joker.mybatisplus.cascade.entity.Role;
import com.devloper.joker.mybatisplus.cascade.entity.User;
import com.devloper.joker.mybatisplus.cascade.entity.UserRoleVO;
import com.devloper.joker.mybatisplus.cascade.mapper.UserMapper;
import com.devloper.joker.mybatisplus.cascade.support.JackSonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private UserMapper userMapper;

    @Test
    public void contextLoads() {
    }

    public String toJson(Object o) {
        return JackSonUtils.toJson(o);
    }

    @Test
    public void insertUserWithRolePlusTest() {
        User user = new User();
        user.setCreateTime(new Date());
        user.setUsername("wangerxiao-plus");
        user.setPassword("Adssad");
        user.setCreateTime(new Date());
        Role role = new Role();
        role.setId(2L);
        user.setRole(role);
        userMapper.insert(user);
        logger.info("insert user: {}", toJson(user));
    }

    @Test
    public void update() {
        userMapper.update(new User(),
                new UpdateWrapper<User>().set("username", "JOKER")
                        .set("role_id", null).eq("id",2L));
    }


    @Test
    public void customSelectPageWithXml() {
        Page<User> userPage = new Page<>(1, 5);
        IPage<User> result = userMapper.selectPageByCustomWithXml(userPage, new QueryWrapper<User>().eq("role_id", 2).eq("username", "joker"));
        logger.info("查询的列表数据为: {}", toJson(result));
    }

    @Test
    public void selectListByCustom() {
        List<User> result = userMapper.selectListByCustom(new QueryWrapper<User>().lambda().nested(it -> it.eq(User::getRole, 1).or().eq(User::getUsername, "joker")).
                or().eq(User::getUsername, "ss"));
        logger.info("查询的列表数据为: {}", toJson(result));

        result = userMapper.selectListByCustom(new QueryWrapper<User>().nested(it -> it.eq("role_id", 1).or().eq("username", "joker")).
                or().eq("role.name", "admin"));
        logger.info("查询的列表数据为: {}", toJson(result));
    }

    @Test
    public void selectPageByCustom() {
        Page<User> userPage = new Page<>(1, 2);
        IPage<User> result = userMapper.selectPageByCustom(userPage, new QueryWrapper<User>().lambda().nested(it -> it.eq(User::getRole, 1).or().eq(User::getUsername, "joker")).
                or().eq(User::getUsername, "ss").select(User::getUsername, User::getPassword));
        logger.info("查询的列表数据为: {}", toJson(result));
    }


    @Test
    public void findUserWithRoleByVoWithQuery() {
        Object results = userMapper.findUserWithRoleByVoWithQueryList(new QueryWrapper<UserRoleVO>().eq("role.id", 1L).eq("role.name", "admin"));
        logger.info("查询的列表数据为: {}", toJson(results));
    }
}
