package com.meme.ala.core.auth.jwt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class JwtTokenProviderTest {
    @Autowired
    private JwtProvider jwtTokenProvider;

    @Test
    public void Jwt_Create_테스트(){
        String providerId="1234654";
        String token=jwtTokenProvider.createToken(providerId);
        assertThat(jwtTokenProvider.getUserProviderId(token),is(providerId));
        assertTrue(jwtTokenProvider.validateToken(token));
    }
}