package com.baomidou.samples.execution.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.plugins.SqlExplainInterceptor;

/**
 * MybatisPlus配置文件
 * @author nieqiurong 2018/8/11 21:10.
 */
@Configuration
public class MybatisPlusConfig {


    @Bean
    public SqlExplainInterceptor sqlExplainInterceptor(){
        //启用执行分析插件
        SqlExplainInterceptor sqlExplainInterceptor = new SqlExplainInterceptor();
        sqlExplainInterceptor.setStopProceed(true);
        return sqlExplainInterceptor;
    }


}
