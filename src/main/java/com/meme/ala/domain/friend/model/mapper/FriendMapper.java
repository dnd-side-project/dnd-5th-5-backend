package com.meme.ala.domain.friend.model.mapper;

import com.meme.ala.domain.friend.model.dto.FriendDto;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.model.mapper.MemberMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = { MemberMapper.class })
public interface FriendMapper {

    @Mapping(target = "nickname", source = "memberSetting.nickname")
    @Mapping(target = "statusMessage", source = "memberSetting.statusMessage")
    @Mapping(target = "imgUrl", source = "memberSetting.imgUrl")
    FriendDto toMemberFriendDtoFromMember(Member friend);
}
