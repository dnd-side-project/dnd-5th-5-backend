package com.meme.ala.domain.member.model.mapper;

import com.meme.ala.domain.member.model.dto.MemberFriendDto;
import com.meme.ala.domain.member.model.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = { MemberMapper.class })
public interface MemberFriendMapper {

    @Mapping(target = "nickname", source = "memberSetting.nickname")
    @Mapping(target = "statusMessage", source = "memberSetting.statusMessage")
    @Mapping(target = "imgUrl", source = "memberSetting.imgUrl")
    MemberFriendDto toMemberFriendDtoFromMember(Member friend);
}
