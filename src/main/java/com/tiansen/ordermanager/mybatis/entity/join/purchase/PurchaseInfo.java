package com.tiansen.ordermanager.mybatis.entity.join.purchase;

import lombok.Data;

@Data
public class PurchaseInfo {
    /**
     * 产品定义id
     */
    private Integer prodDefId;
    /**
     * 采购单价
     */
    private Double purPrice;
    /**
     * 采购数量
     */
    private Integer purNum;
    /**
     * 单位
     */
    private String unit;
    /**
     * 仓库库id
     */
    private Integer storeId;
    /**
     * 库位
     */
    private String storeLoc;
}
