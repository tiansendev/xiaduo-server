package com.tiansen.ordermanager.mybatis.entity.join.order;

import com.tiansen.ordermanager.mybatis.entity.Express;
import com.tiansen.ordermanager.mybatis.entity.Order;
import com.tiansen.ordermanager.mybatis.entity.join.combination.ProductDefTotalNumberOfOrder;
import lombok.Data;

import java.util.List;

@Data
public class OrderReq extends Order {
    /**
     * 组合ids
     */
    private List<OrderCombInfo> combInfos;
    /**
     * 产品定义ids （当该订单不是组合产品时）
     */
    private List<ProductDefTotalNumberOfOrder> prodDefIdNumbers;
    /**
     * 申请人id
     */
    private Integer proposerId;
    /**
     * 申请人，当无申请人id时
     */
    private String proposer;
    /**
     * 快递
     */
    private Express express;

}
