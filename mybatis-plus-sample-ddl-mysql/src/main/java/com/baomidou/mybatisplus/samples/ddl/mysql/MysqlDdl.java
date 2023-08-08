package com.baomidou.mybatisplus.samples.ddl.mysql;

import com.baomidou.mybatisplus.extension.ddl.SimpleDdl;
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
                // 测试存储过程
                "db/test_procedure.sql#$$",

                // 内置包方式
                "db/tag-schema.sql",
                "db/tag-data.sql"

                // 文件绝对路径方式（修改为你电脑的地址）
                // "D:\\sql\\tag-data.sql"
        );
    }
}
