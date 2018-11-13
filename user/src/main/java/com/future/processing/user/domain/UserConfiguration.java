package com.future.processing.user.domain;

import com.future.processing.user.domain.dto.post.PostUserDto;
import com.future.processing.user.domain.dto.put.PutUserDto;
import com.future.processing.user.domain.model.User;
import com.future.processing.user.domain.stream.stock.channel.StockChannel;

import lombok.NoArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@NoArgsConstructor
class UserConfiguration {

    public UserFacade buildFacade() {

        SuperConverter<PostUserDto, User, PutUserDto> converter = new UserConverter();
        InMemoryUserRepository users = new InMemoryUserRepository();
        InMemoryWalletRepository wallets = new InMemoryWalletRepository();

        PaymentManager paymentManager = new PaymentManagerImpl();
        SaleManager saleManager = new SaleManagerImpl();

        return new UserFacade(users, wallets, converter, paymentManager, saleManager);
    }

    @Bean
    public UserFacade userFacade(UserRepository users, WalletRepository wallets, StockChannel stockChannel) {

        SuperConverter<PostUserDto, User, PutUserDto> converter = new UserConverter();
        PaymentManager paymentManager = new PaymentManagerImpl();
        SaleManager saleManager = new SaleManagerImpl(stockChannel);

        return new UserFacade(users, wallets, converter, paymentManager, saleManager);
    }

}
