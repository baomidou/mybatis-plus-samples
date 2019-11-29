package com.baomidou.mybatisplus.samples.customizebasemapper.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;

import java.io.Serializable;

/**
 * @author K
 * @since 2019/7/9
 */
public interface MySuperMapper<T> extends BaseMapper<T> {

    /* ↓↓↓↓↓↓↓↓↓↓↓↓↓↓ copy from {@link com.baomidou.mybatisplus.extension.toolkit.ChainWrappers} ↓↓↓↓↓↓↓↓↓↓↓↓↓↓ */

    default QueryChainWrapper<T> queryChain() {
        return new QueryChainWrapper<>(this);
    }

    default LambdaQueryChainWrapper<T> lambdaQueryChain() {
        return new LambdaQueryChainWrapper<>(this);
    }

    default UpdateChainWrapper<T> updateChain() {
        return new UpdateChainWrapper<>(this);
    }

    default LambdaUpdateChainWrapper<T> lambdaUpdateChain() {
        return new LambdaUpdateChainWrapper<>(this);
    }

    /* ↑↑↑↑↑↑↑↑↑↑↑↑↑↑ copy from {@link com.baomidou.mybatisplus.extension.toolkit.ChainWrappers} ↑↑↑↑↑↑↑↑↑↑↑↑↑↑ */

    T findOne(Serializable id);
}
