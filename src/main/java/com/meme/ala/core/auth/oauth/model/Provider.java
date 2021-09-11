package com.meme.ala.core.auth.oauth.model;

import com.meme.ala.core.error.ErrorCode;
import com.meme.ala.core.error.exception.BusinessException;

import java.util.Arrays;
import java.util.Optional;

public enum Provider {
    KAKAO("k"), GOOGLE("g"), NAVER("n");

    private String initial;

    Provider(String initial) {
        this.initial = initial;
    }

    public String getInitial(){
        return initial;
    }

    public static Provider getProvider(String initial){
        Optional<Provider> provider = Arrays.stream(Provider.values())
                                            .filter(el -> el.getInitial().equals(initial))
                                            .findFirst();

        if(!provider.isPresent())
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);

        return provider.get();
    }
}
