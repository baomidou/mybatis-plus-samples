package com.baomidou.mybatisplus.samples.deluxe;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.samples.deluxe.entity.User;
import com.baomidou.mybatisplus.samples.deluxe.mapper.UserMapper;
import com.baomidou.mybatisplus.samples.deluxe.model.UserPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author miemie
 * @since 2018-08-13
 */
@SpringBootTest
class DeluxeTest {

    @Autowired
    private UserMapper mapper;

    @Test
    public void testPage() {
        System.out.println("------ 自定义 xml 分页 ------");
        UserPage selectPage = new UserPage(1, 5).setSelectInt(20);
        UserPage userPage = mapper.selectUserPage(selectPage);
        Assertions.assertSame(userPage, selectPage);
        System.out.println("总条数 ------> " + userPage.getTotal());
        System.out.println("当前页数 ------> " + userPage.getCurrent());
        System.out.println("当前每页显示数 ------> " + userPage.getSize());
        print(userPage.getRecords());

        System.out.println("------ baseMapper 自带分页 ------");
        Page<User> page = new Page<>(1, 5);
        IPage<User> userIPage = mapper.selectPage(page, new QueryWrapper<User>().eq("age", 20));
        Assertions.assertSame(userIPage, page);
        System.out.println("总条数 ------> " + userIPage.getTotal());
        System.out.println("当前页数 ------> " + userIPage.getCurrent());
        System.out.println("当前每页显示数 ------> " + userIPage.getSize());
        print(userIPage.getRecords());
    }

    @Test
    void testDelAll() {
        mapper.deleteAll();
    }

    @Test
    void testInsert() {
        User u = new User().setEmail("122@qq.com").setVersion(1).setDeleted(0);
        mapper.insert(u);

        u.setAge(18);
        mapper.updateById(u);
        u = mapper.selectById(u.getId());
        // version should be updated
        Assertions.assertEquals(2, u.getVersion().intValue());
    }

    @Test
    void testSelect() {
        System.out.println(mapper.selectById(1L));
    }

    private <T> void print(List<T> list) {
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(System.out::println);
        }
    }


    @Test
    void myInsertAll() {
        long id = 1008888L;
        User u = new User().setEmail("122@qq.com").setVersion(1).setDeleted(0).setId(id);
        mapper.myInsertAll(u);

        User user = mapper.selectById(id);
        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getCreateTime());
    }

    @Test
    void myInsertBatch() {
        long id = 1009991;
        List<User> batchList = new ArrayList<>(2);
        batchList.add(new User().setId(id++).setEmail("111@qq.com").setVersion(1).setDeleted(0));
        batchList.add(new User().setId(id).setEmail("112@qq.com").setVersion(1).setDeleted(0));
        mapper.mysqlInsertAllBatch(batchList);

        User user = mapper.selectById(1009991);
        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getCreateTime());
    }

    @Test
    void verifyGithub1532() {
        mapper.findList(new User().setName("a")).forEach(System.out::println);
    }

    @Test
    void testCustomSqlSegment() {
        QueryWrapper<User> ew = new QueryWrapper<>();
        ew.like("u.name", "Tom");
        List<User> list = mapper.customerSqlSegment(ew);
        Assertions.assertEquals(1, list.size());
    }
}
