package com.future.processing.user

import com.future.processing.user.domain.dto.post.PostUserDataDto
import com.future.processing.user.domain.dto.post.PostUserDto
import com.future.processing.user.domain.dto.post.PostWalletDto

trait SampleUsersDtos implements SampleStockItems {

    PostUserDataDto postUserDataDto = new PostUserDataDto("McRay", "Bertie", "bertie@wp.pl", "512232123");
    PostWalletDto postWalletDto = new PostWalletDto(1l, "5000", Arrays.asList(SampleStockItems.coin, SampleStockItems.fp))
    PostUserDto bertie = createPostUserDto(1l, "bertie", "bertie", postUserDataDto, postWalletDto)

    static private createPostUserDto(Long id, String username, String password, PostUserDataDto userData, PostWalletDto wallet) {
        return PostUserDto.builder()
                .id(id)
                .userData(userData)
                .password(password)
                .username(username)
                .wallet(wallet)
                .build()

    }
}