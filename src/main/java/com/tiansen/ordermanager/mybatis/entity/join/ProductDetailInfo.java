package com.tiansen.ordermanager.mybatis.entity.join;

import com.tiansen.ordermanager.mybatis.entity.*;
import lombok.Data;

@Data
public class ProductDetailInfo extends ProductDetail {
    private Purchase purchase;
    private ProductDefinition productDefinition;
    private Store store;
    private Order order;
}
