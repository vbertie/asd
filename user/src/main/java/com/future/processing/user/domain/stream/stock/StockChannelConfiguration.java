package com.future.processing.user.domain.stream.stock;


import com.future.processing.user.domain.stream.stock.channel.StockChannel;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Profile({"development", "production"})
@EnableBinding(StockChannel.class)
public class StockChannelConfiguration {
}
