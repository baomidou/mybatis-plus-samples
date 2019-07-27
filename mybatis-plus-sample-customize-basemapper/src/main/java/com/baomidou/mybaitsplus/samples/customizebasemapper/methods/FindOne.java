package com.baomidou.mybaitsplus.samples.customizebasemapper.methods;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;

/**
 * 删除全部
 *
 * @author K 2019-7-9
 */
public class FindOne extends AbstractMethod {

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        /* 执行 SQL ，动态 SQL 参考类 SqlMethod */
        String sql = "select * from " + tableInfo.getTableName()
                + " where " + tableInfo.getKeyColumn() + "=#{" + tableInfo.getKeyProperty() + "}";
        /* mapper 接口方法名一致 */
        String method = "findOne";
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return addSelectMappedStatementForTable(mapperClass, method, sqlSource, tableInfo);
    }
}
