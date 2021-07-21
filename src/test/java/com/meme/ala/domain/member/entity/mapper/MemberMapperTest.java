package com.meme.ala.domain.member.entity.mapper;

import com.meme.ala.common.DtoFactory;
import com.meme.ala.common.EntityFactory;
import com.meme.ala.domain.member.model.dto.MemberPrincipalDto;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.model.mapper.MemberMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberMapperTest {

    @Autowired
    private MemberMapper memberMapper;

    private final Member member = EntityFactory.testMember();
    private final MemberPrincipalDto dto = DtoFactory.testMemberPrincipalDto();

    @Test
    void 엔티티에서_DTO변환_테스트() {
        MemberPrincipalDto mappedDto = memberMapper.toPrincipalDto(member);
        assertEquals(mappedDto.getEmail(), dto.getEmail());
        assertEquals(mappedDto.getImgUrl(), dto.getImgUrl());
        assertEquals(mappedDto.getNickname(), dto.getNickname());
        assertEquals(mappedDto.getStatusMessage(), dto.getStatusMessage());
    }

    @Test
    void DTO로부터_엔티티_업데이트_테스트(){
        MemberPrincipalDto updateDto = MemberPrincipalDto.builder().nickname("test").build();
        memberMapper.updateMemberSettingFromDto(updateDto, member.getMemberSetting());
        assertEquals(member.getMemberSetting().getNickname(), updateDto.getNickname());

        MemberPrincipalDto updateDto2 = MemberPrincipalDto.builder().nickname("test").statusMessage("update status message").build();
        memberMapper.updateMemberSettingFromDto(updateDto2, member.getMemberSetting());
        assertEquals(member.getMemberSetting().getNickname(), updateDto2.getNickname());
        assertEquals(member.getMemberSetting().getStatusMessage(), updateDto2.getStatusMessage());
    }

}
