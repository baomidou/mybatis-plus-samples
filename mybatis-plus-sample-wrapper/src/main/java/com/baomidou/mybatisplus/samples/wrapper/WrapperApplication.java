package com.baomidou.mybatisplus.samples.wrapper;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.baomidou.mybatisplus.samples.wrapper.mapper")
public class WrapperApplication {

    public static void main(String[] args) {
        SpringApplication.run(WrapperApplication.class, args);
    }

}
