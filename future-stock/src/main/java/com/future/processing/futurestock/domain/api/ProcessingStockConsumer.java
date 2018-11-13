package com.future.processing.futurestock.domain.api;

import com.future.processing.futurestock.domain.dto.PostStockDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class ProcessingStockConsumer implements Consumer<PostStockDto> {

    private final String URL = "http://webtask.future-processing.com:8068/stocks";

    @Override
    public PostStockDto consume() {

        RestTemplate template = new RestTemplate();

        PostStockDto stock = template.getForObject(URL, PostStockDto.class);

        log.info("Consumed data from url - " + URL);

        return stock;
    }
}
