package com.baomidou.mybatisplus.samples.mysql.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.samples.mysql.enums.TestEnum;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 包含功能:
 * 1.自动填充        验证无误
 * 2.乐观锁          验证无误
 * 3.多租户          验证无误
 * 4.枚举            验证无误
 */
@Data
@Accessors(chain = true)
public class TestData implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField(jdbcType = JdbcType.INTEGER)
    private Integer testInt;
    @TableField("test_str")
    private String testStr;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
    private TestEnum testEnum;

    @Version
    private Integer version;
    /**
     * 多租户
     * 不用配置实体字段,但是数据库需要该字段
     */
    private Long tenantId;

}
