package com.baomidou.samples.metainfo;

import static org.assertj.core.api.Assertions.assertThat;

import com.baomidou.samples.metainfo.entity.User;
import com.baomidou.samples.metainfo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

/**
 * 自动填充测试
 *
 * @author nieqiurong 2018-08-10 23:47:02.
 */
@Slf4j
@SpringBootTest
class AutoFillTest {

    @Autowired
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

    @Test
    void testCustomMethod() {
        List<User> userList = Arrays.asList(new User(null, "testMyMethod1", 12, "aa@qq.com", null),
                new User(null, "testMyMethod1", 12, "aa@qq.com", null));
        userMapper.testMyMethod1(userList);
        userList = Arrays.asList(new User(null, "testMyMethod2", 12, "aa@qq.com", null),
                new User(null, "testMyMethod2", 12, "aa@qq.com", null));
        userMapper.testMyMethod2(userList);
        userList = Arrays.asList(new User(null, "testMyMethod3", 12, "aa@qq.com", null),
                new User(null, "testMyMethod3", 12, "aa@qq.com", null));
        userMapper.testMyMethod3(userList);
        userList = Arrays.asList(new User(null, "testMyMethod4", 12, "aa@qq.com", null),
                new User(null, "testMyMethod4", 12, "aa@qq.com", null));
        userMapper.testMyMethod4(userList);
        //----- 后面这里是3.5.4正式版后才开始支持的.....
        userList = Arrays.asList(new User(null, "testMyMethod5", 12, "aa@qq.com", null),
                new User(null, "testMyMethod5", 12, "aa@qq.com", null));
        userMapper.testMyMethod5(userList);
    }

}
