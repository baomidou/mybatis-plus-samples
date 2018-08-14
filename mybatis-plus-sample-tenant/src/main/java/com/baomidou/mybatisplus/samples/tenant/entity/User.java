package com.baomidou.mybatisplus.samples.tenant.entity;

import lombok.Data;
import lombok.experimental.Accessors;

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
public class User {
    private Long id;
    /**
     * 租户 ID
     */
    private Long tenantId;
    private String name;

}
