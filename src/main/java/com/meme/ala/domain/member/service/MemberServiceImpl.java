package com.meme.ala.domain.member.service;

import com.meme.ala.core.auth.jwt.JwtTokenProvider;
import com.meme.ala.core.auth.oauth.GoogleUser;
import com.meme.ala.core.auth.oauth.NaverUser;
import com.meme.ala.core.auth.oauth.OAuthProvider;
import com.meme.ala.core.auth.oauth.OAuthUserInfo;
import com.meme.ala.core.error.ErrorCode;
import com.meme.ala.core.error.exception.BusinessException;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.model.entity.MemberSetting;
import com.meme.ala.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional(readOnly = true)
    public String loginOrJoin(Map<String, Object> data, String provider) {
        OAuthUserInfo authUserInfo;
        if(provider.equals(OAuthProvider.GOOGLE)){
            authUserInfo = new GoogleUser((Map<String, Object>)data.get("profileObj"));
        }else if(provider.equals(OAuthProvider.NAVER)){
            authUserInfo = new NaverUser((Map<String, Object>)data);
        }else {
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        Optional<Member> optionalMember =
                memberRepository.findByEmail(authUserInfo.getEmail());
        if(!optionalMember.isPresent()){
            join(authUserInfo,provider);
        }
        return jwtTokenProvider.createToken(authUserInfo.getEmail());
    }

    @Override
    @Transactional
    public void join(OAuthUserInfo authUserInfo, String provider) {
        Member newMember = Member.builder()
                .email(authUserInfo.getEmail())
                .memberSetting(
                        MemberSetting.builder()
                                .nickname("alamonster_" + memberRepository.count())
                                .build())
                .build();
        if (provider.equals(OAuthProvider.GOOGLE)) {
            newMember.setGoogleId(authUserInfo.getProviderId());
        } else if (provider.equals(OAuthProvider.NAVER)) {
            newMember.setNaverId(authUserInfo.getProviderId());
        } else {
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        memberRepository.save(newMember);
    }
}
