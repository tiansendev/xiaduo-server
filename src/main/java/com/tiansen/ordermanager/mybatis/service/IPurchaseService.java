package com.tiansen.ordermanager.mybatis.service;

import com.tiansen.ordermanager.mybatis.entity.Purchase;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tiansen.ordermanager.mybatis.entity.join.purchase.PurchaseDetail;
import com.tiansen.ordermanager.mybatis.entity.join.purchase.PurchaseReq;

/**
 * <p>
 * 采购 服务类
 * </p>
 *
 * @author rylai
 * @since 2019-01-02
 */
public interface IPurchaseService extends IService<Purchase> {

    PurchaseDetail getDetailById(Integer id);

    void addPurchase(PurchaseReq purchase);
}
