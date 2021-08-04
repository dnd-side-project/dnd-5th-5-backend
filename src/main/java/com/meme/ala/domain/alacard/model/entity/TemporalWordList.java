package com.meme.ala.domain.alacard.model.entity;

import com.meme.ala.domain.alacard.model.dto.response.SelectionWordDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@Document(collection = "TEMPORAL_WORD_LIST")
public class TemporalWordList {
    @Id
    private ObjectId id;

    private String cookieId;

    private List<SelectionWordDto> wordDtoList;

    @Builder.Default
    @Indexed(expireAfterSeconds = 600)
    private Date expireAt = new Date();
}
