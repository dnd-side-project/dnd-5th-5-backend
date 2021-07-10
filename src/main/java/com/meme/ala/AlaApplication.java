package com.meme.ala;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class AlaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlaApplication.class, args);
    }

    @GetMapping("/ping")
    public ResponseEntity<?> ping(){
        return ResponseEntity.ok("pong");
    }

}
