package com.future.processing.user.domain.dto;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

@Value
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class StockItemDto implements Serializable {

    private String name;

    private String code;

    private int unit;

    private BigDecimal price;

    private int amount;
}
