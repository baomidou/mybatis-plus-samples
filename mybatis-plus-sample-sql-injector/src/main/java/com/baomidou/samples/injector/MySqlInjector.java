package com.baomidou.samples.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.samples.injector.methods.DeleteAll;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义Sql注入
 *
 * @author nieqiurong 2018/8/11 20:23.
 */
@Component
public class MySqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        //增加自定义方法
        methodList.add(new DeleteAll());
        return methodList;
    }
}
