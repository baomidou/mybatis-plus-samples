package com.baomidou.samples.performance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;

/**
 * MybatisPlus配置文件
 * @author nieqiurong 2018/8/11 21:10.
 */
@Configuration
public class MybatisPlusConfig {


    @Bean
    public PerformanceInterceptor performanceInterceptor(){
        //启用性能分析插件
        return new PerformanceInterceptor();
    }


}
