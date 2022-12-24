package com.baomidou.mybatisplus.samples.optlocker;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.samples.optlocker.entity.User;
import com.baomidou.mybatisplus.samples.optlocker.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 乐观锁
 *
 * @author yuxiaobin
 * @since 2018/8/24
 */
@SpringBootTest
class OptLockerTest {
    @Autowired
    UserMapper userMapper;

    @Order(0)
    @Test
    public void testUpdateByIdSucc() {
        User user = new User();
        user.setAge(18);
        user.setEmail("test@baomidou.com");
        user.setName("optlocker");
        user.setVersion(1);
        userMapper.insert(user);
        Long id = user.getId();

        User userUpdate = new User();
        userUpdate.setId(id);
        userUpdate.setAge(19);
        userUpdate.setVersion(1);
        assertThat(userMapper.updateById(userUpdate)).isEqualTo(1);
        assertThat(userUpdate.getVersion()).isEqualTo(2);
    }

    @Order(1)
    @Test
    public void testUpdateByIdSuccFromDb() {
        User user = userMapper.selectById(1);
        int oldVersion = user.getVersion();
        int i = userMapper.updateById(user);
        assertThat(i).isEqualTo(1);
        assertThat(oldVersion + 1).isEqualTo(user.getVersion());
    }

    @Order(2)
    @Test
    public void testUpdateByIdFail() {
        User user = new User();
        user.setAge(18);
        user.setEmail("test@baomidou.com");
        user.setName("optlocker");
        user.setVersion(1);
        userMapper.insert(user);
        Long id = user.getId();

        User userUpdate = new User();
        userUpdate.setId(id);
        userUpdate.setAge(19);
        userUpdate.setVersion(0);
        // Should update failed due to incorrect version(actually 1, but 0 passed in)
        Assertions.assertEquals(0, userMapper.updateById(userUpdate));
    }

    @Order(3)
    @Test
    public void testUpdateByIdSuccWithNoVersion() {
        User user = new User();
        user.setAge(18);
        user.setEmail("test@baomidou.com");
        user.setName("optlocker");
        user.setVersion(1);
        userMapper.insert(user);
        Long id = user.getId();

        User userUpdate = new User();
        userUpdate.setId(id);
        userUpdate.setAge(19);
        userUpdate.setVersion(null);
        // Should update success as no version passed in
        Assertions.assertEquals(1, userMapper.updateById(userUpdate));
        User updated = userMapper.selectById(id);
        // Version not changed
        Assertions.assertEquals(1, updated.getVersion().intValue());
        // Age updated
        Assertions.assertEquals(19, updated.getAge().intValue());
    }

    /**
     * 批量更新带乐观锁
     * <p>
     * update(et,ew) et:必须带上version的值才会触发乐观锁
     */
    @Order(4)
    @Test
    public void testUpdateByEntitySucc() {
        QueryWrapper<User> ew = new QueryWrapper<>();
        ew.eq("version", 1);
        long count = userMapper.selectCount(ew);

        User entity = new User();
        entity.setAge(28);
        entity.setVersion(1);

        // updated records should be same
        Assertions.assertEquals(count, userMapper.update(entity, null));
        ew = new QueryWrapper<>();
        ew.eq("version", 1);
        // No records found with version=1
        Assertions.assertEquals(0, userMapper.selectCount(ew).intValue());
        ew = new QueryWrapper<>();
        ew.eq("version", 2);
        // All records with version=1 should be updated to version=2
        Assertions.assertEquals(count, userMapper.selectCount(ew).intValue());
    }

}
