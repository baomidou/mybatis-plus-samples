package com.baomidou.mybatisplus.samples.mysql.config;

import mybatis.mate.ddl.SimpleDdl;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class MysqlDdl extends SimpleDdl {

    /**
     * 执行 SQL 脚本方式
     */
    @Override
    public List<String> getSqlFiles() {
        return Arrays.asList(
                // 内置包方式
                "db/test-schema.sql"
//                ,"D:\\db\\tag-data.sql"
        );
    }
}
