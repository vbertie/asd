package com.future.processing.futurestock.domain;

import com.future.processing.futurestock.domain.dto.stream.SaleDto;
import com.future.processing.futurestock.domain.model.Stock;

interface SaleManager {
    Stock processSale(Stock stock, SaleDto saleDto);
}
