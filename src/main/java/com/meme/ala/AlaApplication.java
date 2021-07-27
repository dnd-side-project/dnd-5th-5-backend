package com.meme.ala;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class AlaApplication {
    public static void main(String[] args) {
        SpringApplication.run(AlaApplication.class, args);
    }
}