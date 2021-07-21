package com.meme.ala.domain.alacard.model.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MiddleCategoryDto {
    private String middleCategoryName;

    private String hint;

    private SentenceComponentDto sentenceComponent;

    private String prefix;

    private String form;

    private List<WordDto> wordList;
}
