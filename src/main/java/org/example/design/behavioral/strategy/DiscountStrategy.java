package org.example.design.behavioral.strategy;

import java.math.BigDecimal;
import java.util.Objects;

import lombok.Getter;

/**
 *  折扣策略父类
 *
 * Author: GL
 * Date: 2021-11-22
 */
public abstract class DiscountStrategy implements Strategy {
    // 折扣粒度
    @Getter
    private final String discount;

    public DiscountStrategy(String discount) {
        Objects.requireNonNull(discount, "Discount granularity cannot be blank");
        this.discount = discount;
    }

    // 计算折扣额度:
    protected abstract BigDecimal getDiscount(BigDecimal total);
}
