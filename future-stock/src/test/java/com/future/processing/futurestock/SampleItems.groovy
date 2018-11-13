package com.future.processing.futurestock


import com.future.processing.futurestock.domain.dto.StockItemDto

trait SampleItems {

    StockItemDto fp = createPostItem("Future Processing", "fp-07", 50, new BigDecimal("25.235"))
    StockItemDto adv = createPostItem("Adventure", "adv-07", 25, new BigDecimal("14.094"))
    StockItemDto coin = createPostItem("Bitcoin Deluxe", "coin-07", 15, new BigDecimal("72.111"))

    static private createPostItem(String name, String code, int unit, BigDecimal price) {
        return StockItemDto.builder()
            .name(name)
            .code(code)
            .unit(unit)
            .price(price)
            .build()
    }
}