package com.meme.ala.domain.member.entity.mapper;

import com.meme.ala.common.DtoFactory;
import com.meme.ala.common.EntityFactory;
import com.meme.ala.domain.member.model.dto.MemberFriendDto;
import com.meme.ala.domain.member.model.dto.MemberPrincipalDto;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.model.mapper.MemberFriendMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MemberFriendMapperTest {

    @Autowired
    private MemberFriendMapper memberFriendMapper;

    private final Member member = EntityFactory.testMember();
    private final MemberFriendDto memberFriendDto = DtoFactory.testMemberFriendDto();

    @Test
    void 엔티티에서_DTO변환_테스트() {
        MemberFriendDto mappedDto = memberFriendMapper.toMemberFriendDtoFromMember(member);
        assertEquals(mappedDto.getImgUrl(), memberFriendDto.getImgUrl());
        assertEquals(mappedDto.getNickname(), memberFriendDto.getNickname());
        assertEquals(mappedDto.getStatusMessage(), memberFriendDto.getStatusMessage());
    }

}
