package com.baomidou.mybatisplus.samples.customizebasemapper.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.extension.injector.methods.additional.AlwaysUpdateSomeColumnById;
import com.baomidou.mybatisplus.extension.injector.methods.additional.InsertBatchSomeColumn;
import com.baomidou.mybatisplus.extension.injector.methods.additional.LogicDeleteByIdWithFill;
import com.baomidou.mybatisplus.samples.customizebasemapper.methods.FindOne;

import java.util.List;

/**
 * 自定义 SqlInjector
 *
 * @author K
 * @since 2019-7-9
 */
public class CustomizeSuperMapperSqlInjector extends DefaultSqlInjector {

    /**
     * 如果只需增加方法，保留MP自带方法
     * 可以super.getMethodList() 再add
     */
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        methodList.add(new FindOne());
        /**
         * 以下 3 个为内置选装件
         * 头 2 个支持字段筛选函数
         */
        // 例: 不要指定了 update 填充的字段
        methodList.add(new InsertBatchSomeColumn(i -> i.getFieldFill() != FieldFill.UPDATE));
        methodList.add(new AlwaysUpdateSomeColumnById());
        methodList.add(new LogicDeleteByIdWithFill());
        return methodList;
    }
}
