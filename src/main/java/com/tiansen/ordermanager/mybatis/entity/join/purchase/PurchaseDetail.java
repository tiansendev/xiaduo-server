package com.tiansen.ordermanager.mybatis.entity.join.purchase;

import com.tiansen.ordermanager.mybatis.entity.Purchase;
import com.tiansen.ordermanager.mybatis.entity.Supplier;
import lombok.Data;

@Data
public class PurchaseDetail extends Purchase {
    private Supplier supplier;
}
