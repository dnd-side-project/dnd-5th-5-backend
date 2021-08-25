package com.meme.ala.domain.member.service;

import com.meme.ala.core.annotation.PublishEvent;
import com.meme.ala.core.auth.oauth.model.OAuthProvider;
import com.meme.ala.core.auth.oauth.model.OAuthUserInfo;
import com.meme.ala.core.error.ErrorCode;
import com.meme.ala.core.error.exception.BusinessException;
import com.meme.ala.core.error.exception.EntityNotFoundException;
import com.meme.ala.domain.member.model.dto.MemberPrincipalDto;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.model.entity.MemberSetting;
import com.meme.ala.domain.member.model.mapper.MemberMapper;
import com.meme.ala.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    @Value("${frontdomain}")
    private String frontUrl;
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Override
    @Transactional(readOnly = true)
    public boolean existsEmail(String email) {
        return memberRepository.existsMemberByEmailAndMemberSetting_IsDeleted(email, false);
    }

    @Override
    @PublishEvent
    @Transactional
    public String join(OAuthUserInfo authUserInfo, String provider) {
        int newNumber = 0;
        if (memberRepository.count() != 0) {
            Member lastMember = memberRepository.findTop1ByMemberSettingNicknameRegexOrderByCreatedAtDesc("ala_[0-9]+");
            String lastNumber = lastMember.getMemberSetting().getNickname().split("_")[1];
            newNumber = Integer.parseInt(lastNumber) + 1;
        }
        Member newMember = Member.builder()
                .email(authUserInfo.getEmail())
                .memberSetting(
                        MemberSetting.builder()
                                .nickname("ala_" + newNumber)
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
        return authUserInfo.getEmail();
    }

    @Override
    @Transactional
    public void updateMember(Member newMember, MemberPrincipalDto memberPrincipalDto) {
        memberMapper.updateMemberSettingFromDto(memberPrincipalDto, newMember.getMemberSetting());
        memberRepository.save(newMember);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsNickname(String nickname) {
        return memberRepository.existsMemberByMemberSettingNicknameAndMemberSetting_IsDeleted(nickname, false);
    }

    @Override
    @Transactional(readOnly = true)
    public Member findByNickname(String nickname) {
        return memberRepository.findByMemberSettingNicknameAndMemberSetting_IsDeleted(nickname, false).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
    }

    @Override
    public Member findByMemberId(ObjectId memberId) {
        return memberRepository.findByIdAndMemberSetting_IsDeleted(memberId, false).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
    }

    @Override
    @Transactional
    public void deleteMemberByNickname(String nickname) {
        memberRepository.deleteMemberByMemberSettingNickname(nickname);
    }

    @Override
    @Transactional(readOnly = true)
    public String shareSelectLink(String nickname) {
        return frontUrl + "select/" + nickname;
    }

    @Override
    public String shareMyPageLink(String nickname) {
        return frontUrl + "mypage/" + nickname;
    }
}
