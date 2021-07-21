package com.meme.ala.alacard.entity;

import com.meme.ala.domain.alacard.model.entity.AlaCard;
import com.meme.ala.domain.alacard.model.entity.MiddleCategory;
import com.meme.ala.domain.alacard.model.entity.SentenceComponent;
import com.meme.ala.domain.alacard.model.entity.Word;

public class AlaCardCreator {
    public static AlaCard testAlaCard=AlaCard.builder()
            .bigCategory("test")
        .middleCategoryItem(MiddleCategory.builder()
                .middleCategoryName("testMiddle")
                .hint("testHint")
                .prefix("???는 ")
                .form("???하는 것을 좋아")
                .sentenceComponent(SentenceComponent.builder().eomi("한다").josa("하고, ").build())
            .wordItem(Word.builder().wordName("공부").build())
            .wordItem(Word.builder().wordName("수영").build())
            .wordItem(Word.builder().wordName("테스트").build()).build()
        ).build();
}
