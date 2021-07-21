package com.meme.ala.member.entity;

import com.meme.ala.domain.member.model.dto.MemberPrincipalDto;

public class MemberPrincipalDtoCreator {
    public static MemberPrincipalDto testMemberPrincipalDto=MemberPrincipalDto.builder()
            .email("test@gmail.com")
            .nickname("testNickname")
            .statusMessage("너 자신을 ala")
            .imgUrl("https://user-images.githubusercontent.com/46064193/126342816-399b6cfa-869c-485f-b676-a303af41f2ec.png")
            .isOpen(true)
            .build();
}
