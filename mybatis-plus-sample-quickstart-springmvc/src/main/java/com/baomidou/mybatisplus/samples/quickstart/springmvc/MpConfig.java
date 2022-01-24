package com.baomidou.mybatisplus.samples.quickstart.springmvc;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class MpConfig {

    @Bean
    public GlobalConfig globalConfiguration() {
        GlobalConfig conf = new GlobalConfig();
        conf.setDbConfig(new GlobalConfig.DbConfig().setKeyGenerators(Arrays.asList(
                // h2 1.x 的写法（默认 2.x 的写法）
                new IKeyGenerator() {

                    @Override
                    public String executeSql(String incrementerName) {
                        return "select " + incrementerName + ".nextval";
                    }

                    @Override
                    public DbType dbType() {
                        return DbType.POSTGRE_SQL;
                    }
                }
        )));
        return conf;
    }
}
