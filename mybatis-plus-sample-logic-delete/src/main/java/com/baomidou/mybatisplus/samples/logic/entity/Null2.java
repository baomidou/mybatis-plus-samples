package com.baomidou.mybatisplus.samples.logic.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableLogic;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author miemie
 * @since 2019-11-26
 */
@Data
@Accessors(chain = true)
public class Null2 {

    private Long id;

    private String name;

    @TableLogic(delval = "now()", value = "null")
    private LocalDateTime delTime;
}
