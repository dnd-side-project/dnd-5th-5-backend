package com.meme.ala.core.auth.oauth.model;

import java.util.Map;

public class NaverUser implements OAuthUserInfo {
    private Map<String, Object> attribute;

    public NaverUser(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getProviderId() {
        return (String) attribute.get("id");
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
        //TODO: 2021.7.16. DEFAULT 이미지(코알라) 세팅
        return null;
    }
}
