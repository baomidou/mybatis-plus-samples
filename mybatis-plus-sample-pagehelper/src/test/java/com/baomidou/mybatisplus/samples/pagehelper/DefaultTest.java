package com.baomidou.mybatisplus.samples.pagehelper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author miemie
 * @since 2020-05-29
 */
@SpringBootTest
class DefaultTest {

    @Autowired
    private UserMapper mapper;

    @Test
    void test() {
        Page<User> mpPage = mapper.selectPage(new Page<>(1, 2), Wrappers.<User>query().eq("id", 1));
        assertThat(mpPage.getTotal()).isEqualTo(1L);
        List<User> records = mpPage.getRecords();
        assertThat(records).isNotEmpty();
        assertThat(records.size()).isEqualTo(1);

        // pagehelper
        PageInfo<User> info = PageHelper.startPage(1, 2).doSelectPageInfo(() -> mapper.selectById(1));
        assertThat(info.getTotal()).isEqualTo(1L);
        List<User> list = info.getList();
        assertThat(list).isNotEmpty();
        assertThat(list.size()).isEqualTo(1);
    }

    @Test
    void testIn() {
        List<Long> ids = Arrays.asList(1L, 2L);
        Page<User> mpPage = mapper.selectPage(new Page<>(1, 5), Wrappers.<User>query().in("id", ids));
        assertThat(mpPage.getTotal()).isEqualTo(2L);
        List<User> records = mpPage.getRecords();
        assertThat(records).isNotEmpty();
        assertThat(records.size()).isEqualTo(2);

        // pagehelper
        PageInfo<User> info = PageHelper.startPage(1, 5)
                .doSelectPageInfo(() -> mapper.selectList(Wrappers.<User>query().in("id", ids)));
        assertThat(info.getTotal()).isEqualTo(2L);
        List<User> list = info.getList();
        assertThat(list).isNotEmpty();
        assertThat(list.size()).isEqualTo(2);
    }
}
