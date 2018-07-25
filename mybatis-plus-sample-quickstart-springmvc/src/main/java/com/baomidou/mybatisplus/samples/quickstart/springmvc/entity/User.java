package com.baomidou.mybatisplus.samples.quickstart.springmvc.entity;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
