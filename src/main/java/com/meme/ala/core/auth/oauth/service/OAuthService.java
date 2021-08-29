package com.meme.ala.core.auth.oauth.service;

import com.meme.ala.core.auth.oauth.model.*;
import com.meme.ala.core.error.ErrorCode;
import com.meme.ala.core.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class OAuthService {

    public OAuthUserInfo getMemberByProvider(Map<String, Object> data, String provider) {
        OAuthUserInfo authUserInfo;
        switch (provider) {
            case OAuthProvider.GOOGLE:
                authUserInfo = new GoogleUser((Map<String, Object>) data.get("profileObj"));
                break;
            case OAuthProvider.KAKAO:
                authUserInfo = new KakaoUser((Map<String, Object>) data.get("profileObj"));
                break;
            case OAuthProvider.NAVER:
                authUserInfo = new NaverUser(data);
                break;
            default:
                throw new BusinessException(ErrorCode.METHOD_NOT_ALLOWED);
        }
        return authUserInfo;
    }
}
