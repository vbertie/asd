package com.future.processing.user;

import com.future.processing.user.domain.dto.StockItemDto;

import java.math.BigDecimal;

public interface SampleStockItems {

    StockItemDto fp = createStockItem("FP", "fp-07", 50, new BigDecimal("13.23"));
    StockItemDto coin = createStockItem("coin", "coin-07", 15, new BigDecimal("14.11"));

    static StockItemDto createStockItem(String name, String code, int unit, BigDecimal price) {
        return StockItemDto.builder()
                .name(name)
                .code(code)
                .unit(unit)
                .price(price)
                .build();
    }
}