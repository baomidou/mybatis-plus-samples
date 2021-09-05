package com.baomidou.mybatisplus.samples.ar;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.samples.ar.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <p>
 * Active Record 演示
 * </p>
 *
 * @author hubin
 * @since 2018-08-11
 */
@SpringBootTest
public class ActiveRecordTest {

    @Test
    public void aInsert() {
        User user = new User();
        user.setName("咩咩");
        user.setAge(5);
        user.setEmail("miemie@mp.com");
        Assertions.assertTrue(user.insert());
        // 成功可以直接获取 ID
        System.err.println("\n插入成功 ID 为：" + user.getId());
    }


    @Test
    public void bDelete() {
        Assertions.assertTrue(new User().setId(3L).deleteById());
        Assertions.assertTrue(new User().delete(new QueryWrapper<User>()
                .lambda().eq(User::getName, "Sandy")));
    }


    @Test
    public void cUpdate() {
        Assertions.assertTrue(new User().setId(1L).setEmail("ab@c.c").updateById());
        Assertions.assertTrue(new User().update(new UpdateWrapper<User>().lambda()
                        .set(User::getAge, 3).eq(User::getId, 2)));
    }


    @Test
    public void dSelect() {
        Assertions.assertEquals("ab@c.c", new User().setId(1L).selectById().getEmail());
        User user = new User().selectOne(new QueryWrapper<User>().lambda().eq(User::getId, 2));
        Assertions.assertEquals("Jack", user.getName());
        Assertions.assertTrue(3 == user.getAge());
    }
}
