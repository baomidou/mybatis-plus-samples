package com.baomidou.mybatisplus.samples.optlocker;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.samples.optlocker.entity.Person;
import com.baomidou.mybatisplus.samples.optlocker.mapper.PersonMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 乐观锁
 *
 * @author yuxiaobin
 * @since 2018/8/24
 */
@SpringBootTest
class OptLockerTestCase4LocalDateTime {
    @Autowired
    PersonMapper userMapper;

    LocalDateTime versionSnapshot;

    @BeforeEach
    public void init() {
        long nowSecond = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        versionSnapshot = LocalDateTime.ofEpochSecond(nowSecond - 60, 0, ZoneOffset.UTC);

    }

    @Order(0)
    @Test
    public void testUpdateByIdSucc() {
        Person user = new Person();
        user.setAge(18);
        user.setEmail("test@baomidou.com");
        user.setName("optlocker");

        user.setVersion(versionSnapshot);
        userMapper.insert(user);
        Long id = user.getId();

        Person userUpdate = new Person();
        userUpdate.setId(id);
        userUpdate.setAge(19);
        userUpdate.setVersion(versionSnapshot);
        assertThat(userMapper.updateById(userUpdate)).isEqualTo(1);

        Person retrieved = userMapper.selectById(id);
        long updatedSecond = retrieved.getVersion().toEpochSecond(ZoneOffset.UTC);
        long nowSeconds = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        assertThat(Math.abs(updatedSecond - nowSeconds)).isLessThan(2);
    }

    @Order(2)
    @Test
    public void testUpdateByIdFail() {
        Person user = new Person();
        user.setAge(18);
        user.setEmail("test@baomidou.com");
        user.setName("optlocker");
        user.setVersion(versionSnapshot);
        userMapper.insert(user);
        Long id = user.getId();

        Person userUpdate = new Person();
        userUpdate.setId(id);
        userUpdate.setAge(19);
        userUpdate.setVersion(LocalDateTime.now());
        // Should update failed due to incorrect version(actually 1, but 0 passed in)
        Assertions.assertEquals(0, userMapper.updateById(userUpdate));
    }

    /**
     * version=null， 能更新数据，但version不会更新
     */
    @Order(3)
    @Test
    public void testUpdateByIdSuccWithNoVersion() {
        Person user = new Person();
        user.setAge(18);
        user.setEmail("test@baomidou.com");
        user.setName("optlocker");
        user.setVersion(versionSnapshot);
        userMapper.insert(user);
        Long id = user.getId();

        Person userUpdate = new Person();
        userUpdate.setId(id);
        userUpdate.setAge(19);
        userUpdate.setVersion(null);
        // Should update success as no version passed in, and version will not change
        Assertions.assertEquals(1, userMapper.updateById(userUpdate));

        Person retrieved = userMapper.selectById(id);
        System.out.println("retrieved.getVersion()="+retrieved.getVersion());
        long updatedSecond = retrieved.getVersion().toEpochSecond(ZoneOffset.UTC);
        long nowSeconds = versionSnapshot.toEpochSecond(ZoneOffset.UTC);
        assertThat(Math.abs(updatedSecond - nowSeconds)).isLessThan(2);//version not changed
        // Age updated
        Assertions.assertEquals(19, retrieved.getAge().intValue());
    }

    /**
     * 批量更新带乐观锁
     * <p>
     * update(et,ew) et:必须带上version的值才会触发乐观锁
     */
    @Order(4)
    @Test
    public void testUpdateByEntitySucc() {
        QueryWrapper<Person> ew = new QueryWrapper<>();
        ew.eq("version", versionSnapshot);
        long count = userMapper.selectCount(ew);

        Person entity = new Person();
        entity.setAge(28);
        entity.setVersion(versionSnapshot);

        // updated records should be same
        Assertions.assertEquals(count, userMapper.update(entity, null));
        ew = new QueryWrapper<>();
        ew.eq("version", versionSnapshot);
        // No records found with version=1
        Assertions.assertEquals(0, userMapper.selectCount(ew).intValue());
        ew = new QueryWrapper<>();
        ew.ge("version", versionSnapshot);
        // All records with version=1 should be updated to version=2
        Assertions.assertEquals(count, userMapper.selectCount(ew).intValue());
    }

}
