package com.baomidou.mybatisplus.samples.kotlin.config

import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor
import org.mybatis.spring.annotation.MapperScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author nieqiurong
 */
@Configuration
@MapperScan("com.baomidou.mybatisplus.samples.kotlin.mapper")
open class MybatisPlusConfig {

    @Bean
    open fun paginationInnerInterceptor(): PaginationInnerInterceptor {
        return PaginationInnerInterceptor();
    }

}