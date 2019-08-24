package com.baomidou.mybatisplus.samples.dytablename.config;

import com.baomidou.mybatisplus.extension.parsers.DynamicTableNameParser;
import com.baomidou.mybatisplus.extension.parsers.ITableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 * @author miemie
 * @since 2018-08-10
 */
@Configuration
@MapperScan("com.baomidou.mybatisplus.samples.dytablename.mapper")
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        DynamicTableNameParser dynamicTableNameParser = new DynamicTableNameParser();
        dynamicTableNameParser.setTableNameHandlerMap(new HashMap<String, ITableNameHandler>(2) {{
            put("user", (metaObject, sql, tableName) -> {
                // metaObject 可以获取传入参数，这里实现你自己的动态规则
                String year = "_2018";
                int random = new Random().nextInt(10);
                if (random % 2 == 1) {
                    year = "_2019";
                }
                return tableName + year;
            });
        }});
        paginationInterceptor.setSqlParserList(Collections.singletonList(dynamicTableNameParser));
        return paginationInterceptor;
    }
}
