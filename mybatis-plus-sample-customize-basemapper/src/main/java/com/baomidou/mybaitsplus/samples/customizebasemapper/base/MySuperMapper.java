package com.baomidou.mybaitsplus.samples.customizebasemapper.base;

import com.baomidou.mybatisplus.core.mapper.Mapper;

/**
 * <p>
 * </p>
 *
 * @author K
 * @date 2019/7/9
 */
public interface MySuperMapper<T> extends Mapper {

    public T findOne(Object id);

}
