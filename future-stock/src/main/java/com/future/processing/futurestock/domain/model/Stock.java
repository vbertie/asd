package com.future.processing.futurestock.domain.model;

import lombok.*;
import lombok.experimental.Wither;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

import java.util.List;

@Getter
@Builder
@Wither
@NoArgsConstructor(force = true)
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Document(collection = "Stock_Shares")
public class Stock  {

    @Id
    private String id;

    private String stockName;

    private List<StockItem> stockItems;

    private LocalDateTime date;
}
