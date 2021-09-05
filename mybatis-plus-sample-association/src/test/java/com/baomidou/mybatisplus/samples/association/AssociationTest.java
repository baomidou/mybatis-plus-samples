package com.baomidou.mybatisplus.samples.association;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.samples.association.entity.Company;
import com.baomidou.mybatisplus.samples.association.entity.User;
import com.baomidou.mybatisplus.samples.association.mapper.CompanyMapper;
import com.baomidou.mybatisplus.samples.association.service.IUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class AssociationTest {
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private IUserService userService;

    @Test
    public void testSelectList() {
        userService.list().forEach(t -> System.out.println(t.getCompany()));
    }

    @Test
    public void testInsert() {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 100; ++i) {
            Company cmp = new Company();
            cmp.setId(1L);
            User user = new User();
            user.setId(100L + i);
            user.setCompany(cmp);
            user.setName("Han Meimei" + i);
            user.setEmail(user.getName() + "@baomidou.com");
            user.setAge(18);
            userList.add(user);
        }
        userService.saveBatch(userList);
        userService.list().forEach(t -> System.out.println(t));
        testSelect();
        testUpdate();
    }


    private void testSelect() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("t.company_id", 1);
        int pageSize = 5;
        IPage<User> page = new Page<User>(1, pageSize);
        List<User> userList = userService.selectUserPage(page, wrapper);
        for (int i = 1; i <= page.getPages(); ++i) {
            page = new Page<User>(i, pageSize);
            userList = userService.selectUserPage(page, wrapper);
            System.out.printf("==========================>共%d条数据,当前显示第%d页，每页%d条，共%d页====================================>\n", page.getTotal(), page.getCurrent(), page.getSize(), page.getPages());
            userList.forEach(System.out::println);
        }
    }

    private void testUpdate() {
        System.out.println("=======================开始更新==================================================>");
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("company_id", 1);
        User user = new User();
        user.setName(new Date().getTime() + "");
        userService.update(user, wrapper);
        testSelect();
    }

    @Test
    public void testResultMapCollection() {
        Company company = companyMapper.testResultMapCollection();
        Assertions.assertTrue(3 == company.getUserList().size());
    }
}
