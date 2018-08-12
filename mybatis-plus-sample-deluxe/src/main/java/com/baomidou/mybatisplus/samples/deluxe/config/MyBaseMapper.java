package com.baomidou.mybatisplus.samples.deluxe.config;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author miemie
 * @since 2018-08-13
 */
public interface MyBaseMapper<T> extends BaseMapper<T> {

    /**
     * 自定义通用方法
     */
    Integer deleteAll();
}
