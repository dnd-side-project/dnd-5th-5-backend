package com.meme.ala.domain.member.model.entity;

import com.meme.ala.domain.alacard.model.entity.AlaCard;
import com.meme.ala.domain.alacard.model.entity.cardSetting.AlaCardSetting;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AlaCardSettingPair {
    @DBRef
    private AlaCard alaCard;
    private AlaCardSetting alaCardSetting;
}
