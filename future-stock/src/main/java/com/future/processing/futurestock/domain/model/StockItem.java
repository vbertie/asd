package com.future.processing.futurestock.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

import java.io.Serializable;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor(force = true)
@Builder
@Wither
@AllArgsConstructor
public class StockItem implements Serializable {

    private String name;

    private String code;

    private int unit;

    private BigDecimal price;

    private int amount;
}
