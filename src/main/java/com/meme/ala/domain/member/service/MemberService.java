package com.meme.ala.domain.member.service;

import com.meme.ala.core.auth.oauth.model.OAuthUserInfo;
import com.meme.ala.domain.member.model.dto.JwtVO;
import com.meme.ala.domain.member.model.dto.MemberPrincipalDto;
import com.meme.ala.domain.member.model.entity.Member;
import org.bson.types.ObjectId;

public interface MemberService {
    boolean existsEmail(String email);

    String join(OAuthUserInfo authUserInfo, String provider);

    void updateMember(Member newMember, MemberPrincipalDto memberPrincipalDto);

    boolean existsNickname(String nickname);

    Member findByNickname(String nickname);

    Member findByMemberId(ObjectId memberId);

    Member deleteMember(Member member);

    String shareSelectLink(String nickname);

    String shareMyPageLink(String nickname);
}
