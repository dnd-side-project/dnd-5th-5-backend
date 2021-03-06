package com.meme.ala.domain.alacard.model.mapper;

import com.meme.ala.domain.alacard.model.dto.request.AlaCardSaveDto;
import com.meme.ala.domain.alacard.model.dto.response.SelectionWordDto;
import com.meme.ala.domain.alacard.model.entity.AlaCard;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AlaCardSaveMapper {
    AlaCardSaveMapper INSTANCE = Mappers.getMapper(AlaCardSaveMapper.class);

    @Mapping(target = "id", ignore = true)
    AlaCard toEntity(AlaCardSaveDto alaCardSaveDto);

    default List<SelectionWordDto> alaCardListToSelectionWordDtoList(List<AlaCard> alaCardList) {
        return alaCardList.stream()
                .map(this::alaCardToSelectionWordDtoList)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    default List<SelectionWordDto> alaCardToSelectionWordDtoList(AlaCard alaCard) {
        return alaCard.getMiddleCategoryList()
                .stream()
                .map(middleCategory -> middleCategory
                        .getWordList()
                        .stream()
                        .map(word -> SelectionWordDto.
                                builder()
                                .id(Base64.getEncoder()
                                        .encodeToString((
                                                alaCard.getBigCategory() + "-" +
                                                        middleCategory.getMiddleCategoryName() + "-" +
                                                        middleCategory.getHint() + "-" +
                                                        word.getWordName())
                                                .getBytes()))
                                .hint(middleCategory.getHint())
                                .wordName(word.getWordName())
                                .build()
                        )
                        .collect(Collectors.toList())
                )
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
