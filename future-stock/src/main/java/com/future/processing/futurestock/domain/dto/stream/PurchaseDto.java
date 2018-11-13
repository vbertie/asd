package com.future.processing.futurestock.domain.dto.stream;

import com.future.processing.futurestock.domain.dto.StockItemDto;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.Wither;

import java.io.Serializable;

import java.math.BigDecimal;

@Value
@Builder
@Wither
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class PurchaseDto implements Serializable {

    private Long userId;

    private int amount;

    private BigDecimal payment;

    private StockItemDto stockItem;
}
