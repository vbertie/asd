package com.future.processing.futurestock.domain;

import com.future.processing.futurestock.domain.dto.StockDto;
import com.future.processing.futurestock.domain.model.Stock;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryStockRepository implements StockRepository {

    private Map<String, Stock> stocks = new ConcurrentHashMap<>();

    @Override
    public void save(Stock stock) {
        stocks.put(stock.getId(), stock);
    }

    @Override
    public Optional<Stock> findByStockName(String stockName) {
        return Optional.ofNullable(findStockByUsername(stockName));
    }

    @Override
    public Page<Stock> findAll(Pageable pageable) {

        List<Stock>  stockList = new ArrayList<>(stocks.values());

        return new PageImpl<>(stockList, pageable, stockList.size());
    }

    @Override
    public Optional<StockDto> findOptionalByStockName(String stockName) {
        return Optional.empty();
    }

    @Override
    public Optional<Stock> findById(String id) {
        return Optional.ofNullable(stocks.get(id));
    }

    private Stock findStockByUsername(String name) {
        return stocks.values()
                .stream()
                .filter(it -> it.getStockName().equals(name))
                .findFirst().orElse(null);
    }
}
