package com.baomidou.mybatisplus.samples.deluxe.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author miemie
 * @since 2018-08-12
 */
@Configuration
@MapperScan("com.baomidou.mybatisplus.samples.deluxe.mapper")
public class MybatisPlusConfig {

    /**
     * 1.分页插件
     * 2.多租户
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
