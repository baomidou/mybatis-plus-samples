package com.baomidou.mybatisplus.no.spring.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author miemie
 * @since 2020-03-11
 */
@Data
@Accessors(chain = true)
public class Person {

    private Long id;

    private String name;

    private int age;
}
