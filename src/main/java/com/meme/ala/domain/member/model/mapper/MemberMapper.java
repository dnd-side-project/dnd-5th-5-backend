package com.meme.ala.domain.member.model.mapper;

import com.meme.ala.domain.member.model.dto.MemberDto;
import com.meme.ala.domain.member.model.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    // Member toEntity(MemberDto memberDto);

    // MemberDto toDto(Member member);
}
