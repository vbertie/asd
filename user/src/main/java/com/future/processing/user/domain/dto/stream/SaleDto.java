package com.future.processing.user.domain.dto.stream;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.io.Serializable;

@Value
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class SaleDto implements Serializable {

    private String stockName;
    private int amount;
    private String companyName;
    private Long userId;
}
