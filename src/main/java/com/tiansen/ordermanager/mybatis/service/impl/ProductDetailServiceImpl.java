package com.tiansen.ordermanager.mybatis.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tiansen.ordermanager.mybatis.entity.*;
import com.tiansen.ordermanager.mybatis.entity.join.ProductDetailInfo;
import com.tiansen.ordermanager.mybatis.fill.DefaultOrderFill;
import com.tiansen.ordermanager.mybatis.mapper.ProductDetailMapper;
import com.tiansen.ordermanager.mybatis.service.IProductDetailService;
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
public class ProductDetailServiceImpl extends ServiceImpl<ProductDetailMapper, ProductDetail> implements IProductDetailService {

    @Autowired
    private ProductDetailMapper productDetailMapper;

    @Override
    public Page<ProductDetailInfo> findByCondPage(String name, String status, Integer defId, Integer purId, Integer storeId,
                                                  Integer orderId, Long purDateStartTime, Long purDateEndTime, Long orderDateStartTime,
                                                  Long orderDateEndTime, String patterName, Pageable pageable) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(name))
            map.put(ProductDefinition.PROD_DEF_NAME, name);
        if (StringUtils.isNotBlank(name))
            map.put(ProductDetail.PROD_STATUS, status);
        if (defId != null)
            map.put(ProductDetail.PDDEF_ID, defId);
        if (purId != null)
            map.put(ProductDetail.PUR_ID, purId);
        if (storeId != null)
            map.put(ProductDetail.STORE_ID, storeId);
        if (orderId != null)
            map.put(ProductDetail.ORDER_ID, orderId);
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
        DefaultOrderFill.fillOrderDefault(page);
        return productDetailMapper.findByCondPage(page, map);
    }
}
