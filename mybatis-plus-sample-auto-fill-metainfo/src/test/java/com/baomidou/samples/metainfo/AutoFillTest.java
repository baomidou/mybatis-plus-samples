package com.baomidou.samples.metainfo;

import static org.assertj.core.api.Assertions.assertThat;

import com.baomidou.samples.metainfo.entity.User;
import com.baomidou.samples.metainfo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * 自动填充测试
 *
 * @author nieqiurong 2018-08-10 23:47:02.
 */
@Slf4j
@SpringBootTest
class AutoFillTest {

    @Resource
    private UserMapper userMapper;

    @Test
    void testFieldAutoFill() {
        User user = new User(null, "Tom", 1, "tom@qq.com", null);
        userMapper.insert(user);
        User afterInsertedUser = userMapper.selectById(user.getId());
        log.info("new user:{}", afterInsertedUser);
        assertThat(afterInsertedUser)
            .extracting(User::getOperator)
            .isEqualTo("Jetty");

        afterInsertedUser.setAge(12);
        // 需要重置为null，以支持字段自动填充
        afterInsertedUser.setOperator(null);
        userMapper.updateById(afterInsertedUser);

        User afterUpdatedUser = userMapper.selectById(user.getId());
        log.info("update after user:{}", afterUpdatedUser);
        assertThat(afterUpdatedUser)
            .extracting(User::getOperator)
            .isEqualTo("Tom");
    }
}
