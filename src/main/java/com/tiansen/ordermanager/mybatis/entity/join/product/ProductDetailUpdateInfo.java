package com.tiansen.ordermanager.mybatis.entity.join.product;

import com.tiansen.ordermanager.mybatis.entity.ProductDetailOutStore;
import lombok.Data;

@Data
public class ProductDetailUpdateInfo extends ProductDetailOutStore {
    private Integer outNum;
}
