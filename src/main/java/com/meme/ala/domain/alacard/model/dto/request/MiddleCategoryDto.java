package com.meme.ala.domain.alacard.model.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class MiddleCategoryDto {
    private String middleCategoryName;

    private String hint;

    private SentenceComponentDto sentenceComponent;

    private String prefix;

    private String form;

    private List<WordDto> wordList;
}
