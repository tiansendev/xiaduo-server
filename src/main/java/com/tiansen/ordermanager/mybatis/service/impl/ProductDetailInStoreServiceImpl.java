package com.tiansen.ordermanager.mybatis.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tiansen.ordermanager.mybatis.entity.*;
import com.tiansen.ordermanager.mybatis.entity.join.product.ProductDetailInStoreInfo;
import com.tiansen.ordermanager.common.util.SortProcessor;
import com.tiansen.ordermanager.mybatis.mapper.ProductDetailInStoreMapper;
import com.tiansen.ordermanager.mybatis.service.IProductInStoreDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.sf.ehcache.util.ProductInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rylai
 * @since 2019-01-02
 */
@Service
public class ProductDetailInStoreServiceImpl extends ServiceImpl<ProductDetailInStoreMapper, ProductDetailInStore> implements IProductInStoreDetailService {

    @Autowired
    private ProductDetailInStoreMapper productDetailMapper;

    @Override
    public Page<ProductDetailInStoreInfo> findByCondPage(String name, String status, Integer defId, Integer purId, Integer storeId,
                                                         Integer orderId, Long purDateStartTime, Long purDateEndTime, Long orderDateStartTime,
                                                         Long orderDateEndTime, String patterName, Pageable pageable) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(name))
            map.put(ProductDefinition.PROD_DEF_NAME, name);
        if (StringUtils.isNotBlank(name))
            map.put(ProductDetailInStore.PROD_STATUS, status);
        if (defId != null)
            map.put(ProductDetailInStore.PDDEF_ID, defId);
        if (purId != null)
            map.put(ProductDetailInStore.PUR_ID, purId);
        if (storeId != null)
            map.put(ProductDetailInStore.STORE_ID, storeId);
        if (orderId != null)
            map.put(ProductDetailInStore.ORDER_ID, orderId);
        if (orderDateStartTime != null)
            map.put("order_date_start_time", new Date(orderDateStartTime));
        if (orderDateEndTime != null)
            map.put("order_date_end_time", new Date(orderDateEndTime));
        if (purDateStartTime != null)
            map.put("pur_date_start_time", new Date(purDateStartTime));
        if (purDateEndTime != null)
            map.put("pur_date_end_time", new Date(purDateEndTime));

        if (StringUtils.isNotBlank(patterName))
            map.put("pattern_name", patterName);
        Page<ProductInfo> page = new Page<>(pageable.getPageNumber(), pageable.getPageSize());
        SortProcessor.process(page, pageable);
        return productDetailMapper.findByCondPage(page, map);
    }
}
