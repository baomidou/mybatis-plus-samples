package com.baomidou.samples.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class User {
    /**
     * 字符串自增 ID
     */
    @TableId(type = IdType.AUTO)
    private String id;
    private String name;
    private Integer age;
    private String email;

}
