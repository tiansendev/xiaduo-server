package com.tiansen.ordermanager.mybatis.entity.join.order;

import lombok.Data;

/**
 * 订单中组合信息
 */
@Data
public class OrderCombInfo {
    /**
     * 组合定义id
     */
    private Integer combDefId;
    /**
     * 组合数量
     */
    private Integer combNumber;
}
