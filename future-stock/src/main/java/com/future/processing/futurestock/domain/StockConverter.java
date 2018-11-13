package com.future.processing.futurestock.domain;

import com.future.processing.futurestock.domain.dto.PostStockDto;
import com.future.processing.futurestock.domain.dto.StockItemDto;
import com.future.processing.futurestock.domain.model.Stock;
import com.future.processing.futurestock.domain.model.StockItem;

import lombok.Synchronized;

import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class StockConverter implements SuperConverter<PostStockDto, Stock> {

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    @Override
    public Stock convert(@NotNull PostStockDto postStockDto) {
        return create(postStockDto, new Stock());
    }

    @Override
    public Collection<Stock> convert(@NotNull Collection<PostStockDto> t) {
        return null;
    }

    @Override
    public Stock update(@NotNull PostStockDto updater, @NotNull Stock existingStock) {
        return Stock.builder()
                .id(existingStock.getId())
                .stockName(existingStock.getStockName())
                .stockItems(updateItems(updater.getItems(), existingStock.getStockItems()))
                .date(parseDate(updater.getPublicationDate()))
                .build();
    }

    @Override
    public Stock create(@NotNull PostStockDto updater, @NotNull Stock result) {
        return Stock.builder()
                .id(updater.getId())
                .stockName("Future Stock")
                .stockItems(createItems(updater.getItems()))
                .date(parseDate(updater.getPublicationDate()))
                .build();
    }

    private LocalDateTime parseDate(String publicationDate) {
        return LocalDateTime.parse(publicationDate, formatter);
    }

    private List<StockItem> updateItems(List<StockItemDto> stockItemDtos, List<StockItem> stockItems) {
        return IntStream.range(0, stockItems.size())
                .mapToObj(it -> convertToItem(stockItemDtos.get(it), stockItems.get(it)))
                .collect(Collectors.toList());
    }

    private List<StockItem> createItems(List<StockItemDto> stockItemDtos) {
        return stockItemDtos
                .stream()
                .map(this::convertToItem)
                .collect(Collectors.toList());
    }

    private StockItem convertToItem(StockItemDto dto, StockItem item) {
        return StockItem.builder()
                .unit(dto.getUnit())
                .code(dto.getCode())
                .name(dto.getName())
                .price(dto.getPrice())
                .amount(item.getAmount())
                .build();
    }

    private StockItem convertToItem(StockItemDto dto) {
        return StockItem.builder()
                .unit(dto.getUnit())
                .code(dto.getCode())
                .name(dto.getName())
                .price(dto.getPrice())
                .amount(1500) /** starter amount for every stock share **/
                .build();
    }
}
