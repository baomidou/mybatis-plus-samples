package com.baomidou.mybatisplus.samples.optlocker.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("sys_person")
public class Person {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    @Version
    private LocalDateTime version;
}
