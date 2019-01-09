package com.tiansen.ordermanager.mybatis.service.impl;

import com.tiansen.ordermanager.common.util.RylaiRandom;
import com.tiansen.ordermanager.exception.ParameterIllegalException;
import com.tiansen.ordermanager.mybatis.entity.ProductDetailInStore;
import com.tiansen.ordermanager.mybatis.entity.Purchase;
import com.tiansen.ordermanager.mybatis.entity.emun.ProductStatusEmun;
import com.tiansen.ordermanager.mybatis.entity.join.purchase.PurchaseDetail;
import com.tiansen.ordermanager.mybatis.entity.join.purchase.PurchaseInfo;
import com.tiansen.ordermanager.mybatis.entity.join.purchase.PurchaseReq;
import com.tiansen.ordermanager.mybatis.fill.CreateFieldFill;
import com.tiansen.ordermanager.mybatis.mapper.ProductDetailInStoreMapper;
import com.tiansen.ordermanager.mybatis.mapper.PurchaseMapper;
import com.tiansen.ordermanager.mybatis.service.IPurchaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 采购 服务实现类
 * </p>
 *
 * @author rylai
 * @since 2019-01-02
 */
@Service
public class PurchaseServiceImpl extends ServiceImpl<PurchaseMapper, Purchase> implements IPurchaseService {

    @Autowired
    private PurchaseMapper purchaseMapper;

    @Autowired
    private ProductDetailInStoreMapper productDetailInStoreMapper;

    @Override
    public PurchaseDetail getDetailById(Integer id) {
        if (id == null)
            throw new ParameterIllegalException();
        return purchaseMapper.findDetailById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addPurchase(PurchaseReq purchaseReq) {
        if (purchaseReq == null || purchaseReq.getPurchaseDetailInfos() == null
                || purchaseReq.getSupplierId() == null)
            throw new ParameterIllegalException();
        List<PurchaseInfo> purchaseDetailInfos = purchaseReq.getPurchaseDetailInfos();
        // 生成采购单
        CreateFieldFill.fill(purchaseReq);
        purchaseReq.setPurCode(RylaiRandom.genOrderNo());
        save(purchaseReq);

        Double totalPrice = 0d;
        List<ProductDetailInStore> productDetailsInstore = new ArrayList<>();
        for (PurchaseInfo info : purchaseDetailInfos) {
            Integer prodDefId = info.getProdDefId();
            Double purPrice = info.getPurPrice();
            Integer purNum = info.getPurNum();
            Integer storeId = info.getStoreId();
            String storeLoc = info.getStoreLoc();
            String unit = info.getUnit();
            if (prodDefId == null)
                throw new ParameterIllegalException("产品为空");
            if (purPrice == null)
                throw new ParameterIllegalException("采购单价为空");
            if (storeId == null)
                throw new ParameterIllegalException("仓库为空");
            if (StringUtils.isBlank(unit))
                throw new ParameterIllegalException("单位为空");
            if (StringUtils.isBlank(storeLoc))
                throw new ParameterIllegalException("库位为空");
            if (purNum == null)
                throw new ParameterIllegalException("采购数量为空");
            // 该采购单总价
            totalPrice += purPrice * purNum;
            // 产品信息
            for (int i = 0; i< purNum; i++) {
                ProductDetailInStore productDetailInStore = new ProductDetailInStore();
                productDetailInStore.setProdStatus(ProductStatusEmun.IN_STORE.getIndex())
                        .setPddefId(prodDefId)
                        .setPurId(purchaseReq.getId())
                        .setPurPrice(purPrice)
                        .setStoreId(storeId)
                        .setStoreLoc(storeLoc);
                productDetailsInstore.add(productDetailInStore);
                CreateFieldFill.fill(productDetailInStore);
            }
        }
        purchaseReq.setPurTotalMoney(totalPrice);
        // 更新价格
        purchaseReq.updateById();
        // 保存产品信息
        productDetailInStoreMapper.insertBatch(productDetailsInstore);
    }
}
