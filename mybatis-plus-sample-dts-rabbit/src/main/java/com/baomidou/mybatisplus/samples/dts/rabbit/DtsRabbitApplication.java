package com.baomidou.mybatisplus.samples.dts.rabbit;

import com.baomidou.mybatisplus.dts.EnableDtsRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDtsRabbit
@SpringBootApplication
public class DtsRabbitApplication {

	public static void main(String[] args) {
		SpringApplication.run(DtsRabbitApplication.class, args);
	}

}
