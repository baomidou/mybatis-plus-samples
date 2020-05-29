package com.baomidou.mybatisplus.samples.pagehelper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.github.pagehelper.PageInterceptor;

/**
 * 两个分页插件都配置,不会冲突
 *
 * @author miemie
 * @date 2020/5/29
 */
@Configuration
public class MybatisPlusPageConfig {

    /**
     * mp的分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * pagehelper的分页插件
     */
    @Bean
    public PageInterceptor pageInterceptor() {
        return new PageInterceptor();
    }
}
