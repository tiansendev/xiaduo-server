package com.tiansen.ordermanager.mybatis.mapper;

import com.tiansen.ordermanager.mybatis.entity.ProductDetailOutStore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 出库产品 Mapper 接口
 * </p>
 *
 * @author rylai
 * @since 2019-01-06
 */
public interface ProductDetailOutStoreMapper extends BaseMapper<ProductDetailOutStore> {

    void insertBatch(List<ProductDetailOutStore> outStores);
}
