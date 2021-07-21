package com.meme.ala.domain.member.service;

import com.meme.ala.core.auth.oauth.OAuthUserInfo;
import com.meme.ala.domain.member.model.entity.Member;

import java.util.Map;

public interface MemberService {
    public Map<String,String> loginOrJoin(Map<String, Object> data, String provider);
    public void join(OAuthUserInfo authUserInfo, String provider);
    public void updateMember(Member newMember);
}
