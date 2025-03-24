package com.baomidou.mybatisplus.samples.mysql.injector;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.stream.Collectors;

/**
 * 演示一个自定义注入
 * <p>insert into table(....) values(....),(....)</p>
 *
 * @author nieqiurong
 */
public class MysqlInsertAllBatch extends AbstractMethod {

    /**
     * @param methodName 方法名
     * @since 3.5.0
     */
    public MysqlInsertAllBatch(String methodName) {
        super(methodName);
    }

    /**
     * @since 3.5.0
     */
    public MysqlInsertAllBatch() {
        super("mysqlInsertAllBatch");
    }

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        // 定义SQL语句
        String sql = "INSERT INTO " + tableInfo.getTableName() + "(" + tableInfo.getKeyColumn() + "," +
                tableInfo.getFieldList().stream().map(TableFieldInfo::getColumn).collect(Collectors.joining(",")) + ") VALUES ";
        String value = "(" + "#{" + ENTITY + DOT + tableInfo.getKeyProperty() + "}" + ","
                + tableInfo.getFieldList().stream().map(tableFieldInfo -> "#{" + ENTITY + DOT + tableFieldInfo.getProperty() + "}")
                .collect(Collectors.joining(",")) + ")";
        String valuesScript = SqlScriptUtils.convertForeach(value, "list", null, ENTITY, COMMA);
        SqlSource sqlSource = super.createSqlSource(configuration, "<script>" + sql + valuesScript + "</script>", modelClass);
        KeyGenerator keyGenerator = tableInfo.getIdType() == IdType.AUTO ? Jdbc3KeyGenerator.INSTANCE : NoKeyGenerator.INSTANCE;
        return this.addInsertMappedStatement(mapperClass, modelClass, this.methodName, sqlSource, keyGenerator, tableInfo.getKeyProperty(), tableInfo.getKeyColumn());
    }
}
