package com.baomidou.mybatisplus.samples.logic.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author miemie
 * @since 2019-11-26
 */
@Data
@Accessors(chain = true)
public class Null1 {

    private Long id;

    private String name;

    @TableLogic(delval = "null", value = "1")
    private Integer deleted;
}
