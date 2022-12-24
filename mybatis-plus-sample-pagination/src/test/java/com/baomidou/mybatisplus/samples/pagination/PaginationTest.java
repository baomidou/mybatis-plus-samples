package com.baomidou.mybatisplus.samples.pagination;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.samples.pagination.entity.User;
import com.baomidou.mybatisplus.samples.pagination.mapper.UserMapper;
import com.baomidou.mybatisplus.samples.pagination.model.MyPage;
import com.baomidou.mybatisplus.samples.pagination.model.ParamSome;
import com.baomidou.mybatisplus.samples.pagination.model.UserChildren;
import com.baomidou.mybatisplus.samples.pagination.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.assertj.core.util.Maps;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author miemie
 * @since 2018-08-10
 */
@Slf4j
@SpringBootTest
class PaginationTest {

    @Autowired
    private UserMapper mapper;

    @Test
    void lambdaPagination() {
        Page<User> page = new Page<>(1, 3);
        Page<User> result = mapper.selectPage(page, Wrappers.<User>lambdaQuery().ge(User::getAge, 1).orderByAsc(User::getAge));
        assertThat(result.getTotal()).isGreaterThan(3);
        assertThat(result.getRecords().size()).isEqualTo(3);
    }

    @Test
    void tests1() {
        log.error("----------------------------------baseMapper 自带分页-------------------------------------------------------");
        Page<User> page = new Page<>(1, 5);
        page.addOrder(OrderItem.asc("age"));
        Page<User> userIPage = mapper.selectPage(page, Wrappers.<User>lambdaQuery().eq(User::getAge, 20).like(User::getName, "Jack"));
        assertThat(page).isSameAs(userIPage);
        log.error("总条数 -------------> {}", userIPage.getTotal());
        log.error("当前页数 -------------> {}", userIPage.getCurrent());
        log.error("当前每页显示数 -------------> {}", userIPage.getSize());
        List<User> records = userIPage.getRecords();
        assertThat(records).isNotEmpty();

        log.error("----------------------------------json 正反序列化-------------------------------------------------------");
        String json = JSON.toJSONString(page);
        log.info("json ----------> {}", json);
        Page<User> page1 = JSON.parseObject(json, new TypeReference<Page<User>>() {
        });
        List<User> records1 = page1.getRecords();
        assertThat(records1).isNotEmpty();
        assertThat(records1.get(0).getClass()).isEqualTo(User.class);

        log.error("----------------------------------自定义 XML 分页-------------------------------------------------------");
        MyPage<User> myPage = new MyPage<User>(1, 5).setSelectInt(20).setSelectStr("Jack");
        ParamSome paramSome = new ParamSome(20, "Jack");
        MyPage<User> userMyPage = mapper.mySelectPage(myPage, paramSome);
        assertThat(myPage).isSameAs(userMyPage);
        log.error("总条数 -------------> {}", userMyPage.getTotal());
        log.error("当前页数 -------------> {}", userMyPage.getCurrent());
        log.error("当前每页显示数 -------------> {}", userMyPage.getSize());
    }

    @Test
    void tests2() {
        /* 下面的 left join 不会对 count 进行优化,因为 where 条件里有 join 的表的条件 */
        MyPage<UserChildren> myPage = new MyPage<>(1, 5);
        myPage.setSelectInt(18).setSelectStr("Jack");
        MyPage<UserChildren> userChildrenMyPage = mapper.userChildrenPage(myPage);
        List<UserChildren> records = userChildrenMyPage.getRecords();
        records.forEach(System.out::println);

        /* 下面的 left join 会对 count 进行优化,因为 where 条件里没有 join 的表的条件 */
        myPage = new MyPage<UserChildren>(1, 5).setSelectInt(18);
        userChildrenMyPage = mapper.userChildrenPage(myPage);
        records = userChildrenMyPage.getRecords();
        records.forEach(System.out::println);
    }

    private <T> void print(List<T> list) {
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(System.out::println);
        }
    }


    @Test
    void testMyPageMap() {
        MyPage<User> myPage = new MyPage<User>(1, 5).setSelectInt(20).setSelectStr("Jack");
        mapper.mySelectPageMap(myPage, Maps.newHashMap("name", "%a"));
        myPage.getRecords().forEach(System.out::println);
    }

    @Test
    void testMap() {
        mapper.mySelectMap(Maps.newHashMap("name", "%a")).forEach(System.out::println);
    }

    @Test
    void myPage() {
        MyPage<User> page = new MyPage<>(1, 5);
        page.setName("a");
        mapper.myPageSelect(page).forEach(System.out::println);
    }

    @Test
    void iPageTest() {
        IPage<User> page = new Page<User>(1, 5) {
            private String name = "%";

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        };

        List<User> list = mapper.iPageSelect(page);
        System.out.println("list.size=" + list.size());
        System.out.println("page.total=" + page.getTotal());
    }

    /**
     * 只查询当前页的记录，不查询总记录数
     */
    @Test
    void currentPageListTest() {
        //使用三参数的构造器创建Page对象
        //第三个参数isSearchCount：传true则查询总记录数;传false则不查询总记录数（既不进行count查询）
        Page<User> page = new Page<>(1,3,false);
        Page<User> result = mapper.selectPage(page, Wrappers.<User>lambdaQuery().ge(User::getAge, 20));
        assertThat(result.getRecords().size()).isEqualTo(3);
        //因为没有进行count查询，total值为0
        assertThat(result.getTotal()).isEqualTo(0);
    }

    @Test
    void rowBoundsTest() {
        RowBounds rowBounds = new RowBounds(0, 5);
        List<User> list = mapper.rowBoundList(rowBounds, Maps.newHashMap("name", "%"));
        System.out.println("list.size=" + list.size());
    }

    @Test
    void selectAndGroupBy() {
        LambdaQueryWrapper<User> lq = new LambdaQueryWrapper<>();
        lq.select(User::getAge).groupBy(User::getAge);
        for (User user : mapper.selectList(lq)) {
            System.out.println(user.getAge());
        }
    }

    @Autowired
    IUserService userService;

    @Test
    void lambdaPageTest() {
        LambdaQueryChainWrapper<User> wrapper2 = userService.lambdaQuery();
        wrapper2.like(User::getName, "a");
        userService.page(new Page<>(1, 10), wrapper2.getWrapper()).getRecords().forEach(System.out::print);
    }

    @Test
    void test() {
        userService.lambdaQuery().like(User::getName, "a").list().forEach(System.out::println);

        Page page = userService.lambdaQuery().like(User::getName, "a").page(new Page<>(1, 10));
        page.getRecords().forEach(System.out::println);
    }
}
