package com.meme.ala.domain.aggregation.model.entity;

import com.meme.ala.domain.alacard.model.entity.Word;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Getter
@Builder
@AllArgsConstructor
public class WordCount {
    private ObjectId cardId;

    private String middleCategoryName;

    @DBRef(lazy = true)
    private Word word;

    @Builder.Default
    private int count = 0;
}
