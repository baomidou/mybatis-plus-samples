package com.baomidou.mybatisplus.samples.pagination.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author miemie
 * @since 2018-08-10
 */
@Data
@TableName("sys_user")
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
