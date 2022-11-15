package com.baomidou.mybatisplus.samples.typehandler;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.samples.typehandler.entity.Currency;
import com.baomidou.mybatisplus.samples.typehandler.entity.User;
import com.baomidou.mybatisplus.samples.typehandler.entity.Wallet;
import com.baomidou.mybatisplus.samples.typehandler.mapper.UserMapper;
import java.util.List;
import javax.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <p>
 * 内置 类型处理器 演示
 * </p>
 *
 * @author hubin
 * @since 2018-08-11
 */
@SpringBootTest
class TypeHandlerTest {

    @Resource
    private UserMapper userMapper;

    /**
     * 自定义类型处理器演示
     */
    @Test
    void testCommon() {
        User jone = userMapper.selectById(1);
        assertThat(jone)
            .hasFieldOrPropertyWithValue("name", "Jone");
        assertThat(jone.getWallets())
            .hasSize(2);
        assertThat(jone.getWallets().get(1))
            .hasFieldOrPropertyWithValue("name", "微信钱包");

        User jack = userMapper.selectById(2);
        assertThat(jack)
            .hasFieldOrPropertyWithValue("name", "Jack");
        assertThat(jack.getWallets())
            .hasSize(1)
            .first()
            .hasFieldOrPropertyWithValue("name", "银联钱包");

    }

    @Test
    void wrapperTypeHandler() {
        List<Wallet> wallets = singletonList(new Wallet("Tom",
            singletonList(new Currency("RMB", 1000d))));
        LambdaUpdateWrapper<User> wrapper = Wrappers.<User>lambdaUpdate()
            .set(User::getWallets,
                wallets,
                "typeHandler=com.baomidou.mybatisplus.samples.typehandler.WalletListTypeHandler")
            .set(User::getAge, 99)
            .eq(User::getId, 2L);
        assertThat(userMapper.update(null, wrapper))
            .isEqualTo(1);

        User user = userMapper.selectById(2L);
        assertThat(user)
            .extracting(User::getWallets)
            .isEqualTo(wallets);
    }

    @Test
    void pageTest() {
        // 分页测试
        Page<User> userPage = userMapper.selectPage(new Page<>(1, 20), null);
        assertThat(userPage)
            .extracting(Page::getTotal, page -> page.getRecords().size())
            .containsExactly(2L, 2);
    }

}
