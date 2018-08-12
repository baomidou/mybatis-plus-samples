package com.baomidou.mybatisplus.samples.pagination;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.samples.pagination.entity.User;
import com.baomidou.mybatisplus.samples.pagination.mapper.UserMapper;
import com.baomidou.mybatisplus.samples.pagination.model.MyPage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author miemie
 * @since 2018-08-10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PaginationTest {

    @Resource
    private UserMapper mapper;

    @Test
    public void tests() {
        System.err.println("----- baseMapper 自带分页 ------");
        Page<User> page = new Page<>(1, 5);
        IPage<User> userIPage = mapper.selectPage(page, new QueryWrapper<User>()
                .eq("age", 20).eq("name", "Jack"));
        Assert.assertSame(page, userIPage);
        System.out.println("总条数 ------> " + userIPage.getTotal());
        System.out.println("当前页数 ------> " + userIPage.getCurrent());
        System.out.println("当前每页显示数 ------> " + userIPage.getSize());
        print(userIPage.getRecords());


        System.err.println("----- 自定义 XML 分页 ------");
        MyPage<User> myPage = new MyPage<User>(1, 5).setSelectInt(20).setSelectStr("Jack");
        IPage<User> userMyPage = mapper.mySelectPage(myPage);
        Assert.assertSame(myPage, userMyPage);
        System.out.println("总条数 ------> " + userMyPage.getTotal());
        System.out.println("当前页数 ------> " + userMyPage.getCurrent());
        System.out.println("当前每页显示数 ------> " + userMyPage.getSize());
        print(userMyPage.getRecords());


//        System.err.println("----- 自定注解义分页 ------");
//        MyPage<User> atPage = new MyPage<User>(1, 5).setSelectInt(20).setSelectStr("Jack");
//        IPage<User> userAtPage = mapper.atSelectPage(atPage);
//        Assert.assertSame(atPage, userAtPage);
//        System.out.println("总条数 ------> " + userAtPage.getTotal());
//        System.out.println("当前页数 ------> " + userAtPage.getCurrent());
//        System.out.println("当前每页显示数 ------> " + userAtPage.getSize());
//        print(userAtPage.getRecords());
    }

    private <T> void print(List<T> list) {
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(System.out::println);
        }
    }
}
