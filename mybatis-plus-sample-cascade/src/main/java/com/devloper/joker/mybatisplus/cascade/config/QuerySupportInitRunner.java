package com.devloper.joker.mybatisplus.cascade.config;

import com.devloper.joker.mybatis.plus.query.core.QuerySupportMethod;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class QuerySupportInitRunner implements CommandLineRunner {

    @Resource
    private QuerySupportMethod querySupportMethod;

    @Override
    public void run(String... args) throws Exception {
        querySupportMethod.injectQuerySupportMappedStatement();
    }
}
