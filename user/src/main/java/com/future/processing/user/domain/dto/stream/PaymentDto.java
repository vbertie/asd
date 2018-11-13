package com.future.processing.user.domain.dto.stream;

import com.future.processing.user.domain.dto.StockItemDto;

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
public class PaymentDto implements Serializable {

    private Long userId;
    private int amount;
    private BigDecimal payment;
    private StockItemDto stockItem;
}
