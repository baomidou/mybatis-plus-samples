package com.baomidou.mybatisplus.samples.typehandler.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.baomidou.mybatisplus.samples.typehandler.WalletListTypeHandler;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 用户实体对应表 user
 * </p>
 *
 * @author hubin
 * @since 2018-08-11
 */
@Data
@Accessors(chain = true)
@TableName(value = "sys_user", autoResultMap = true)
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;

    /**
     * 注意！！ 必须开启映射注解
     *
     * @TableName(autoResultMap = true)
     * <p>
     * 以下两种类型处理器，二选一 也可以同时存在
     * <p>
     * 注意！！选择对应的 JSON 处理器也必须存在对应依赖包
     */
    @TableField(typeHandler = WalletListTypeHandler.class)
    private List<Wallet> wallets;

    @TableField(typeHandler = FastjsonTypeHandler.class)
    private OtherInfo otherInfo;
}
