package com.meme.ala.domain.alacard.model.entity.cardSetting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AlaCardSetting {
    @Id
    private ObjectId id;

    private Background background;

    @Builder.Default
    private Boolean isOpen = true;
}
