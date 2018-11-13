package com.future.processing.futurestock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableAsync
@EnableScheduling
public class FutureStockApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(FutureStockApplication.class)
                .profiles("development")
                .run(args);
    }
}
