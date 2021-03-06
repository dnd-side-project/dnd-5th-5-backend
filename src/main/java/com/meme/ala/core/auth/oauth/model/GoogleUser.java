package com.meme.ala.core.auth.oauth.model;

import java.util.Map;

public class GoogleUser implements OAuthUserInfo {
    private Map<String, Object> attribute;

    public GoogleUser(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getProviderId() {
        return "g_" + attribute.get("googleId");
    }

    @Override
    public String getProvider() {
        return OAuthProvider.GOOGLE;
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
