package com.baomidou.mybatisplus.sample.generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GeneratorApplication {

    /**
     * <p>
     * 测试 RUN<br>
     * 查看 h2 数据库控制台：http://localhost:8080/console<br>
     * 使用：JDBC URL 设置 jdbc:h2:mem:testdb 用户名 root 密码 test 进入，可视化查看 user 表<br>
     * 误删连接设置，开发机系统本地 ~/.h2.server.properties 文件<br>
     * <br>
     * 不同数据库例子参考 generator 模块
     * 包 com.baomidou.mybatisplus.test.generator 下的文件
     * </p>
     */
    public static void main(String[] args) {
        SpringApplication.run(GeneratorApplication.class, args);
    }

}
