package com.meme.ala.domain.member.service;

import com.meme.ala.core.annotation.PublishEvent;
import com.meme.ala.core.auth.oauth.model.OAuthUserInfo;
import com.meme.ala.core.error.ErrorCode;
import com.meme.ala.core.error.exception.EntityNotFoundException;
import com.meme.ala.domain.member.model.dto.MemberPrincipalDto;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.model.entity.MemberSetting;
import com.meme.ala.domain.member.model.mapper.MemberMapper;
import com.meme.ala.domain.member.repository.MemberRepository;
import com.mongodb.DuplicateKeyException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    @Value("${frontdomain}")
    private String frontUrl;
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Override
    @Transactional(readOnly = true)
    public boolean existsProviderId(String providerId) {
        return memberRepository.existsMemberByProviderIdAndMemberSetting_IsDeleted(providerId, false);
    }

    @Override
    @PublishEvent
    @Transactional
    public String join(OAuthUserInfo authUserInfo, String provider, String nickname) {
        int newNumber = 0;
        if (memberRepository.count() != 0) {
            try {
                Member lastMember = memberRepository.findTop1ByMemberSettingNicknameRegexOrderByCreatedAtDesc("ala_[0-9]+$");
                String lastNumber = lastMember.getMemberSetting().getNickname().split("_")[1];
                newNumber = Integer.parseInt(lastNumber) + 1;
            } catch (Exception e) {
                newNumber = 0;
            }
        }
        Member newMember = Member.builder()
                .email(authUserInfo.getEmail())
                .providerId(authUserInfo.getProviderId())
                .memberSetting(
                        MemberSetting.builder()
                                .nickname("ala_" + newNumber)
                                .build())
                .build();
        while (true) {
            try {
                memberRepository.save(newMember);
                return authUserInfo.getProviderId();
            } catch (DuplicateKeyException e) {
                Random ran = new Random();
                newMember.getMemberSetting().setNickname("ala_" + newNumber + "_" + ran.nextInt() % 1000);
            }
        }
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

    @PublishEvent
    @Override
    @Transactional
    public Member deleteMember(Member member) {
        member.getMemberSetting().setIsDeleted(true);
        member.getMemberSetting().setNickname(UUID.randomUUID().toString());
        memberRepository.save(member);

        return member;
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
