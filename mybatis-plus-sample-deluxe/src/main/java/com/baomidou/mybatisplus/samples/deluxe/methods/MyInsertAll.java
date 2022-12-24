package com.baomidou.mybatisplus.samples.deluxe.methods;

import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;

/**
 * <p>
 * </p>
 *
 * @author yuxiaobin
 * @date 2019/6/14
 */
public class MyInsertAll extends AbstractMethod {

    public MyInsertAll(String methodName) {
        super(methodName);
    }

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String sql = "insert into %s %s values %s";
        StringBuilder fieldSql = new StringBuilder();
        fieldSql.append(tableInfo.getKeyColumn()).append(",");
        StringBuilder valueSql = new StringBuilder();
        valueSql.append("#{").append(tableInfo.getKeyProperty()).append("},");
        tableInfo.getFieldList().forEach(x->{
            fieldSql.append(x.getColumn()).append(",");
            valueSql.append("#{").append(x.getProperty()).append("},");
        });
        fieldSql.delete(fieldSql.length()-1, fieldSql.length());
        fieldSql.insert(0, "(");
        fieldSql.append(")");
        valueSql.insert(0, "(");
        valueSql.delete(valueSql.length()-1, valueSql.length());
        valueSql.append(")");
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, String.format(sql, tableInfo.getTableName(), fieldSql.toString(), valueSql.toString()), modelClass);
        return this.addInsertMappedStatement(mapperClass, modelClass, methodName, sqlSource, new NoKeyGenerator(), null, null);
    }
}
