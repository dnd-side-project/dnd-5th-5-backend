package com.meme.ala.domain.member.service;

import com.meme.ala.core.auth.oauth.OAuthUserInfo;
import com.meme.ala.domain.member.model.dto.MemberPrincipalDto;
import com.meme.ala.domain.member.model.entity.Member;

import java.util.Map;
import java.util.Optional;

public interface MemberService {
    Map<String, String> loginOrJoin(Map<String, Object> data, String provider);

    void join(OAuthUserInfo authUserInfo, String provider);

    void updateMember(Member newMember, MemberPrincipalDto memberPrincipalDto);

    boolean existsNickname(String nickname);

    Optional<Member> findByNickname(String nickname);

    void deleteMemberByNickname(String nickname);

    String shareSelectLink(String nickname);
}
