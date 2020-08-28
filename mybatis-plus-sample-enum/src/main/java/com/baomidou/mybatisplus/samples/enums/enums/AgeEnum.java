package com.baomidou.mybatisplus.samples.enums.enums;


import com.baomidou.mybatisplus.annotation.IEnum;

import lombok.Getter;

/**
 * <p>
 * 通用枚举注入演示，注意需要实现 IEnums 也需要扫描枚举包
 * </p>
 *
 * @author hubin
 * @since 2018-08-15
 */
@Getter
public enum AgeEnum implements IEnum<Integer> {
  ONE(1, "一岁"),
  TWO(2, "二岁"),
  THREE(3, "三岁");

  private final int value;
  private final String desc;

  AgeEnum(final int value, final String desc) {
    this.value = value;
    this.desc = desc;
  }

  @Override
  public Integer getValue() {
    return value;
  }
}
