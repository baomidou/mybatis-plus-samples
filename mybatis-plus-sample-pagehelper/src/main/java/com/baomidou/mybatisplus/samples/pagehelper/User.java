package com.baomidou.mybatisplus.samples.pagehelper;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author miemie
 * @since 2020-05-29
 */
@Data
@TableName("sys_user")
public class User {

    private Long id;

    private String name;
}
