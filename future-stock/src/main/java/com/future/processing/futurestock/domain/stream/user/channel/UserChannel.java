package com.future.processing.futurestock.domain.stream.user.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;

import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface UserChannel {

    String PROCESS_USER_PURCHASE = "processUserPurchase";
    String PROCESS_SALE_REQUEST = "processSaleRequest";

    @Output(PROCESS_USER_PURCHASE)
    MessageChannel processUserPurchase();

    @Input(PROCESS_SALE_REQUEST)
    SubscribableChannel processSaleRequest();
}
