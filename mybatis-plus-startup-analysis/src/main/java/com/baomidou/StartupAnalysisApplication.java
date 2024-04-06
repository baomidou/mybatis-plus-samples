package com.baomidou;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动性能分析
 * 请先用test目录下的GeneratorCode生成自己所需要测试的表数量和字段数量
 *
 * @author nieqiurong
 */
@MapperScan("com.baomidou.mapper")
@ImportAutoConfiguration(MybatisPlusConfig.class)
@SpringBootApplication(scanBasePackages = "${scanpackage:com.baomidou}")
public class StartupAnalysisApplication implements CommandLineRunner {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public static void main(String[] args) {
        SpringApplication.run(StartupAnalysisApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Configuration configuration = sqlSessionFactory.getConfiguration();
        System.out.println("注册Mapper数量:" + configuration.getMapperRegistry().getMappers().size());
        System.out.println("注册MappedStatements数量:" + configuration.getMappedStatements().size());
    }

}
