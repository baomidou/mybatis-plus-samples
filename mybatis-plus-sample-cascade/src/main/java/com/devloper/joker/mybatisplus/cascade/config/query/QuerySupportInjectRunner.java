package com.devloper.joker.mybatisplus.cascade.config.query;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class QuerySupportInjectRunner implements CommandLineRunner {

    @Resource
    private QuerySupportMethod querySupportMethod;

    @Override
    public void run(String... args) throws Exception {
        querySupportMethod.injectQuerySupportMappedStatement();
    }
}
