package com.future.processing.user.domain;

import com.future.processing.user.domain.dto.stream.SaleDto;
import com.future.processing.user.domain.model.User;

interface SaleManager {

    User process(SaleDto dto, User user);

    void processUserSaleToStock(SaleDto dto);
}
