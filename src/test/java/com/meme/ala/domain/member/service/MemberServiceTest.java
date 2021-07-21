package com.meme.ala.domain.member.service;

import com.meme.ala.common.EntityFactory;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberServiceTest {
    @MockBean
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;

    @Test
    public void MemberService_중복_체크_테스트() throws Exception{
        Member testMember= EntityFactory.testMember();

        given(memberRepository
                .existsMemberByMemberSettingNickname(testMember.getMemberSetting().getNickname()))
                .willReturn(true);

        assertThat(memberService.existsNickname(testMember.getMemberSetting().getNickname())).isTrue();
    }
}