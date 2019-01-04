package com.tiansen.ordermanager.mybatis.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tiansen.ordermanager.mybatis.entity.ProductDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tiansen.ordermanager.mybatis.entity.join.ProductDetailInfo;
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
public interface ProductDetailMapper extends BaseMapper<ProductDetail> {
    Page<ProductDetailInfo> findByCondPage(Page<ProductInfo> page, @Param("condMap")Map<String, Object> map);

    void insertBatch(List<ProductDetail> productDetails);
}
