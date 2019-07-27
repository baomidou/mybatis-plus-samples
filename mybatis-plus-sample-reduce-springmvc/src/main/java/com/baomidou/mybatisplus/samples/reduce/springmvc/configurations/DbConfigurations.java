package com.baomidou.mybatisplus.samples.reduce.springmvc.configurations;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.AutoMapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author  alan2lin
 * @date 2019年07月04日
 */
@Configuration
@AutoMapperScan(basePackages = "com.baomidou.mybatisplus.samples.reduce.springmvc.mapper", beanPackages = "com.baomidou.mybatisplus.samples.reduce.springmvc.entity",superMapperClassName = "com.baomidou.mybatisplus.samples.reduce.springmvc.mapper.MyBaseMapper",excludedBeans = "com.baomidou.mybatisplus.samples.reduce.springmvc.entity.D*")
public class DbConfigurations {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
