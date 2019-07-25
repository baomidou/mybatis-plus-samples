package com.baomidou.mybatisplus.samples.reduce.springmvc.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author hcq
 * @Description:
 * @date 2019/5/2212:09
 */
@Component
public class SpringContext implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    /**
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     *
     * @param applicationContext
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContext.applicationContext = applicationContext;
    }

    /**
     * 根据名字去获取bean
     *
     * @param beanName
     * @param <T>
     * @return
     */
    public static <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    /**
     * 根据class 获取  bean
     *
     * @param c
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> c) {
        return (T) applicationContext.getBean(c);
    }
}
