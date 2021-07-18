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
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void Jwt_Create_테스트(){
        String email="test@gmail.com";
        String token=jwtTokenProvider.createToken(email);
        assertThat(jwtTokenProvider.getUserEmail(token),is(email));
        assertTrue(jwtTokenProvider.validateToken(token));
    }
}