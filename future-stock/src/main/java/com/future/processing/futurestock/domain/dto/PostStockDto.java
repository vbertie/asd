package com.future.processing.futurestock.domain.dto;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.io.Serializable;

import java.util.List;

@Value
@Builder
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class PostStockDto implements Serializable {

    private String id;
    private String publicationDate;
    private List<StockItemDto> items;
}
