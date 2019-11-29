package com.baomidou.mybatisplus.samples.customizebasemapper;

import com.baomidou.mybatisplus.samples.customizebasemapper.entity.User;
import com.baomidou.mybatisplus.samples.customizebasemapper.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author K
 * @since 2019/7/9
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomizeSuperMapperTest {

    @Resource
    UserMapper mapper;

    @Test
    public void test() {
        final User user = mapper.findOne(1L);
        assertThat(user).isNotNull();

        List<User> list = mapper.lambdaQueryChain().select(User::getId).list();
        assertThat(list).isNotEmpty();
        list.forEach(i -> assertThat(i).isNotNull());
    }
}
