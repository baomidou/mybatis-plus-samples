package com.baomidou.mybatisplus.samples.logic.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private Integer age;
    private String email;
    @TableLogic
    private Integer isDelete;
}
