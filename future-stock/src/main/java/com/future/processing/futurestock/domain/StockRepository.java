package com.future.processing.futurestock.domain;

import com.future.processing.futurestock.domain.dto.StockDto;
import com.future.processing.futurestock.domain.model.Stock;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.Optional;

interface StockRepository extends Repository<Stock, String> {

    void save(Stock stock);

    Optional<Stock> findByStockName(String stockName);

    Page<Stock> findAll(Pageable pageable);

    Optional<StockDto> findOptionalByStockName(String stockName);

    Optional<Stock> findById(String id);
}
