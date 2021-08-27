package com.meme.ala.core.auth.oauth.model;

import java.util.Map;

public class NaverUser implements OAuthUserInfo {
    private Map<String, Object> attribute;

    public NaverUser(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getProviderId() {
        return "n_" + attribute.get("id");
    }

    @Override
    public String getProvider() {
        return "naver";
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
