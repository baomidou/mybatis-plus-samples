package com.baomidou.mybatisplus.samples.enums;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IEnum;


/**
 * <p>
 * #1500 github
 * </p>
 *
 * @author yuxiaobin
 * @date 2019/8/29
 */
public interface IBaseEnum<T extends Serializable> extends IEnum<T> {

    String getDescription();
}
