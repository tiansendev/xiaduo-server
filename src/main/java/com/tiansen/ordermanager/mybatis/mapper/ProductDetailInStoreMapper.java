package com.tiansen.ordermanager.mybatis.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tiansen.ordermanager.mybatis.entity.ProductDetailInStore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tiansen.ordermanager.mybatis.entity.join.combination.ProductDefTotalNumberOfOrder;
import com.tiansen.ordermanager.mybatis.entity.join.product.ProductDetailInStoreInfo;
import net.sf.ehcache.util.ProductInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author rylai
 * @since 2019-01-02
 */
public interface ProductDetailInStoreMapper extends BaseMapper<ProductDetailInStore> {
    Page<ProductDetailInStoreInfo> findByCondPage(Page<ProductInfo> page, @Param("condMap")Map<String, Object> map);

    void insertBatch(List<ProductDetailInStore> productDetails);

    List<ProductDetailInStore> findDetailsByProductDefNumbers(List<ProductDefTotalNumberOfOrder> productDefIdNumbers);
}
