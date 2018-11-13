package com.future.processing.futurestock.domain;

import com.future.processing.futurestock.domain.dto.stream.PurchaseDto;
import com.future.processing.futurestock.domain.model.Stock;
import com.future.processing.futurestock.domain.model.StockItem;
import com.future.processing.futurestock.domain.stream.user.channel.UserChannel;

import lombok.NoArgsConstructor;

import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
class PurchaseManagerImpl implements PurchaseManager {

    private MessageChannel channel;

    public PurchaseManagerImpl(UserChannel userChannel) {
        this.channel = userChannel.processUserPurchase();
    }

    @Override
    public Stock process(Stock stock, PurchaseDto dto) {
        return Stock.builder()
                .id(stock.getId())
                .date(stock.getDate())
                .stockName(stock.getStockName())
                .stockItems(processItems(stock.getStockItems(), dto.getStockItem().getName(), dto.getAmount()))
                .build();
    }

    @Override
    public void processUserPayment(PurchaseDto dto) {
        if (channel != null) {
            channel.send(new GenericMessage<>(dto)); /** not so proud of this, but couldn't inject it for tests :( **/
        }
    }

    private List<StockItem> processItems(List<StockItem> items, String name, int amount) {
            return items
                    .stream()
                    .map(it -> subtractAmount(it, name, amount))
                    .collect(Collectors.toList());
    }

    private StockItem subtractAmount(StockItem item, String name, int amount) {
        return item.withAmount(item.getName().equals(name) ?
                item.getAmount() - amount : item.getAmount());
    }

}
