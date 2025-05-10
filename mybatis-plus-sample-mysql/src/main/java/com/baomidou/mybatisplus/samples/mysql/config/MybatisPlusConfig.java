package com.baomidou.mybatisplus.samples.mysql.config;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.extension.injector.methods.AlwaysUpdateSomeColumnById;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.samples.mysql.injector.MysqlInsertAllBatch;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Mybatis Plus Config
 */
@Configuration
@MapperScan("com.baomidou.mybatisplus.samples.mysql.mapper")
public class MybatisPlusConfig {

    @Bean
    public MetaObjectHandler metaObjectHandler(){
        return new MysqlMetaObjectHandler();
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return mybatisPlusInterceptor;
    }

    @Bean
    public ISqlInjector sqlInjector(){
        return new DefaultSqlInjector() {
            /**
             * 注入自定义全局方法
             */
            @Override
            public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
                List<AbstractMethod> methodList = super.getMethodList(mapperClass, tableInfo);
                // 不要逻辑删除字段, 不要乐观锁字段, 不要填充策略是 UPDATE 的字段
                methodList.add(new InsertBatchSomeColumn(t -> !t.isLogicDelete() && !t.isVersion() && t.getFieldFill() != FieldFill.UPDATE));
                // 不要填充策略是 INSERT 的字段, 不要字段名是 column4 的字段
                methodList.add(new AlwaysUpdateSomeColumnById(t -> t.getFieldFill() != FieldFill.INSERT && !t.getProperty().equals("column4")));
                // 演示自定义注入
                methodList.add(new MysqlInsertAllBatch());
                return methodList;
            }
        };
    }

}
