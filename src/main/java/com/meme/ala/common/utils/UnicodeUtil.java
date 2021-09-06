package com.meme.ala.common.utils;

import org.springframework.stereotype.Component;

@Component
public class UnicodeUtil {
    public static Boolean isWithBatchim(String word) {
        char lastName = word.charAt(word.length() - 1);
        if (lastName < 0xAC00 || lastName > 0xD7A3)
            return true;
        return (lastName - 0xAC00) % 28 > 0;
    }
}
