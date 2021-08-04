package com.meme.ala.common.utils;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import java.util.Random;

@Component
@AllArgsConstructor
public class CookieGenerator {

    public Cookie generate(String cookieId){
        Cookie cookie = new Cookie("id", cookieId);

        cookie.setMaxAge(10 * 60);

        return cookie;
    }

    public String randomString(){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit,rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }
}
