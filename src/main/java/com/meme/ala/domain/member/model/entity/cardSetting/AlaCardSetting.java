package com.meme.ala.domain.member.model.entity.cardSetting;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Getter
@Builder
@NoArgsConstructor
public class AlaCardSetting {
    @DBRef
    private Background background;

    @DBRef
    private Font font;

    @Builder.Default
    private Boolean isOpen=true;
}
