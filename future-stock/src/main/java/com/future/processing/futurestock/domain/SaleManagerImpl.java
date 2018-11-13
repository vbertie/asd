package com.future.processing.futurestock.domain;

import com.future.processing.futurestock.domain.dto.stream.SaleDto;
import com.future.processing.futurestock.domain.model.Stock;
import com.future.processing.futurestock.domain.model.StockItem;

import java.util.List;
import java.util.stream.Collectors;

class SaleManagerImpl implements SaleManager {

    @Override
    public Stock processSale(Stock stock, SaleDto saleDto) {
        return stock.withStockItems(processStockItems(stock.getStockItems(), saleDto));
    }

    private List<StockItem> processStockItems(List<StockItem> stockItems, SaleDto saleDto) {
        return stockItems
                .stream()
                .map(it -> processStockItem(it, saleDto.getCompanyName(), saleDto.getAmount()))
                .collect(Collectors.toList());
    }

    private StockItem processStockItem(StockItem it, String companyName, int amount) {
        return it.withAmount(it.getName().equals(companyName) ?
                it.getAmount() + amount : it.getAmount());
    }
}
