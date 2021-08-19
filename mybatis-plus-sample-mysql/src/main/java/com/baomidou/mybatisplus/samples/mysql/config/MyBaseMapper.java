package com.baomidou.mybatisplus.samples.mysql.config;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author miemie
 * @since 2018-09-13
 */
public interface MyBaseMapper<T> extends BaseMapper<T> {

    int insertBatchSomeColumn(@Param("list") List<T> entityList);

    int alwaysUpdateSomeColumnById(@Param(Constants.ENTITY) T entity);
}
