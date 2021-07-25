package com.meme.ala.core.auth.oauth;

public interface OAuthUserInfo {
    String getProviderId();

    String getProvider();

    String getEmail();

    String getName();

    String getImgUrl();
}
