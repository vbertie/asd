package com.future.processing.user.domain;

import com.future.processing.user.domain.dto.stream.PaymentDto;
import com.future.processing.user.domain.model.User;

interface PaymentManager {
    User processPayment(User user, PaymentDto paymentDto);
}
