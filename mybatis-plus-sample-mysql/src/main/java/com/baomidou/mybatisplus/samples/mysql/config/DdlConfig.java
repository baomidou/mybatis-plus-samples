package com.baomidou.mybatisplus.samples.mysql.config;

import mybatis.mate.ddl.DdlScript;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DdlConfig {

    /**
     * 注入脚本执行类，支持自定义执行脚本
     */
    @Bean
    public DdlScript ddlScript(DataSource dataSource) {
        return new DdlScript(dataSource);
    }
}
