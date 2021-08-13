package com.meme.ala.domain.alacard.model.mapper;

import com.meme.ala.domain.alacard.model.dto.response.AlaCardSettingDto;
import com.meme.ala.domain.alacard.model.entity.cardSetting.AlaCardSetting;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AlaCardSettingMapper {
    AlaCardSettingMapper INSTANCE = Mappers.getMapper(AlaCardSettingMapper.class);

    @Mapping(target = "backgroundImgUrl", source = "background.imgUrl")
    @Mapping(target = "fontColor", source = "background.fontColor")
    AlaCardSettingDto toDto(AlaCardSetting alaCardSetting);

    @Mapping(target = "background.imgUrl", source = "backgroundImgUrl")
    @Mapping(target = "background.fontColor", source = "fontColor")
    AlaCardSetting toEntity(AlaCardSettingDto alaCardSettingDto);

    @InheritConfiguration
    void updateAlaCardSettingFromDto(AlaCardSettingDto alaCardSettingDto, @MappingTarget AlaCardSetting alaCardSetting);
}