package com.meme.ala.domain.alacard.model.entity;

import com.meme.ala.domain.aggregation.model.entity.WordCount;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class SentenceWord {
    private String sentence;
    List<WordCount> wordCountList;
    Boolean isCompleted;
}