package com.future.processing.futurestock

import com.future.processing.futurestock.domain.StockConfiguration
import com.future.processing.futurestock.domain.StockFacade
import com.future.processing.futurestock.domain.api.ProcessingStockConsumer
import com.future.processing.futurestock.domain.dto.PostStockDto
import com.future.processing.futurestock.domain.dto.stream.PurchaseDto
import com.future.processing.futurestock.domain.dto.stream.SaleDto
import com.future.processing.futurestock.domain.model.Stock

import org.mockito.Mock
import org.mockito.MockitoAnnotations

import spock.lang.Specification

import static org.mockito.Mockito.when

class StockSpecTest extends Specification implements SampleItems {

    @Mock ProcessingStockConsumer consumer

    StockFacade stockFacade

    final String DATE = "2018-11-10T11:55:53.4648892Z"
    final String UPDATED_DATE = "2013-05-10T11:55:53.4648892Z"

    def setup() {
        MockitoAnnotations.initMocks(this)
        stockFacade = new StockConfiguration().buildFacade(consumer)
    }

    def "should consume stock with items to database" () {

        given:

        PostStockDto dto = PostStockDto.builder()
                .id("123")
                .items(Arrays.asList(fp,adv))
                .publicationDate(DATE)
                .build()

            when(consumer.consume()).thenReturn(dto)

        when:

            stockFacade.futureProcessingConsumer()

        then:
            stockFacade.findById(dto.id) != null
    }

    def "should update stock shares if stock already exists" () {

        given:

        PostStockDto dto = PostStockDto.builder()
                .id("123")
                .items(Arrays.asList(fp,adv))
                .publicationDate(DATE)
                .build()

        PostStockDto dto2 = PostStockDto.builder()
                .items(Arrays.asList(coin,adv))
                .publicationDate(UPDATED_DATE)
                .build()

            when(consumer.consume()).thenReturn(dto).thenReturn(dto2)

        when:

            stockFacade.futureProcessingConsumer()
            stockFacade.futureProcessingConsumer()

        then:

        Stock stock = stockFacade.findById(dto.id)

            String.valueOf(stock.date) != DATE
    }

    def "should process purchase" () {

        given:

            PurchaseDto purchaseDto = PurchaseDto.builder()
                    .stockItem(fp)
                    .payment(new BigDecimal(55))
                    .amount(10)
                    .build()

            PostStockDto dto = PostStockDto.builder()
                    .id("123")
                    .items(Arrays.asList(fp,adv))
                    .publicationDate(DATE)
                    .build()

            when(consumer.consume()).thenReturn(dto)

            stockFacade.futureProcessingConsumer()

            Stock stock = stockFacade.findById(dto.id)

            int amountBeforeProcess = stock.stockItems.get(0).amount

        when:

            stockFacade.processPurchase(purchaseDto)

        then:

            Stock processedStock = stockFacade.findById(dto.id)

            processedStock.stockItems.get(0).amount == amountBeforeProcess - purchaseDto.amount

    }

    def "should process user's sale" () {

        given:

            SaleDto saleDto = SaleDto.builder()
                    .amount(25)
                    .stockName("Future Stock")
                    .companyName("Future Processing")
                    .build()

            PostStockDto dto = PostStockDto.builder()
                    .id("123")
                    .items(Arrays.asList(fp,adv))
                    .publicationDate(DATE)
                    .build()

            when(consumer.consume()).thenReturn(dto)

            stockFacade.futureProcessingConsumer()

            int amountBefore = stockFacade.findById(dto.id).stockItems.get(0).amount

        when:

            stockFacade.processSale(saleDto)

        then:

            Stock stock = stockFacade.findById(dto.id)

            stock.stockItems.get(0).amount == amountBefore + saleDto.amount
    }
}
