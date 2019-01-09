package com.tiansen.ordermanager.mybatis.entity.join.combination;

import com.tiansen.ordermanager.mybatis.entity.ProductDefinition;
import lombok.Data;

import java.util.Objects;

/**
 * 订单中每个产品定义-总数量
 */
@Data
public class ProductDefTotalNumberOfOrder extends ProductDefinition {
    private Integer totalNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDefTotalNumberOfOrder that = (ProductDefTotalNumberOfOrder) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
