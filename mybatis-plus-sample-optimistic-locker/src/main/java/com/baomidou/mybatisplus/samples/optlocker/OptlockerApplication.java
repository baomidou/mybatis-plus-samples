package com.baomidou.mybatisplus.samples.optlocker;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.baomidou.mybatisplus.samples.*.mapper")
public class OptlockerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OptlockerApplication.class, args);
    }
}
