package com.meme.ala.domain.alacard.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AlaCardSettingDto {
    private String backgroundColor;
    private String fontColor;
    private String font;
    private Boolean isOpen;
}