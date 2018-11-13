package com.future.processing.futurestock.domain;

import com.future.processing.futurestock.domain.dto.stream.PurchaseDto;
import com.future.processing.futurestock.domain.model.Stock;

interface PurchaseManager {

    Stock process(Stock stock, PurchaseDto dto);

    void processUserPayment(PurchaseDto dto);
}
