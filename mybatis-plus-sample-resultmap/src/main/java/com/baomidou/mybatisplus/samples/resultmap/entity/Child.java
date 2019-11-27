package com.baomidou.mybatisplus.samples.resultmap.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author miemie
 * @since 2019-11-27
 */
@Data
@TableName(autoResultMap = true) //不配合 typeHandler 或 numericScale 使用无意义,演示而已
public class Child {

    private Long id;

    private String name;

    private Long laoHanId;

    private Long laoMaId;

    @TableField(exist = false)
    private Man laoHan;

    @TableField(exist = false)
    private Woman laoMa;
}
