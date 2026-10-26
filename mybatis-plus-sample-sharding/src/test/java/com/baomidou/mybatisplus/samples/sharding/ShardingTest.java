package com.baomidou.mybatisplus.samples.sharding;

import com.baomidou.mybatisplus.samples.sharding.entity.User;
import com.baomidou.mybatisplus.samples.sharding.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ShardingTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    void querySwitchesReplicasInTheSameGroupAndUpdateUsesWriter() {
        assertThat(userMapper.selectById(1L).getName()).isEqualTo("reader-b");
        assertThat(userMapper.selectById(2L).getName()).isEqualTo("reader-a");
        User user = new User();
        user.setId(1L);
        user.setName("updated");
        assertThat(userMapper.updateById(user)).isEqualTo(1);
        assertThat(userMapper.selectById(1L).getName()).isEqualTo("reader-b");
    }

    @Test
    void shardingAnnotationCanRouteToAnotherDatasourceGroup() {
        assertThat(userMapper.selectFromInventory().getName()).isEqualTo("inventory-group");
    }
}
