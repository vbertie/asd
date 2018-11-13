package com.future.processing.user.domain;

import com.future.processing.user.domain.dto.post.PostUserDto;
import com.future.processing.user.domain.dto.put.PutUserDto;
import com.future.processing.user.domain.dto.stream.SaleDto;
import com.future.processing.user.domain.dto.UserDto;
import com.future.processing.user.domain.dto.stream.PaymentDto;
import com.future.processing.user.domain.model.User;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UserFacade implements UserDetailsService {

    private final UserRepository users;
    private final WalletRepository wallets;
    private final SuperConverter<PostUserDto, User,PutUserDto> converter;
    private final PaymentManager paymentManager;
    private final SaleManager saleManager;

    public void createUser(PostUserDto user) {

        users.save(converter.convert(user));

        log.info("Saved user with id to database.");
    }

    public UserDto showUser(Long id) {
        return users.findOptionalById(id)
                .orElseThrow(IllegalArgumentException::new);
    }

    public UserDto findByUsername(String username) {
        return users.findOptionalByUsername(username)
                .orElseThrow(IllegalAccessError::new);
    }

    public UserDto findById(Long id) {
        return users.findOptionalById(id)
                .orElseThrow(IllegalAccessError::new);
    }

    public User findUserById(Long id) {
        return users.findById(id)
                .orElseThrow(IllegalAccessError::new);
    }

    @Transactional
    public void processPayment(PaymentDto paymentDto) {

        User user = users.findById(paymentDto.getUserId())
                .orElseThrow(IllegalAccessError::new);

        User processedUser = paymentManager.processPayment(user, paymentDto);

        users.save(processedUser);

        log.info("Saved processed user with id {} ", processedUser.getId());
    }

    public void processSale(SaleDto dto) {

        User user = users.findById(dto.getUserId())
                .orElseThrow(IllegalAccessError::new);

        User processedUser = saleManager.process(dto, user);

        saleManager.processUserSaleToStock(dto);

        users.save(processedUser);

        log.info("Saved processed user with id {} ", processedUser.getId());
    }

    public UserDto updateUser(PutUserDto dto) {

        User user = users.findById(dto.getId())
                .orElseThrow(IllegalAccessError::new);

        User updatedUser = converter.updateExisting(dto, user);

        users.save(updatedUser);

        return users.findOptionalById(updatedUser.getId())
                .orElseThrow(IllegalAccessError::new);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return users.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found."));
    }

    @Transactional
    public boolean isUsernameInUse(String value) {
        return users.findByUsername(value).isPresent();
    }

    @Transactional
    public boolean isEmailInUse(String value) {
        return users.findByUserDataEmail(value).isPresent();
    }
}
