package com.meme.ala.domain.alacard.model.mapper;

import com.meme.ala.domain.alacard.model.dto.response.AlaCardSettingDto;
import com.meme.ala.domain.member.model.entity.cardSetting.AlaCardSetting;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AlaCardSettingMapper {
    AlaCardSettingMapper INSTANCE = Mappers.getMapper(AlaCardSettingMapper.class);

    @Mapping(target = "backgroundColor", source = "background.backgroundColor")
    @Mapping(target = "fontColor", source = "background.fontColor")
    @Mapping(target = "font", source = "font.fontName")
    AlaCardSettingDto toDto(AlaCardSetting alaCardSetting);
}