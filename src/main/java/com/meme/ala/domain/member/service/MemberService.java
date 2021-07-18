package com.meme.ala.domain.member.service;

import com.meme.ala.core.auth.oauth.OAuthUserInfo;

import java.util.Map;

public interface MemberService {
    public String loginOrJoin(Map<String, Object> data, String provider);
    public void join(OAuthUserInfo authUserInfo, String provider);
    public boolean test();
}
