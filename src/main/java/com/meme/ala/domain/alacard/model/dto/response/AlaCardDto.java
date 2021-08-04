package com.meme.ala.domain.alacard.model.dto.response;

import com.meme.ala.domain.aggregation.model.dto.response.WordCountDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AlaCardDto {
    private String sentence;
    private Boolean isCompleted;
    private List<WordCountDto> selectedWordList;
    private AlaCardSettingDto alaCardSettingDto;
}