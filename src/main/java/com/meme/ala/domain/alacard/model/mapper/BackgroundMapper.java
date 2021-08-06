package com.meme.ala.domain.alacard.model.mapper;

import com.meme.ala.domain.alacard.model.dto.response.BackgroundDto;
import com.meme.ala.domain.alacard.model.entity.cardSetting.Background;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BackgroundMapper {
    @Mapping(target = "imgUrl", source = "backgroundImgUrl")
    Background toEntity(BackgroundDto backgroundDto);
}
