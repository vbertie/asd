package com.future.processing.futurestock.domain;

import com.future.processing.futurestock.domain.api.Consumer;
import com.future.processing.futurestock.domain.dto.PostStockDto;
import com.future.processing.futurestock.domain.dto.StockDto;
import com.future.processing.futurestock.domain.dto.stream.PurchaseDto;
import com.future.processing.futurestock.domain.dto.stream.SaleDto;
import com.future.processing.futurestock.domain.model.Stock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Scheduled;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class StockFacade {

    private final StockRepository stockRepository;
    private final Consumer<PostStockDto> futureConsumer;
    private final SuperConverter<PostStockDto, Stock> converter;
    private final PurchaseManager purchaseManager;
    private final SaleManager saleManager;

    @Scheduled(fixedRate = 30000)
    public void futureProcessingConsumer() {

        PostStockDto dto = futureConsumer.consume();

        Optional<Stock> existingStock = stockRepository.findByStockName("Future Stock");

        Stock stock = existingStock.isPresent() ? converter.update(dto, existingStock.get()) : converter.convert(dto);

        stockRepository.save(stock);

        log.info("Saved stock - {} - to database", stock.getStockName());
    }

    public void processPurchase(PurchaseDto dto) {

        Stock stock = stockRepository.findByStockName("Future Stock")
                .orElseThrow(IllegalArgumentException::new);

        Stock processedStock = purchaseManager.process(stock,dto);

        stockRepository.save(processedStock);

        log.info("Saved processed stock");

        purchaseManager.processUserPayment(dto);
    }

    public void processSale(SaleDto saleDto) {

        Stock stock = stockRepository.findByStockName(saleDto.getStockName())
                .orElseThrow(IllegalArgumentException::new);

        Stock processedStock = saleManager.processSale(stock, saleDto);

        stockRepository.save(processedStock);
    }

    public Stock findById(String id) {
        return stockRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }

    public StockDto findOptionalByStockName(String stockName) {
        return stockRepository.findOptionalByStockName(stockName)
                .orElseThrow(IllegalArgumentException::new);
    }
}
