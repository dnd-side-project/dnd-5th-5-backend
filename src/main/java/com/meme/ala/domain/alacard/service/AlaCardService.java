package com.meme.ala.domain.alacard.service;

import com.meme.ala.domain.alacard.model.dto.response.SelectionWordDto;
import com.meme.ala.domain.alacard.model.entity.AlaCard;

import java.util.List;

public interface AlaCardService {
    public void save(AlaCard alaCard);
    public List<SelectionWordDto> getWordList(String nickname, Boolean shuffle);
}
