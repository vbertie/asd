package com.future.processing.user.domain.dto.post;

import com.future.processing.user.domain.dto.StockItemDto;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.validation.constraints.*;

import java.io.Serializable;

import java.util.List;

@Value
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class PostWalletDto implements Serializable {

    private Long id;

    @Pattern(regexp="([0-9]+([.][0-9]{1,2})?)")
    @Size(min = 1, max = 10)
    private String balance;

    private List<StockItemDto> stockItems;
}
