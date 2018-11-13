package com.future.processing.futurestock.domain.stream;

import com.future.processing.futurestock.domain.StockFacade;
import com.future.processing.futurestock.domain.dto.stream.SaleDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import static com.future.processing.futurestock.domain.stream.user.channel.UserChannel.PROCESS_SALE_REQUEST;

@Component
@Slf4j
@RequiredArgsConstructor
class SaleStream {

    private final StockFacade stockFacade;

    @StreamListener(PROCESS_SALE_REQUEST)
    public void processPaymentRequest(SaleDto saleDto) {
        log.info("JESTEM TU KURWO");
        stockFacade.processSale(saleDto);
    }
}
