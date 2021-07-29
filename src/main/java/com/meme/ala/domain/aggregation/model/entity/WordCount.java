package com.meme.ala.domain.aggregation.model.entity;

import com.meme.ala.domain.alacard.model.entity.Word;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;

@Getter
@Builder
@AllArgsConstructor
public class WordCount {
    private ObjectId cardId;

    private String middleCategoryName;

    private Word word;

    @Builder.Default
    private int count = 0;
}
