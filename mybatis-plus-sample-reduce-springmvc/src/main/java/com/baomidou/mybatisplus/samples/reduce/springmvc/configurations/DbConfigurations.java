package com.zhenai.salary.configurations;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.AutoMapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author pj
 * @date 2019年3月29日
 */
@Configuration
@AutoMapperScan(basePackages = "com.zhenai.salary.mapper", beanPackages = "com.zhenai.salary.bean")
public class DbConfigurations {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
