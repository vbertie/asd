package com.future.processing.futurestock.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.io.Serializable;

import java.math.BigDecimal;

@Value
@Builder
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class StockItemDto implements Serializable {

    @JsonProperty("name")
    private String name;

    private String code;

    private int unit;

    private BigDecimal price;
}
