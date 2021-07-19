package com.meme.ala.domain.alacard.model.mapper;

import com.meme.ala.domain.alacard.model.dto.request.AlaCardSaveDto;
import com.meme.ala.domain.alacard.model.entity.AlaCard;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AlaCardSaveMapper {
    AlaCardSaveMapper INSTANCE = Mappers.getMapper(AlaCardSaveMapper.class);
    @Mapping(target = "id", ignore = true)
    AlaCard toEntity(AlaCardSaveDto alaCardSaveDto);
}
