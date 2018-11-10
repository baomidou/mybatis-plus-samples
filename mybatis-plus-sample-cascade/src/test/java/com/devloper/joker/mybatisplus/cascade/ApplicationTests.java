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
        //依赖于User实体类中role字段注解上的el
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
    public void selectById() {
        //最终返回结果包含关联对象属性值依赖于@TableName(resultMap = "")
        User user = userMapper.selectById(1L);
        logger.info("result class: {}", user.getClass());
        logger.info("result: {}", toJson(user));
    }

    @Test
    public void selectCascadeById() {
        //最终返回结果包含关联对象属性值依赖于@TableName(resultMap = "")
        User user = userMapper.selectCascadeById(1L);
        logger.info("result class: {}", user.getClass());
        logger.info("result: {}", toJson(user));
    }

    @Test
    public void selectCascadeById2() {
        //需要设置@ResultMap
        User user = userMapper.selectCascadeById2( 1L);
        logger.info("result class: {}", user.getClass());
        logger.info("result: {}", toJson(user));
    }

    @Test
    public void selectByText() {
        List<User> result = userMapper.selectByText(UserMapper.JOIN_SQL, new QueryWrapper<User>().eq("role_id", 2).or().eq("user.id", 1L));
        logger.info("result: {}", toJson(result));

        result = userMapper.selectByText("SELECT * FROM user", new QueryWrapper<User>().eq("role_id", 2).or().eq("id", 1L));
        logger.info("result: {}", toJson(result));
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
                or().eq(User::getUsername, "ss").select(User::getUsername, User::getPassword)); //select未生效
        logger.info("查询的列表数据为: {}", toJson(result));
    }


    @Test
    public void findUserWithRoleByVoWithQuery() {
        Object results = userMapper.findUserWithRoleByVoWithQueryList(new QueryWrapper<UserRoleVO>().eq("role.id", 1L).eq("role.name", "admin"));
        logger.info("查询的列表数据为: {}", toJson(results));
    }


  @Test
    public void findUserWithRoleByVoWithQueryListAndColumns() {
        Object results = userMapper.findUserWithRoleByVoWithQueryListAndColumns(new QueryWrapper<UserRoleVO>().select("user.*", "role.name as role_name", "role.create_time").eq("role.id", 1L).eq("role.name", "admin"));
        logger.info("查询的列表数据为: {}", toJson(results));
    }

    @Test
    public void selectPageByCustomWithAssociationAndColumns() {
        Page<User> userPage = new Page<>(1, 2);

        Object results = userMapper.selectPageByCustomWithAssociationAndColumns(userPage, new QueryWrapper<User>().lambda().select().eq(User::getRole, 1L));
        logger.info("查询的列表数据为: {}", toJson(results));

        results = userMapper.selectPageByCustomWithAssociationAndColumns(userPage, new QueryWrapper<User>().lambda().select(User::getId, User::getUsername, User::getRole).eq(User::getRole, 1L));
        logger.info("查询的列表数据为: {}", toJson(results));

        results = userMapper.selectPageByCustomWithAssociationAndColumns(userPage, new QueryWrapper<User>().select("id", "username", "create_time", "role_id").eq("role_id", 1L));
        logger.info("查询的列表数据为: {}", toJson(results));
    }
}
