package com.future.processing.user.domain.model;

import com.future.processing.user.domain.dto.StockItemDto;

import lombok.*;
import lombok.experimental.Wither;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

import java.math.BigDecimal;

@Value
@Embeddable
@Builder
@Wither
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(force = true)
public class StockItem {

    @Column(length = 40, nullable = false)
    private String name;

    @Column(length = 10, nullable = false)
    private String code;

    @Column
    private int amount;

    @Min(0)
    @Digits(integer = 7, fraction = 4)
    private BigDecimal price;

    public StockItemDto dto() {
        return StockItemDto.builder()
                .code(code)
                .price(price)
                .name(name)
                .build();
    }
}
