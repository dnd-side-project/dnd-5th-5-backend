package com.meme.ala.domain.member.model.entity.cardSetting;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@NoArgsConstructor
@Document("BACKGROUND")
public class Background {
    @Id
    private ObjectId id;

    private String imgUrl;

    private String fontColor;
}
