package com.baomidou.mybatisplus.samples.optlocker.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

@Data
@TableName("sys_user")
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    @Version
    private Integer version;
}
