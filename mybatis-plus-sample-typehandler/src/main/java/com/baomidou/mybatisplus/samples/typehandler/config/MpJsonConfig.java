package com.baomidou.mybatisplus.samples.typehandler.config;

import com.baomidou.mybatisplus.extension.handlers.GsonTypeHandler;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author miemie
 * @since 2019-11-28
 */
@Component
public class MpJsonConfig implements CommandLineRunner {

    /**
     * 可以set进去自己的
     */
    @Override
    public void run(String... args) throws Exception {
        JacksonTypeHandler.setObjectMapper(new ObjectMapper());
        GsonTypeHandler.setGson(new Gson());
    }
}
