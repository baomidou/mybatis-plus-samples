package com.baomidou.mybatisplus.samples.crud.entity;

import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * <p>
 * 用户实体对应表 user2 测试注解条件
 * </p>
 *
 * @author hubin
 * @since 2021-08-19
 */
@Data
@Accessors(chain = true)
public class User2 {
    private Long id;
    @TableField(condition = SqlCondition.LIKE, jdbcType = JdbcType.VARCHAR)
    private String name;
    private Integer age;

}
