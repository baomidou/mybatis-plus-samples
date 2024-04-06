package com.baomidou.mybatisplus;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.query.DefaultQuery;

import java.util.ArrayList;
import java.util.List;

import static com.baomidou.mybatisplus.generator.config.builder.GeneratorBuilder.globalConfigBuilder;

public class GeneratorCode {

    public static final int TABLE_SIZE = 1000;

    public static final int COLUMN_SIZE = 20;

    public static class MockTable extends DefaultQuery {

        public MockTable(ConfigBuilder configBuilder) {
            super(configBuilder);
        }

        @Override
        public List<TableInfo> queryTables() {
            List<TableInfo> tableInfos = new ArrayList<>();
            for (int i = 0; i < TABLE_SIZE; i++) {
                TableInfo tableInfo = new TableInfo(configBuilder, "Demo" + i);
                tableInfo.setEntityName("Demo"+i);
                for (int j = 0; j < COLUMN_SIZE; j++) {
                    TableField tableField = new TableField(configBuilder, "name" + j);
                    tableField.setPropertyName("name"+j, DbColumnType.STRING);
                    tableInfo.addField(tableField);
                }
                tableInfo.setHavePrimaryKey(true);
                TableField tableField = new TableField(configBuilder, "id");
                tableField.setPropertyName("id",DbColumnType.STRING);
                tableInfo.addField(tableField);
                tableInfo.processTable();
                tableInfos.add(tableInfo);
            }
            return tableInfos;
        }
    }

    public static void main(String[] args) {
        String dir = System.getProperty("user.dir");
        String output = dir + "\\mybatis-plus-startup-analysis\\src\\main\\java\\";
        System.out.println(output);
        DataSourceConfig dataSourceConfig = new DataSourceConfig
                .Builder("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;CASE_INSENSITIVE_IDENTIFIERS=TRUE;MODE=MYSQL;DATABASE_TO_LOWER=TRUE", "sa", "")
                .databaseQueryClass(MockTable.class)
                .build();
        AutoGenerator generator = new AutoGenerator(dataSourceConfig);
        generator.global(globalConfigBuilder().outputDir(output).build());
        generator.execute();
    }

}
