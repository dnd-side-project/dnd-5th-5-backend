package com.meme.ala.domain.alacard.model.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AlaCardSaveDto {
    private String bigCategory;
    private List<MiddleCategoryDto> middleCategoryList;
}
