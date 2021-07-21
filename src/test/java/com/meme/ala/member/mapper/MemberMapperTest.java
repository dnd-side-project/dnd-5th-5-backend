package com.meme.ala.member.mapper;

import com.meme.ala.domain.member.model.dto.MemberPrincipalDto;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.model.mapper.MemberMapper;
import com.meme.ala.member.entity.MemberCreator;
import com.meme.ala.member.entity.MemberPrincipalDtoCreator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberMapperTest {
    @Autowired
    private MemberMapper memberMapper;
    private final Member member= MemberCreator.testMember;
    private final MemberPrincipalDto dto= MemberPrincipalDtoCreator.testMemberPrincipalDto;
    @Test
    void 엔티티에서_DTO변환_테스트() {
        MemberPrincipalDto mappedDto = memberMapper.toDto(member);
        assertEquals(mappedDto.getEmail(), dto.getEmail());
        assertEquals(mappedDto.getImgUrl(), dto.getImgUrl());
        assertEquals(mappedDto.getNickname(), dto.getNickname());
        assertEquals(mappedDto.getStatusMessage(), dto.getStatusMessage());
    }
}
