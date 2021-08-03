package com.meme.ala;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAsync
@EnableCaching
@SpringBootApplication
public class AlaApplication {
    public static void main(String[] args) {
        SpringApplication.run(AlaApplication.class, args);
    }
}