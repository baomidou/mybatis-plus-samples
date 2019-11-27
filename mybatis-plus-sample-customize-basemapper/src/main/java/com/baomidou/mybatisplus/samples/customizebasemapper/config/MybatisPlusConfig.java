package com.baomidou.mybatisplus.samples.customizebasemapper.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.samples.customizebasemapper.base.CustomizeSuperMapperSqlInjector;

/**
 * <p>
 * </p>
 *
 * @author K
 * @date 2019/7/9
 */
@Configuration
@MapperScan("com.baomidou.mybatisplus.samples.customizebasemapper.mapper")
public class MybatisPlusConfig {

    @Bean
    public CustomizeSuperMapperSqlInjector customizeSuperMapperSqlInjector(){
        return new CustomizeSuperMapperSqlInjector();
    }

}
