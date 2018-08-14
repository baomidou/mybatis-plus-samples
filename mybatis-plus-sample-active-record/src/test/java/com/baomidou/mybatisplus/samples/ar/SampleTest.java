package com.baomidou.mybatisplus.samples.ar;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.samples.ar.entity.User;

/**
 * <p>
 * Active Record 演示
 * </p>
 *
 * @author hubin
 * @since 2018-08-11
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleTest {

    @Test
    public void aInsert() {
        User user = new User();
        user.setName("咩咩");
        user.setAge(5);
        user.setEmail("miemie@mp.com");
        Assert.assertTrue(user.insert());
        // 成功直接拿会写的 ID
        System.err.println("\n插入成功 ID 为：" + user.getId());
    }


    @Test
    public void bDelete() {
        Assert.assertTrue(new User().setId(3L).deleteById());
        Assert.assertTrue(new User().delete(new QueryWrapper<User>()
                .lambda().eq(User::getName, "Sandy")));
    }


    @Test
    public void cUpdate() {
        Assert.assertTrue(new User().setId(1L).setEmail("ab@c.c").updateById());
        Assert.assertTrue(new User().update(new UpdateWrapper<User>().lambda()
                        .set(User::getAge, 3).eq(User::getId, 2)));
    }


    @Test
    public void dSelect() {
        Assert.assertEquals("ab@c.c", new User().setId(1L).selectById().getEmail());
        User user = new User().selectOne(new QueryWrapper<User>().lambda().eq(User::getId, 2));
        Assert.assertEquals("Jack", user.getName());
        Assert.assertTrue(3 == user.getAge());
    }
}
