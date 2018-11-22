package com.future.processing.futurestock.domain;

import com.future.processing.futurestock.domain.api.Consumer;
import com.future.processing.futurestock.domain.api.ProcessingStockConsumer;
import com.future.processing.futurestock.domain.dto.PostStockDto;
import com.future.processing.futurestock.domain.model.Stock;
import com.future.processing.futurestock.domain.stream.user.channel.UserChannel;

import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@NoArgsConstructor
class StockConfiguration {

    public StockFacade buildFacade(Consumer<PostStockDto> consumer) {

        SuperConverter<PostStockDto, Stock> converter = new StockConverter();
        InMemoryStockRepository stocks = new InMemoryStockRepository();
        PurchaseManager purchaseManager = new PurchaseManagerImpl();
        SaleManager saleManager = new SaleManagerImpl();

        return new StockFacade(stocks, consumer, converter, purchaseManager,saleManager);
    }

    @Bean
    public StockFacade stockFacade(StockRepository stocks, UserChannel userChannel) {

        Consumer<PostStockDto> consumer = new ProcessingStockConsumer();
        SuperConverter<PostStockDto, Stock> converter = new StockConverter();
        PurchaseManager purchaseManager = new PurchaseManagerImpl(userChannel);
        SaleManager saleManager = new SaleManagerImpl();

        return new StockFacade(stocks, consumer, converter, purchaseManager, saleManager);
    }
}
