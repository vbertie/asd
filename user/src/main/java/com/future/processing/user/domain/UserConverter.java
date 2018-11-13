package com.future.processing.user.domain;

import com.future.processing.user.domain.dto.*;
import com.future.processing.user.domain.dto.post.PostUserDataDto;
import com.future.processing.user.domain.dto.post.PostUserDto;
import com.future.processing.user.domain.dto.post.PostWalletDto;
import com.future.processing.user.domain.dto.put.PutUserDataDto;
import com.future.processing.user.domain.dto.put.PutUserDto;
import com.future.processing.user.domain.model.StockItem;
import com.future.processing.user.domain.model.User;
import com.future.processing.user.domain.model.User.UserData;
import com.future.processing.user.domain.model.Wallet;

import javax.validation.constraints.NotNull;

import java.math.BigDecimal;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.future.processing.user.domain.configuration.security.SecurityConfiguration.encoder;

class UserConverter implements SuperConverter<PostUserDto, User, PutUserDto> {

    @Override
    public User convert(@NotNull PostUserDto postUserDto) {
        return update(postUserDto, new User());
    }

    @Override
    public Collection<User> convert(@NotNull Collection<PostUserDto> t) {
        return null;
    }

    @Override
    public User update(@NotNull PostUserDto updater, @NotNull User result) {
        return User.builder()
                .id(updater.getId())
                .username(updater.getUsername())
                .userData(createUserData(updater.getUserData()))
                .password(encoder().encode(updater.getPassword()))
                .wallet(createWallet(updater.getWallet()))
                .build();
    }

    @Override
    public User updateExisting(@NotNull PutUserDto updater, @NotNull User result) {
        return User.builder()
                .id(updater.getId())
                .username(updater.getUsername())
                .userData(updateUserData(updater.getUserData()))
                .password(updater.getPassword() != null ?
                        encoder().encode(updater.getPassword()) : result.getPassword())
                .wallet(updateWallet(updater.getWallet()))
                .build();
    }

    private UserData updateUserData(PutUserDataDto dto) {
        return new UserData(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getPhoneNumber(),
                dto.getEmail());
    }

    private UserData createUserData(PostUserDataDto dto) {
        return new UserData(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getPhoneNumber(),
                dto.getEmail());
    }

    private Wallet createWallet(PostWalletDto dto) {
        return Wallet.builder()
                .id(dto.getId())
                .balance(new BigDecimal(dto.getBalance()))
                .stockItems(fetchStockItems(dto.getStockItems()))
                .build();
    }

    private List<StockItem> fetchStockItems(List<StockItemDto> itemsDto) {
        return itemsDto
                .stream()
                .filter(it -> Optional.ofNullable(it).isPresent())
                .map(this::createStockItem)
                .collect(Collectors.toList());
    }

    private StockItem createStockItem(StockItemDto dto) {
        return StockItem.builder()
                .name(dto.getName())
                .code(dto.getCode())
                .price(dto.getPrice())
                .amount(5) /** 5 is basic in register **/
                .build();
    }

    private Wallet updateWallet(PostWalletDto dto) {
        return Wallet.builder()
                .id(dto.getId())
                .balance(new BigDecimal(dto.getBalance()))
                .stockItems(updateStockItems(dto.getStockItems()))
                .build();
    }

    private List<StockItem> updateStockItems(List<StockItemDto> itemsDto) {
        return itemsDto
                .stream()
                .filter(it -> Optional.ofNullable(it).isPresent())
                .map(this::updateStockItem)
                .collect(Collectors.toList());
    }

    private StockItem updateStockItem(StockItemDto dto) {
        return StockItem.builder()
                .name(dto.getName())
                .code(dto.getCode())
                .price(dto.getPrice())
                .amount(dto.getAmount())
                .build();
    }
}
