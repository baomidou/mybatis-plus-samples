package com.baomidou.mybatisplus.samples.generator;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * <p>
 * H2数据库 代码生成Sample
 * <p>
 * 默认H2数据库是大小写敏感的，参考
 * http://www.h2database.com/javadoc/org/h2/engine/DbSettings.html?highlight=CASE_INSENSITIVE_IDENTIFIERS&search=case#CASE_INSENSITIVE_IDENTIFIERS
 *
 * </p>
 *
 * @author yuxiaobin
 * @date 2020/3/27
 */
public class H2CodeGenerationTest {

    @Test
    public void testStr() {
        final String folder = new File(this.getClass().getClassLoader().getResource("./").getFile()).getAbsolutePath();
        System.out.println(folder);

    }


    /**
     * 默认H2数据库是大小写敏感的，参考
     * http://www.h2database.com/javadoc/org/h2/engine/DbSettings.html?highlight=CASE_INSENSITIVE_IDENTIFIERS&search=case#CASE_INSENSITIVE_IDENTIFIERS
     * <p>
     * 忽略大小写，可以在jdbcUrl后加参数：CASE_INSENSITIVE_IDENTIFIERS=TRUE
     * 就可以忽略大小写
     */
    @Test
    public void generateCode() {
        generate("h2", "user");
    }

    private void generate(String moduleName, String... tableNamesInclude) {
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
//        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir("d:/codeGen");
        gc.setAuthor("苗老板");
        gc.setOpen(false);
        //默认不覆盖，如果文件存在，将不会再生成，配置true就是覆盖
        gc.setFileOverride(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        final String folder = new File(this.getClass().getClassLoader().getResource("./").getFile()).getAbsolutePath();
        dsc.setUrl("jdbc:p6spy:h2:file:" + "d:" + "/mybatisplus;TRACE_LEVEL_FILE=0;IFEXISTS=TRUE;CASE_INSENSITIVE_IDENTIFIERS=TRUE");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.p6spy.engine.spy.P6SpyDriver");
        dsc.setUsername("root");
        dsc.setPassword("test");
        dsc.setDbType(DbType.H2);
        mpg.setDataSource(dsc);


        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(moduleName);
        pc.setParent("com.baomidou.mybatisplus.samples.generator");
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("com.baomidou.mybatisplus.samples.generator.common.BaseEntity");
        strategy.setEntityLombokModel(true);
//        strategy.setSuperControllerClass("com.baomidou.mybatisplus.samples.generator.common.BaseController");
        strategy.setInclude(tableNamesInclude);
        strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
//        strategy.entityTableFieldAnnotationEnable(true);
        mpg.setStrategy(strategy);
        // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        configCustomizedCodeTemplate(mpg);
        configInjection(mpg);

        mpg.execute();
    }

    /**
     * 自定义模板
     *
     * @param mpg
     */
    private void configCustomizedCodeTemplate(AutoGenerator mpg) {
        //配置 自定义模板
        TemplateConfig templateConfig = new TemplateConfig()
                .setEntity("templates/MyEntityTemplate.java")//指定Entity生成使用自定义模板
                .setXml(null);//不生成xml
        mpg.setTemplate(templateConfig);
    }

    /**
     * 配置自定义参数/属性
     *
     * @param mpg
     */
    private void configInjection(AutoGenerator mpg) {
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                this.setMap(map);
                /*
                自定义属性注入: 模板配置：abc=${cfg.abc}
                 */
            }
        };
//        List<FileOutConfig> focList = new ArrayList<>();
//        focList.add(new FileOutConfig("/templates/MyEntityTemplate.java.ftl") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 指定模板生，自定义生成文件到哪个地方
//                return "D:/abc";
//            }
//        });
//        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
    }


    @Test
    public void testGetTableNames() throws SQLException {
        Connection conn = DriverManager.
                getConnection("jdbc:p6spy:h2:file:d:/mybatisplus;TRACE_LEVEL_FILE=0;IFEXISTS=TRUE;CASE_INSENSITIVE_IDENTIFIERS=TRUE", "root", "test");
        Statement stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM INFORMATION_SCHEMA.TABLES ");
        while (resultSet.next()) {
            System.out.println(resultSet.getObject("TABLE_NAME"));
        }
        // add application code here
        resultSet = stmt.executeQuery("show table status WHERE 1=1  AND NAME IN ('user')");
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }
        conn.close();
    }
}
