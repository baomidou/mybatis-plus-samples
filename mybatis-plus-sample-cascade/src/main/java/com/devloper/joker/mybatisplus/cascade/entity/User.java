package com.devloper.joker.mybatisplus.cascade.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor

@TableName(value = "user", resultMap = "userCascadeResult") //resultMap 只针对plus所封装的方法
public class User implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String username;

    private String password;

    @TableField(value = "role_id", el = "role.id")
    private Role role;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
