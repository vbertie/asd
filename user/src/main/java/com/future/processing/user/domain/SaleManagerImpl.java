package com.future.processing.user.domain;

import com.future.processing.user.domain.dto.stream.SaleDto;
import com.future.processing.user.domain.model.StockItem;
import com.future.processing.user.domain.model.User;
import com.future.processing.user.domain.model.Wallet;
import com.future.processing.user.domain.stream.stock.channel.StockChannel;

import lombok.NoArgsConstructor;

import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@NoArgsConstructor
class SaleManagerImpl implements SaleManager {

    private MessageChannel messageChannel;

    public SaleManagerImpl(StockChannel stockChannel) {
        this.messageChannel = stockChannel.processSale();
    }

    @Override
    public User process(SaleDto dto, User user) {
        return user.withWallet(processWallet(user.getWallet(),dto));
    }

    @Override
    public void processUserSaleToStock(SaleDto dto) {
        if (messageChannel != null) {
            messageChannel.send(new GenericMessage<>(dto)); /** not so proud of this, but couldn't inject it for tests :( **/
        }
    }

    private Wallet processWallet(Wallet wallet, SaleDto dto) {

        StockItem stockItem = wallet.getStockItems()
                .stream()
                .filter(it -> it.getName().equals(dto.getCompanyName()))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);

        return Wallet.builder()
                .id(wallet.getId())
                .balance(addBalance(wallet.getBalance(), dto.getAmount(), stockItem))
                .stockItems(processStockItems(dto.getAmount(), stockItem, wallet.getStockItems()))
                .build();
    }

    private List<StockItem> processStockItems(int amount, StockItem stockItem, List<StockItem> stockItems) {
        return stockItems
                .stream()
                .map(it -> processItem(it, stockItem.getName(), amount))
                .filter(it -> it.getAmount() > 0)
                .collect(Collectors.toList());
    }

    private StockItem processItem(StockItem stockItem, String companyName, int amount) {
        return stockItem.withAmount(stockItem.getName().equals(companyName) ?
                stockItem.getAmount() - amount : stockItem.getAmount());
    }

    private BigDecimal addBalance(BigDecimal balance, int amount, StockItem stockItem) {

        BigDecimal calculatedBalance =
                new BigDecimal(String.valueOf(balance.add(stockItem.getPrice().multiply(new BigDecimal(amount)))));

        return calculatedBalance.setScale(4, RoundingMode.HALF_DOWN);
    }
}
