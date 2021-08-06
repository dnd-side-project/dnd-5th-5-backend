package com.meme.ala.domain.alacard.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AlaCardSettingDto {
    private String alaCardId;
    private String backgroundImgUrl;
    private String fontColor;
    private String category;
    private Boolean isOpen;
}