package com.meme.ala.domain.alacard.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class MiddleCategory {
    private String middleCategoryName;

    private String hint;

    private SentenceComponent sentenceComponent;

    private String prefix;

    private String form;

    @Singular("wordItem")
    private List<Word> wordList;
}
