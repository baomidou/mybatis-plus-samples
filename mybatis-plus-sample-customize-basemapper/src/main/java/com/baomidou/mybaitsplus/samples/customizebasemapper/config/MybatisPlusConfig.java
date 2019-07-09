package com.baomidou.mybaitsplus.samples.customizebasemapper.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybaitsplus.samples.customizebasemapper.base.CustomizeSuperMapperSqlInjector;

/**
 * <p>
 * </p>
 *
 * @author K
 * @date 2019/7/9
 */
@Configuration
@MapperScan("com.baomidou.mybaitsplus.samples.customizebasemapper.mapper")
public class MybatisPlusConfig {

    @Bean
    public CustomizeSuperMapperSqlInjector customizeSuperMapperSqlInjector(){
        return new CustomizeSuperMapperSqlInjector();
    }

}
