package com.baomidou.mybatisplus.samples.deluxe.config;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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

    int myInsertAll(T entity);

    /**
     * 如果要自动填充，@{@code Param}(xx) xx参数名必须是 list/collection/array 3个的其中之一
     *
     * @param batchList
     * @return
     */
    int mysqlInsertAllBatch(@Param("list") List<T> batchList);
}
