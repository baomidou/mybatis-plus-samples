package com.baomidou.mybatisplus.samples.typehandler;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.samples.typehandler.entity.Currency;
import com.baomidou.mybatisplus.samples.typehandler.entity.User;
import com.baomidou.mybatisplus.samples.typehandler.entity.Wallet;
import com.baomidou.mybatisplus.samples.typehandler.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

/**
 * <p>
 * 内置 类型处理器 演示
 * </p>
 *
 * @author hubin
 * @since 2018-08-11
 */
@SpringBootTest
public class TypeHandlerTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * 自定义类型处理器演示
     */
    @Test
    public void test() {
        User Jone = userMapper.selectById(1);
        Assertions.assertEquals("Jone", Jone.getName());
        Assertions.assertEquals(2, Jone.getWallets().size());
        Assertions.assertEquals("微信钱包", Jone.getWallets().get(1).getName());

        User Jack = userMapper.selectById(2);
        Assertions.assertEquals("Jack", Jack.getName());
        Assertions.assertEquals(1, Jack.getWallets().size());
        Assertions.assertEquals("银联钱包", Jack.getWallets().get(0).getName());

        // wrapper typeHandler 测试
        LambdaUpdateWrapper<User> wrapper = Wrappers.<User>lambdaUpdate().set(User::getWallets, Arrays.asList(new Wallet("Tom",
                Arrays.asList(new Currency("RMB", 1000d)))), "typeHandler=com.baomidou.mybatisplus.samples.typehandler.WalletListTypeHandler");
        wrapper.eq(User::getId, 2L);
        Assertions.assertEquals(userMapper.update(new User().setAge(99), wrapper), 1);
        System.err.println(userMapper.selectById(2));

        // 演示 json 格式 Wrapper TypeHandler 查询
        User h2User = userMapper.selectOne(Wrappers.<User>lambdaQuery()
                .apply("name={0,typeHandler=" + UserNameJsonTypeHandler.class.getCanonicalName() + "}",
                        "{\"id\":101,\"name\":\"Jack\"}"));
        Assertions.assertNotNull(h2User);

        // 分页测试
        Page<User> userPage = userMapper.selectPage(new Page<>(1, 20), null);
        Assertions.assertEquals(userPage.getTotal(), 2);
        Assertions.assertEquals(userPage.getRecords().size(), 2);
    }
}
