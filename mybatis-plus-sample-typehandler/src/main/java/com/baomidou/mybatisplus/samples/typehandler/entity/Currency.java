package com.baomidou.mybatisplus.samples.typehandler.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 货币
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Currency {
    /**
     * 类型: 人民币 RMB , 美元 USD
     */
    private String type;
    /**
     * 金额
     */
    private Double amount;
}
