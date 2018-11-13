package com.future.processing.user.domain.model;

import lombok.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

import java.math.BigDecimal;

import java.time.LocalDateTime;

import java.util.List;

@Value
@Entity
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode(of = "id", callSuper = false)
public class Wallet {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Min(0)
    @Digits(integer = 10, fraction = 4)
    private BigDecimal balance;

    @ElementCollection
    @CollectionTable(name = "stock_items")
    @Embedded
    private List<StockItem> stockItems;

    @CreatedDate
    @Column(insertable = true, updatable = false)
    private LocalDateTime creationDate;

    @LastModifiedDate
    @Column(insertable = true, updatable = true)
    private LocalDateTime lastModifiedDate;
}
