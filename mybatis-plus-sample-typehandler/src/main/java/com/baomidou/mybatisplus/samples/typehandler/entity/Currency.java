package com.baomidou.mybatisplus.samples.typehandler.entity;

import lombok.Data;

/**
 * 货币
 */
@Data
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
