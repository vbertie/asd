package com.future.processing.user.domain.stream.stock.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;

import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface StockChannel {

    String PAYMENT_REQUEST_CHANNEL = "paymentRequestChannel";
    String PROCESS_SALE = "processSale";

    @Input(PAYMENT_REQUEST_CHANNEL)
    SubscribableChannel processPaymentRequest();

    @Output(PROCESS_SALE)
    MessageChannel processSale();
}
