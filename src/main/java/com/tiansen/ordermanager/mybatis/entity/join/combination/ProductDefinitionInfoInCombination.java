package com.tiansen.ordermanager.mybatis.entity.join.combination;

import com.tiansen.ordermanager.mybatis.entity.ProductDefinition;
import lombok.Data;

@Data
public class ProductDefinitionInfoInCombination extends ProductDefinition {
    private Integer prodNum;
    private String prodUnit;
    private String combDefRemark;
}
