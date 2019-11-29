package com.baomidou.mybatisplus.samples.customizebasemapper.base;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
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
        return methodList;
    }
}
