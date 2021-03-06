package com.meme.ala.domain.alacard.service;

import com.meme.ala.domain.alacard.model.dto.response.AlaCardDto;
import com.meme.ala.domain.alacard.model.dto.response.BackgroundDto;
import com.meme.ala.domain.alacard.model.dto.response.BackgroundDtoInSetting;
import com.meme.ala.domain.alacard.model.entity.AlaCard;
import com.meme.ala.domain.alacard.model.entity.SentenceWord;
import com.meme.ala.domain.alacard.model.entity.cardSetting.Background;
import com.meme.ala.domain.member.model.entity.Member;

import java.util.List;
import java.util.Map;

public interface AlaCardService {
    void save(AlaCard alaCard);

    List<AlaCardDto> getAlaCardDtoList(Member member);

    SentenceWord toSentence(AlaCard alaCard, Member member);

    List<String> getBackgroundImageUrls() throws Exception;

    void saveBackground(BackgroundDto backgroundDto);

    List<Background> getBackgrounds();

    Map<String, List<BackgroundDtoInSetting>> getBackgroundThumbCategory();

    List<AlaCard> getAlaCardList();
}
