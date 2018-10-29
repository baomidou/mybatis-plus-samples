package com.devloper.joker.mybatisplus.cascade.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.devloper.joker.mybatis.plus.query.core.QueryConfigProperty;
import com.devloper.joker.mybatis.plus.query.core.QuerySupportMethod;
import com.devloper.joker.mybatis.plus.query.core.QuerySupportSqlInjector;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@MapperScan("com.devloper.joker.*.cascade.mapper")
public class MybatisPlusConfig {

    @Bean
    public PerformanceInterceptor performanceInterceptor(){
        PerformanceInterceptor interceptor = new PerformanceInterceptor();
        //格式化sql语句
        Properties properties = new Properties();
        properties.setProperty("format", "true");
        interceptor.setProperties(properties);
        return interceptor;
    }

    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     *  query support 配置
     * @return
     */

    @ConfigurationProperties(prefix = "mybatis-plus-query")
    @Bean
    public QueryConfigProperty queryConfigProperty() {
        return new QueryConfigProperty();
    }

    @Bean
    public QuerySupportMethod querySupportMethod() {
        return new QuerySupportMethod();
    }

    @Bean
    public QuerySupportSqlInjector querySupportSqlInjector() {
        return new QuerySupportSqlInjector();
    }
}
