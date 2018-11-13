package com.future.processing.user.domain.dto;

import com.future.processing.user.domain.model.User.UserData;
import com.future.processing.user.domain.model.Wallet;

public interface UserDto {

    long getId();
    UserData getUserData();
    Wallet getWallet();
    String getUsername();
}
