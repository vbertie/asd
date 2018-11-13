package com.future.processing.user.domain.stream;

import com.future.processing.user.domain.UserFacade;
import com.future.processing.user.domain.dto.stream.PaymentDto;

import lombok.RequiredArgsConstructor;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import static com.future.processing.user.domain.stream.stock.channel.StockChannel.PAYMENT_REQUEST_CHANNEL;

@Component
@RequiredArgsConstructor
class PaymentStream {

    private final UserFacade userFacade;

    @StreamListener(PAYMENT_REQUEST_CHANNEL)
    public void processPaymentRequest(PaymentDto paymentDto) {
        userFacade.processPayment(paymentDto);
    }
}
