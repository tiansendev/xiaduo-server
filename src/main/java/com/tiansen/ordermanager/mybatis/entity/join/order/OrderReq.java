package com.tiansen.ordermanager.mybatis.entity.join.order;

import com.tiansen.ordermanager.mybatis.entity.Express;
import com.tiansen.ordermanager.mybatis.entity.Order;
import lombok.Data;

import java.util.List;

@Data
public class OrderReq extends Order {
    /**
     * 组合ids
     */
    private List<Integer> combIds;
    /**
     * 产品定义ids （当该订单不是组合产品时）
     */
    private List<Integer> prodDefIds;
    /**
     * 消费品ids
     */
    private List<Integer> consumIds;
    /**
     * 快递
     */
    private Express express;

}
