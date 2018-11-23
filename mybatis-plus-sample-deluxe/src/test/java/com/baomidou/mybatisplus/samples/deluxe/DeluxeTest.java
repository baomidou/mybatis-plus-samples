package com.baomidou.mybatisplus.samples.deluxe;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.samples.deluxe.entity.User;
import com.baomidou.mybatisplus.samples.deluxe.mapper.UserMapper;
import com.baomidou.mybatisplus.samples.deluxe.model.UserPage;

/**
 * @author miemie
 * @since 2018-08-13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DeluxeTest {

    @Resource
    private UserMapper mapper;

    @Test
    public void testPage() {
        System.out.println("------ 自定义 xml 分页 ------");
        UserPage selectPage = new UserPage(1, 5).setSelectInt(20);
        UserPage userPage = mapper.selectUserPage(selectPage);
        Assert.assertSame(userPage, selectPage);
        System.out.println("总条数 ------> " + userPage.getTotal());
        System.out.println("当前页数 ------> " + userPage.getCurrent());
        System.out.println("当前每页显示数 ------> " + userPage.getSize());
        print(userPage.getRecords());


        System.out.println("------ baseMapper 自带分页 ------");
        Page<User> page = new Page<>(1, 5);
        IPage<User> userIPage = mapper.selectPage(page, new QueryWrapper<User>().eq("age", 20));
        Assert.assertSame(userIPage, page);
        System.out.println("总条数 ------> " + userIPage.getTotal());
        System.out.println("当前页数 ------> " + userIPage.getCurrent());
        System.out.println("当前每页显示数 ------> " + userIPage.getSize());
        print(userIPage.getRecords());
    }

    @Test
    public void testDelAll() {
        mapper.deleteAll();
    }

    @Test
    public void testInsert() {
        User u = new User().setEmail("122@qq.com").setVersion(1).setDeleted(0);
        mapper.insert(u);

        u.setAge(18);
        mapper.updateById(u);
        u = mapper.selectById(u.getId());
        Assert.assertEquals("version should be updated",2, u.getVersion().intValue());
    }

    @Test
    public void testSelect() {
        System.out.println(mapper.selectById(1L));
    }

    private <T> void print(List<T> list) {
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(System.out::println);
        }
    }
}
