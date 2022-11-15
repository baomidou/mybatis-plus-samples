package com.baomidou.mybatisplus.samples.wrapper;

import static com.baomidou.mybatisplus.core.toolkit.CollectionUtils.isNotEmpty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.samples.wrapper.entity.User;
import com.baomidou.mybatisplus.samples.wrapper.mapper.UserMapper;
import java.util.List;
import javax.annotation.Resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author miemie
 * @since 2018-08-10
 */
@SpringBootTest
class WrapperTest {

    private QueryWrapper<User> userQueryWrapper;
    private LambdaQueryWrapper<User> userLambdaQueryWrapper;

    @Resource
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userQueryWrapper = new QueryWrapper<>();
        userLambdaQueryWrapper = new LambdaQueryWrapper<User>();
    }

    @Test
    @DisplayName("普通查询")
    void testGeneralQuery() {
        List<User> plainUsers = userMapper.selectList(userQueryWrapper.eq("role_id", 2L));
        List<User> lambdaUsers = userMapper.selectList(
            userLambdaQueryWrapper.eq(User::getRoleId, 2L));
        assertEquals(plainUsers.size(), lambdaUsers.size());
        print(plainUsers);
    }

    @Test
    @DisplayName("带子查询(sql注入)")
    void testQueryWithSub() {
        List<User> plainUsers = userMapper.selectList(userQueryWrapper
            .inSql("role_id", "select id from role where id = 2"));
        List<User> lambdaUsers = userMapper.selectList(userLambdaQueryWrapper
            .inSql(User::getRoleId, "select id from role where id = 2"));
        assertEquals(plainUsers.size(), lambdaUsers.size());
        print(plainUsers);
    }

    @Test
    @DisplayName("带嵌套查询条件")
    void testWithNestedQueryConditions() {
        List<User> plainUsers = userMapper.selectList(userQueryWrapper
            .nested(i -> i.eq("role_id", 2L).or().eq("role_id", 3L))
            .and(i -> i.ge("age", 20)));
        List<User> lambdaUsers = userMapper.selectList(userLambdaQueryWrapper
            .nested(i -> i.eq(User::getRoleId, 2L).or().eq(User::getRoleId, 3L))
            .and(i -> i.ge(User::getAge, 20)));
        assertEquals(plainUsers.size(), lambdaUsers.size());
        print(plainUsers);
    }

    @Test
    @DisplayName("自定义(sql注入)")
    void testCustomSqlInjection() {

        // 方式一
        List<User> plainUsers = userMapper.selectList(userQueryWrapper
            .apply("role_id = 2"));

        // 方式二
        List<User> plainUsers2 = userMapper.selectList(userQueryWrapper
            .apply("role_id = {0}", 2));

        print(plainUsers);
        assertEquals(plainUsers.size(), plainUsers2.size());
    }

    @Test
    @DisplayName("条件更新")
    void tests() {
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.set("email", null);
        userUpdateWrapper.eq("id", 4);

        userMapper.update(null, userUpdateWrapper);
        User u4 = userMapper.selectById(4);
        assertThat(u4.getEmail()).isNull();
    }


    private <T> void print(List<T> list) {
        if (isNotEmpty(list)) {
            list.forEach(System.out::println);
        }
    }

    /**
     * SELECT id,name,age,email,role_id FROM user WHERE ( 1 = 1 ) AND ( ( name = ? AND age = ? ) OR
     * ( name = ? AND age = ? ) )
     */
    @Test
    void testSql() {
        userQueryWrapper.and(i -> i.eq("1", 1))
            .nested(i ->
                i.and(j -> j.eq("name", "a").eq("age", 2))
                    .or(j -> j.eq("name", "b").eq("age", 2)));
        List<User> users = userMapper.selectList(userQueryWrapper);
        assertThat(users).isEmpty();
    }

    /**
     * SELECT id,name FROM user WHERE (age BETWEEN ? AND ?) ORDER BY role_id ASC,id ASC
     */
    @Test
    void testSelect() {
        userQueryWrapper
            .select("id", "name")
            .between("age", 20, 25)
            .orderByAsc("role_id", "id");
        List<User> plainUsers = userMapper.selectList(userQueryWrapper);

        userLambdaQueryWrapper
            .select(User::getId, User::getName)
            .between(User::getAge, 20, 25)
            .orderByAsc(User::getRoleId, User::getId);
        List<User> lambdaUsers = userMapper.selectList(userLambdaQueryWrapper);

        print(plainUsers);
        assertEquals(plainUsers.size(), lambdaUsers.size());
    }
}
