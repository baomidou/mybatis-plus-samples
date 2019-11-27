package com.baomidou.mybatisplus.samples.typehandler;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.baomidou.mybatisplus.samples.typehandler.mapper")
@SpringBootApplication
public class TypehandlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TypehandlerApplication.class, args);
	}
}
