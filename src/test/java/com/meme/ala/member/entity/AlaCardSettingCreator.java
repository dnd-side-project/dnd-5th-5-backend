package com.meme.ala.member.entity;

import com.meme.ala.domain.member.model.entity.cardSetting.AlaCardSetting;
import com.meme.ala.domain.member.model.entity.cardSetting.Background;
import com.meme.ala.domain.member.model.entity.cardSetting.Font;

public class AlaCardSettingCreator {
    public static AlaCardSetting testAlaCardSetting=AlaCardSetting.builder()
            .background(Background.builder()
                    .fontColor("FFFFFF")
                    .imgUrl("http://test")
                    .build()
            )
            .font(Font.builder().font("sans").build())
            .isOpen(true)
            .build();
}
