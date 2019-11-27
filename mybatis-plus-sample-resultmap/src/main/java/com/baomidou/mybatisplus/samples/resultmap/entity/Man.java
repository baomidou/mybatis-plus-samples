package com.baomidou.mybatisplus.samples.resultmap.entity;

import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @author miemie
 * @since 2019-11-27
 */
@Data
@TableName(resultMap = "m_b") // 对应xml里的 id
public class Man {

    private Long id;

    private String name;

    private Long laoPoId;

    @TableField(exist = false)
    private Woman laoPo;

    @TableField(exist = false)
    private List<Child> waWa;
}
