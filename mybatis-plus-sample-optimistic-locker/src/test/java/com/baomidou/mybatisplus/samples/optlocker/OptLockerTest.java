package com.baomidou.mybatisplus.samples.optlocker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Assert;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.samples.optlocker.entity.User;
import com.baomidou.mybatisplus.samples.optlocker.mapper.UserMapper;

/**
 * <p>
 * </p>
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
        Assert.assertEquals("Should update failed due to incorrect version(actually 1, but 0 passed in)", 0, userMapper.updateById(userUpdate));
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
        Assert.assertEquals("Should update success as no version passed in", 1, userMapper.updateById(userUpdate));
        User updated = userMapper.selectById(id);
        Assert.assertEquals("Version not changed", 1, updated.getVersion().intValue());
        Assert.assertEquals("Age updated", 19, updated.getAge().intValue());
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
        int count = userMapper.selectCount(ew);

        User entity = new User();
        entity.setAge(28);
        entity.setVersion(1);

        Assert.assertEquals("updated records should be same", count, userMapper.update(entity, null));
        ew = new QueryWrapper<>();
        ew.eq("version", 1);
        Assert.assertEquals("No records found with version=1", 0, userMapper.selectCount(ew).intValue());
        ew = new QueryWrapper<>();
        ew.eq("version", 2);
        Assert.assertEquals("All records with version=1 should be updated to version=2", count, userMapper.selectCount(ew).intValue());
    }

}
