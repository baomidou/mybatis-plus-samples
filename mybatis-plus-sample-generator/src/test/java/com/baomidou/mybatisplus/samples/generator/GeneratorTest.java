package com.baomidou.mybatisplus.samples.generator;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;
import lombok.Data;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.Test;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * <p>
 * 代码生成Sample
 * </p>
 *
 * @author lanjerry
 * @since 2020-10-28
 */
public class GeneratorTest {

    @Data
    static class BaseEntity implements Serializable {

        private String id;

        private Integer flag;
    }


    @Test
    public void h2Test() throws SQLException {
        new AutoGenerator(h2DataSourceConfig())
                .global(globalConfig())
                .packageInfo(packageConfig())
                .strategy(strategyConfig())
                .engine(new FreemarkerTemplateEngine())
                .template(templateConfig())
                .injection(injectionConfig())
                .execute();
    }

    @Test
    public void mysqlTest() throws SQLException {
        new AutoGenerator(mysqlDataSourceConfig())
                .global(globalConfig())
                .packageInfo(packageConfig())
                .strategy(strategyConfig())
                .engine(new FreemarkerTemplateEngine())
                .template(templateConfig())
                .injection(injectionConfig())
                .execute();
    }

    /**
     * H2数据源配置
     */
    private DataSourceConfig h2DataSourceConfig() throws SQLException {
        DataSourceConfig dataSourceConfig = new DataSourceConfig
                .Builder("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;CASE_INSENSITIVE_IDENTIFIERS=TRUE", "sa", "")
                .driver(org.h2.Driver.class).build();
        runScript(dataSourceConfig);
        return dataSourceConfig;
    }

    private void runScript(DataSourceConfig dataSourceConfig) throws SQLException {
        try (Connection connection = dataSourceConfig.getConn()) {
            InputStream inputStream = GeneratorTest.class.getResourceAsStream("/sql/init.sql");
            ScriptRunner scriptRunner = new ScriptRunner(connection);
            scriptRunner.setAutoCommit(true);
            scriptRunner.runScript(new InputStreamReader(inputStream));
        }
    }

    /**
     * Mysql数据源配置
     */
    private DataSourceConfig mysqlDataSourceConfig() throws SQLException {
        DataSourceConfig dataSourceConfig = new DataSourceConfig
                .Builder("jdbc:mysql://127.0.0.1:3306/mybatis-plus?characterEncoding=UTF8", "root", "123456")
                .driver(com.mysql.cj.jdbc.Driver.class).build();
        runScript(dataSourceConfig);
        return dataSourceConfig;
    }

    /**
     * 全局配置
     */
    private GlobalConfig globalConfig() {
        final String outPutDir = System.getProperty("os.name").toLowerCase().contains("windows") ? "D://tmp" : "/tmp";
        return new GlobalConfig.Builder().outputDir(outPutDir).author("苗老板").openDir(true).fileOverride(true).build();
    }

    /**
     * 包配置
     */
    private PackageConfig packageConfig() {
        return new PackageConfig.Builder().moduleName("h2").parent("com.baomidou.mybatisplus.samples.generator").build();
    }

    /**
     * 策略配置
     */
    private StrategyConfig strategyConfig() {
        return new StrategyConfig.Builder()
                .addInclude("t_simple") // 包含的表名
                .addTablePrefix("t_") //统一表前缀
                .entityBuilder()  // 实体相关配置
                    .naming(NamingStrategy.underline_to_camel)
                    .columnNaming(NamingStrategy.underline_to_camel)
                    .lombok(true)
//                    .addSuperEntityColumns("id","create_time","update_time")
                    .superClass(BaseEntity.class)   //自动识别父类字段
                    .addTableFills(new Column("create_time", FieldFill.INSERT))     //基于字段填充
                    .addTableFills(new Property("updateTime",FieldFill.UPDATE))    //基于属性填充
                .controllerBuilder().hyphenStyle(true)  //控制器相关配置
                .build();
    }

    /**
     * 自定义模板配置
     */
    private TemplateConfig templateConfig() {
        return new TemplateConfig.Builder().all()   //激活所有默认模板
//                .entity("templates/MyEntityTemplate.java")  //覆盖默认模板的实体模板配置,使用自定义实体模板
                .build()
                .disable(TemplateType.XML); //禁用xml生成
        // 上面代码等价于下面这里下面这里
//        return new TemplateConfig.Builder().entity("templates/MyEntityTemplate.java").controller().service().mapper().build();
    }

    /**
     * 自定义配置
     */
    private InjectionConfig injectionConfig() {
        return new InjectionConfig(Collections.singletonMap("abc", this.globalConfig().getAuthor() + "-mp"));
    }
}
