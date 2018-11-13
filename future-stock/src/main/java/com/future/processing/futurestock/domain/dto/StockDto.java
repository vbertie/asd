package com.future.processing.futurestock.domain.dto;

import com.future.processing.futurestock.domain.model.StockItem;

import java.time.LocalDateTime;

import java.util.List;

public interface StockDto {

    String getId();

    List<StockItem> getStockItems();

    String getStockName();

    LocalDateTime getDate();
}
