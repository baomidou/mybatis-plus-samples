package com.baomidou.mybatisplus.samples.jsonb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
// autoResultMap 告知 MP 查询 xml 时候需要映射 typeHandler 配合 @TableField(typeHandler = JsonbTypeHandler.class) 使用
// 更多使用查看文档 https://baomidou.com/pages/fd41d8/
@TableName(autoResultMap = true)
public class TestData implements Serializable {

    private Long id;

    @TableField(typeHandler = JsonbTypeHandler.class)
    private TestContent content;

}
