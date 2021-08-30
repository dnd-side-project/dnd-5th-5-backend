package com.meme.ala.core.auth.oauth.model;

import java.util.Map;

public class KakaoUser implements OAuthUserInfo {
    private Map<String, Object> attribute;

    public KakaoUser(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getProviderId() {
        return "k_" + attribute.get("kakaoId");
    }

    @Override
    public String getProvider() {
        return OAuthProvider.KAKAO;
    }

    @Override
    public String getEmail() {
        return (String) attribute.get("email");
    }

    @Override
    public String getName() {
        return (String) attribute.get("name");
    }

    @Override
    public String getImgUrl() {
        return "https://meme-ala-background.s3.ap-northeast-2.amazonaws.com/img/defaultprofile.svg";
    }
}
