package com.meme.ala.domain.aggregation.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class WordCountDto {
    public String wordName;
    public int count;
}