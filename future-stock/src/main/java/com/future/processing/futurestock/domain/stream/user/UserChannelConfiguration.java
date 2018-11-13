package com.future.processing.futurestock.domain.stream.user;

import com.future.processing.futurestock.domain.stream.user.channel.UserChannel;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"development", "production"})
@EnableBinding(UserChannel.class)
class UserChannelConfiguration {
}
