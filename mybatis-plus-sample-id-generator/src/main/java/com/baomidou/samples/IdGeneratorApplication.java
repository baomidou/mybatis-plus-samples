package com.baomidou.samples;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.samples.incrementer.CustomIdGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 主键生成器启动器
 *
 * @author nieqiuqiu 2019/11/30
 */
@SpringBootApplication
public class IdGeneratorApplication {

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.getGlobalConfig().registerIdGenerator(IdType.ASSIGN_ID, new CustomIdGenerator());
    }

    public static void main(String[] args) {
        SpringApplication.run(IdGeneratorApplication.class, args);
    }

}
