package com.meme.ala.domain.alacard.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
public class MiddleCategory {
    private String middleCategoryName;

    private String hint;

    private SentenceComponent sentenceComponent;

    private String prefix;

    private String form;

    @Builder.Default
    private List<Word> wordList=new LinkedList<>();
}
