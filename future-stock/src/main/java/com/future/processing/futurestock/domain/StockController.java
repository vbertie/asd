package com.future.processing.futurestock.domain;

import com.future.processing.futurestock.domain.StockFacade;
import com.future.processing.futurestock.domain.dto.StockDto;
import com.future.processing.futurestock.domain.dto.stream.PurchaseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/stocks")
@CrossOrigin(origins = "https://localhost:4200") /** add your's */
class StockController {

    private final StockFacade stockFacade;

    @GetMapping("/future")
    @ResponseStatus(HttpStatus.OK)
    public StockDto getStock() {
        return stockFacade.findOptionalByStockName("Future Stock");
    }

    @PutMapping("/purchase")
    @ResponseStatus(HttpStatus.OK)
    public void processPurchase(@RequestBody PurchaseDto dto) {
        stockFacade.processPurchase(dto);
    }
}
