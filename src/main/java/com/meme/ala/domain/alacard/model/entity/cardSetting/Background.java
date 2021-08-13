package com.meme.ala.domain.alacard.model.entity.cardSetting;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("BACKGROUND")
public class Background {
    @Id
    private ObjectId id;

    private String imgUrl;

    private String category;

    private String fontColor;
}