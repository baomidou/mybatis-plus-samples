package com.baomidou.mybatisplus.samples.sequence;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.baomidou.mybatisplus.samples.*.mapper")
public class SequenceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SequenceApplication.class, args);
    }

}
