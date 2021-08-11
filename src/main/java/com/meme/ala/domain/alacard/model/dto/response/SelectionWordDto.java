package com.meme.ala.domain.alacard.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SelectionWordDto {
    private String id;
    private String hint;
    private String wordName;
}
