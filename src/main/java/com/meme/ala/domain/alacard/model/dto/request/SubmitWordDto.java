package com.meme.ala.domain.alacard.model.dto.request;

import com.meme.ala.domain.alacard.model.dto.response.SelectionWordDto;
import lombok.Getter;

import java.util.List;

@Getter
public class SubmitWordDto {
    List<SelectionWordDto> words;
}