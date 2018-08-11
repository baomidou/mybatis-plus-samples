package com.baomidou.samples.performance.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 学生实体
 * @author nieqiurong 2018/8/11 20:20.
 */
@Data
@TableName(value = "student")
public class Student {

    private Long id;

    private String name;

    private Integer age;

}
