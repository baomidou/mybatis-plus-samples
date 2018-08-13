package com.baomidou.mybatisplus.samples.deluxe.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户表
 * 设置逻辑删除字段,并且逻辑删除字段不 select 出来
 *
 * @author miemie
 * @since 2018-08-12
 */
@Data
@Accessors(chain = true)
public class User {
    private Long id;
    private String name;
    private Integer age;
    @TableField(el = "email, typeHandler=com.baomidou.mybatisplus.samples.deluxe.config.TestTypeHandler")
    private String email;
    @TableLogic
    @TableField(select = false)
    private Integer deleted;
}
