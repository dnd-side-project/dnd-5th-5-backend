package com.meme.ala.domain.alacard.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BackgroundDto {
    private String backgroundImgUrl;
    private String fontColor;
    private String category;
}
