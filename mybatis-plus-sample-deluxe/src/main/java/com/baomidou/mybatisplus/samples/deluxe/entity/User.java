package com.baomidou.mybatisplus.samples.deluxe.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

/**
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
