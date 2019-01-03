package com.tiansen.ordermanager.mybatis.entity.join;

import com.tiansen.ordermanager.mybatis.entity.Combination;
import com.tiansen.ordermanager.mybatis.entity.ProductDefinition;
import lombok.Data;

import java.util.List;

@Data
public class CombinationDetail extends Combination {
    private List<ProductDefinition> productDefinitions;
}
