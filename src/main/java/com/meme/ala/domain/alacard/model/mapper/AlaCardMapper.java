package com.meme.ala.domain.alacard.model.mapper;

import com.meme.ala.domain.aggregation.model.entity.WordCount;
import com.meme.ala.domain.aggregation.model.mapper.WordCountMapper;
import com.meme.ala.domain.alacard.model.dto.response.AlaCardDto;
import com.meme.ala.domain.alacard.model.entity.AlaCard;
import com.meme.ala.domain.alacard.model.entity.cardSetting.AlaCardSetting;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AlaCardMapper {
    AlaCardMapper INSTANCE = Mappers.getMapper(AlaCardMapper.class);

    default AlaCardDto toDto(AlaCard alaCard, AlaCardSetting alaCardSetting, String sentence, List<WordCount> selectedWordList, Boolean isCompleted) {
        if(!isCompleted)
            alaCardSetting.getBackground().setFontColor("#B9FF46");
        AlaCardDto alaCardDto=AlaCardDto.builder()
                .alaCardSettingDto(AlaCardSettingMapper.INSTANCE.toDto(alaCardSetting))
                .selectedWordList(selectedWordList.stream()
                        .map(WordCountMapper.INSTANCE::toDto)
                        .collect(Collectors.toList()))
                .isCompleted(isCompleted)
                .sentence(sentence)
                .build();
        alaCardDto.getAlaCardSettingDto().setAlaCardId(alaCard.getId());
        return alaCardDto;
    }
}