package com.meme.ala.domain.aggregation.component;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class AlacardTokenizer {
    private String[] getTokens(String wordId) {
        byte[] decodedIdBytes = Base64.getDecoder().decode(wordId.getBytes());
        String decodedId = new String(decodedIdBytes, StandardCharsets.UTF_8);
        String[] tokens = decodedId.split("-");
        return tokens;
    }

    public String getMiddleCategory(String wordId){
        return getTokens(wordId)[1];
    }

    public String getWordName(String wordId){
        return getTokens(wordId)[3];
    }
}
