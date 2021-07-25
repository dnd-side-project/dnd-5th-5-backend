package com.meme.ala.domain.alacard.model.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.List;

@Getter
@Setter
@Builder
public class AlaCardSaveDto {
    private String bigCategory;

    @Singular("middleCategoryItem")
    private List<MiddleCategoryDto> middleCategoryList;
}
