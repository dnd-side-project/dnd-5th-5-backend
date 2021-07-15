package com.meme.ala.domain.member.service;

import com.meme.ala.core.auth.oauth.GoogleUser;
import com.meme.ala.core.auth.oauth.OAuthUserInfo;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{
    @Autowired
    private final MemberRepository memberRepository;

    @Override
    public String loginOrJoin(Map<String, Object> data) {
        OAuthUserInfo googleUser =
                new GoogleUser((Map<String, Object>)data.get("profileObj"));
        Optional<Member> optionalMember =
                memberRepository.findByEmail(googleUser.getEmail());
        if(optionalMember.isPresent()){
            // TODO: 2021.7.15. 성공 시 JWT 토큰 생성 및 반환하는 기능 추가 - jongmin
            return "dummy token";
        }
        else {
            Member newMember=Member.builder()
                    .email(googleUser.getEmail())
                    .name(googleUser.getName())
                    .googleId(googleUser.getProviderId())
                    .imgUrl(null) // TODO: 2021.7.15. DEFAULT 이미지 Assign
                    .build();
            memberRepository.save(newMember);
            // TODO: 2021.7.15. 성공 시 JWT 토큰 생성 및 반환하는 기능 추가 - jongmin
            return "dummy token";
        }
    }
}
