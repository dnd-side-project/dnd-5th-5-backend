package com.meme.ala.domain.alacard.service;

import com.meme.ala.domain.aggregation.model.entity.Aggregation;
import com.meme.ala.domain.alacard.model.dto.response.AlaCardDto;
import com.meme.ala.domain.alacard.model.dto.response.AlaCardSettingDto;
import com.meme.ala.domain.alacard.model.dto.response.BackgroundDto;
import com.meme.ala.domain.alacard.model.dto.response.SelectionWordDto;
import com.meme.ala.domain.alacard.model.entity.AlaCard;
import com.meme.ala.domain.alacard.model.entity.SentenceWord;
import com.meme.ala.domain.alacard.model.entity.cardSetting.AlaCardSetting;
import com.meme.ala.domain.alacard.model.entity.cardSetting.Background;
import com.meme.ala.domain.member.model.entity.Member;

import java.util.List;

public interface AlaCardService {
    void save(AlaCard alaCard);

    List<AlaCardDto> getAlaCardDtoList(Member member);

    SentenceWord toSentence(AlaCard alaCard, Member member);

    List<String> getBackgroundImageUrls() throws Exception;

    void saveSetting(BackgroundDto backgroundDto);

    List<Background> getBackgrounds();
}
