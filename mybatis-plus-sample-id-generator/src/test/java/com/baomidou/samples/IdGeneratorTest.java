package com.baomidou.samples;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.baomidou.samples.entity.User;
import com.baomidou.samples.mapper.UserMapper;
import com.baomidou.samples.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nieqiuqiu 2019/11/30
 */
@SpringBootTest
class IdGeneratorTest {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserService userService;

    @Test
    void test() {
        User user = new User();
        user.setName("靓仔");
        user.setAge(18);
        userMapper.insert(user);
        assertThat(user)
            .hasFieldOrPropertyWithValue("id", 1L);
    }

    /**
     * 批量插入
     */
    @Test
    void testBatch() {
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            User user = new User();
            user.setName("靓仔" + i);
            user.setAge(18 + i);
            users.add(user);
        }
        boolean result = userService.saveBatch(users);
        assertThat(result).isTrue();
    }
}
