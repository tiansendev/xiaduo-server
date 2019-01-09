package com.tiansen.ordermanager.mybatis.entity.join.product;

import com.tiansen.ordermanager.mybatis.entity.*;
import lombok.Data;

@Data
public class ProductDetailInStoreInfo extends ProductDetailInStore {
    private Purchase purchase;
    private ProductDefinition productDefinition;
    private Store store;
    private Order order;
}
