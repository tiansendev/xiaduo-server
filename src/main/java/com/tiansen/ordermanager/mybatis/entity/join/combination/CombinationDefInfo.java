package com.tiansen.ordermanager.mybatis.entity.join.combination;

import com.tiansen.ordermanager.mybatis.entity.Combination;
import lombok.Data;

import java.util.List;

@Data
public class CombinationDefInfo extends Combination {
    private List<ProductDefinitionInfoInCombination> productDefinitions;
}
