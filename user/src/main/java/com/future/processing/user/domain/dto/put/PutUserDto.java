package com.future.processing.user.domain.dto.put;

import com.future.processing.user.domain.dto.post.PostWalletDto;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.validation.Valid;

import java.io.Serializable;


@Value
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class PutUserDto implements Serializable {

    private Long id;

    private String username;

    private String password;

    @Valid
    private PutUserDataDto userData;

    @Valid
    private PostWalletDto wallet;
}
