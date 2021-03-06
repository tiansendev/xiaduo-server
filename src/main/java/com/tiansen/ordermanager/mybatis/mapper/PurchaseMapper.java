package com.tiansen.ordermanager.mybatis.mapper;

import com.tiansen.ordermanager.mybatis.entity.Purchase;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tiansen.ordermanager.mybatis.entity.join.purchase.PurchaseDetail;

/**
 * <p>
 * 采购 Mapper 接口
 * </p>
 *
 * @author rylai
 * @since 2019-01-02
 */
public interface PurchaseMapper extends BaseMapper<Purchase> {

    PurchaseDetail findDetailById(Integer id);
}
