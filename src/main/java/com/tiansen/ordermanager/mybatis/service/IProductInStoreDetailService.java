package com.tiansen.ordermanager.mybatis.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tiansen.ordermanager.mybatis.entity.ProductDetailInStore;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tiansen.ordermanager.mybatis.entity.join.product.ProductDetailInStoreInfo;
import org.springframework.data.domain.Pageable;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rylai
 * @since 2019-01-02
 */
public interface IProductInStoreDetailService extends IService<ProductDetailInStore> {

    Page<ProductDetailInStoreInfo> findByCondPage(String name, String status, Integer defId, Integer purId, Integer storeId, Integer orderId, Long purDateStartTime, Long purDateEndTime, Long orderDateStartTime, Long orderDateEndTime, String patterName, Pageable pageable);
}
