package com.baomidou.mybatisplus.samples.enums.enums;

import com.baomidou.mybatisplus.samples.enums.IBaseEnum;

import lombok.Getter;

/**
 * <p>
 * </p>
 *
 * @author yuxiaobin
 * @date 2019/8/29
 */
@Getter
public enum UserState implements IBaseEnum<Integer> {

    ACTIVE(1, "A"),
    INACTIVE(2, "I");

    private final int state;
    private final String descp;

    UserState(int state, String descp) {
        this.state = state;
        this.descp = descp;

    }

    @Override
    public Integer getValue() {
        return state;
    }

    @Override
    public String getDescription() {
        return descp;
    }
}
