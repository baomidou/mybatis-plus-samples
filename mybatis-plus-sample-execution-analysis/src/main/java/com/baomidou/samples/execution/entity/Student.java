package com.baomidou.samples.execution.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学生实体
 * @author nieqiurong 2018/8/11 20:20.
 */
@Data
@TableName(value = "student")
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private Long id;

    private String name;

    private Integer age;

}
