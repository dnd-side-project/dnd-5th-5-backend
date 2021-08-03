package com.meme.ala.core.auth.oauth.service;

import com.meme.ala.core.auth.oauth.model.GoogleUser;
import com.meme.ala.core.auth.oauth.model.NaverUser;
import com.meme.ala.core.auth.oauth.model.OAuthProvider;
import com.meme.ala.core.auth.oauth.model.OAuthUserInfo;
import com.meme.ala.core.error.ErrorCode;
import com.meme.ala.core.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class OAuthService {

    public OAuthUserInfo getMemberByProvider(Map<String, Object> data, String provider){
        OAuthUserInfo authUserInfo;
        if (provider.equals(OAuthProvider.GOOGLE)) {
            authUserInfo = new GoogleUser((Map<String, Object>) data.get("profileObj"));
        } else if (provider.equals(OAuthProvider.NAVER)) {
            authUserInfo = new NaverUser(data);
        } else {
            throw new BusinessException(ErrorCode.METHOD_NOT_ALLOWED);
        }

        return authUserInfo;
    }
}
