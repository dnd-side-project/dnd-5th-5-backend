package com.meme.ala.domain.member.model.mapper;

import com.meme.ala.domain.member.model.dto.MemberPrincipalDto;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.model.entity.MemberSetting;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MemberMapper {
    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    @Mapping(target = "nickname", source = "memberSetting.nickname")
    @Mapping(target = "statusMessage", source = "memberSetting.statusMessage")
    @Mapping(target = "imgUrl", source = "memberSetting.imgUrl")
    @Mapping(target = "isOpen", source = "memberSetting.isOpen")
    MemberPrincipalDto toPrincipalDto(Member member);

    void updateMemberSettingFromDto(MemberPrincipalDto dto, @MappingTarget MemberSetting memberSetting);
}