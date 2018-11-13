package com.future.processing.user.domain.dto.post;

import com.future.processing.user.domain.constraint.UniqueUsername;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

@Value
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class PostUserDto implements Serializable {

    @Null
    private Long id;

    @NotNull
    @UniqueUsername
    private String username;

    @NotNull
    private String password;

    @Valid
    private PostUserDataDto userData;

    @Valid
    private PostWalletDto wallet;
}
