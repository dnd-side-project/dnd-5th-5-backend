package com.meme.ala.domain.aggregation.model.mapper;

import com.meme.ala.domain.aggregation.model.dto.response.WordCountDto;
import com.meme.ala.domain.aggregation.model.entity.WordCount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WordCountMapper {
    WordCountMapper INSTANCE = Mappers.getMapper(WordCountMapper.class);

    @Mapping(target = "wordName", source = "word.wordName")
    WordCountDto toDto(WordCount wordCount);
}