package com.baomidou.mybatisplus.samples.optlocker.config;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * </p>
 *
 * @author yuxiaobin
 * @date 2018/8/24
 */
@Configuration
public class MybatisPlusOptLockerConfig {

    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
}
