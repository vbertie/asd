package com.future.processing.user.domain;

import com.future.processing.user.domain.dto.stream.PaymentDto;
import com.future.processing.user.domain.model.StockItem;
import com.future.processing.user.domain.model.User;
import com.future.processing.user.domain.model.Wallet;

import lombok.extern.slf4j.Slf4j;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional
class PaymentManagerImpl implements PaymentManager {

    @Override
    public User processPayment(User user, PaymentDto dto) {
        return user.withWallet(processWallet(user.getWallet(), dto));
    }

    private Wallet processWallet(Wallet wallet, PaymentDto dto) {
        return Wallet.builder()
                .id(wallet.getId())
                .balance(subtractPayment(wallet.getBalance(), dto.getPayment()))
                .stockItems(processStockItems(wallet.getStockItems(), dto.getAmount(), dto))
                .build();
    }

    private BigDecimal subtractPayment(BigDecimal balance, BigDecimal payment) {

        BigDecimal calculatedBalance = new BigDecimal(String.valueOf(balance.subtract(payment)));

        return calculatedBalance.setScale(4, RoundingMode.HALF_DOWN);
    }

    private List<StockItem> processStockItems(List<StockItem> stockItems, int amount, PaymentDto dto) {

        List<StockItem> processedList = stockItems.stream()
                .map(it -> processStockItem(it, amount, dto.getStockItem().getName()))
                .collect(Collectors.toList());

        boolean isPresent = false;

        for (StockItem stockItem : processedList) {

            if (stockItem.getName().equals(dto.getStockItem().getName())) {
                isPresent = true;
                break;
            }
        }

        if (!isPresent) {
            processedList.add(convertToStockItem(dto));
        }

        return processedList;
    }

    private StockItem convertToStockItem(PaymentDto dto) {
        return StockItem.builder()
                .amount(dto.getAmount())
                .price(dto.getStockItem().getPrice())
                .name(dto.getStockItem().getName())
                .code(dto.getStockItem().getCode())
                .build();
    }

    private StockItem processStockItem(StockItem item, int amount, String company) {
        return item.withAmount(item.getName().equals(company) ?
                item.getAmount() + amount : item.getAmount());
    }
}
