package com.baomidou.mybatisplus.samples.sequence.config;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.incrementer.H2KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * </p>
 *
 * @author yuxiaobin
 * @date 2018/8/24
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * sequence主键，需要配置一个主键生成器
     * 配合实体类注解 {@link KeySequence} + {@link TableId} type=INPUT
     * @return
     */
    @Bean
    public H2KeyGenerator h2KeyGenerator(){
        return new H2KeyGenerator();
    }

}
