package com.meme.ala.domain.alacard.model.entity.cardSetting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@AllArgsConstructor
@Document("ALACARDSETTING")
public class AlaCardSetting {
    @Id
    private ObjectId id;

    @DBRef
    private Background background;

    @DBRef
    private Font font;

    @Builder.Default
    private Boolean isOpen = true;
}
