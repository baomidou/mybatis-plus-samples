package com.baomidou.mybatisplus.samples.deluxe.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

/**
 * 用户表
 * 设置逻辑删除字段,并且逻辑删除字段不 select 出来
 * @author miemie
 * @since 2018-08-12
 */
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    @TableLogic
    @TableField(select = false)
    private Integer deleted;
}
