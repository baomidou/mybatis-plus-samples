package com.baomidou.mybatisplus.samples.deluxe;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.samples.deluxe.methods.DeleteAll;

import java.util.List;

/**
 * 自定义 SqlInjector
 *
 * @author miemie
 * @since 2018-08-13
 */
public class MyLogicSqlInjector extends LogicSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList() {
        List<AbstractMethod> methodList = super.getMethodList();
        methodList.add(new DeleteAll());
        return methodList;
    }
}
