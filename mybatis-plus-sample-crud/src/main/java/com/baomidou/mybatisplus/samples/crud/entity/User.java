package com.baomidou.mybatisplus.samples.crud.entity;

import lombok.Data;

/**
 * <p>
 * 用户实体对应表 user
 * </p>
 *
 * @author hubin
 * @since 2018-08-11
 */
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
