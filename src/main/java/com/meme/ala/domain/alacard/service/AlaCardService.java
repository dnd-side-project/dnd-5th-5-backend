package com.meme.ala.domain.alacard.service;

import com.meme.ala.domain.aggregation.model.entity.Aggregation;
import com.meme.ala.domain.alacard.model.dto.response.AlaCardDto;
import com.meme.ala.domain.alacard.model.dto.response.AlaCardSettingDto;
import com.meme.ala.domain.alacard.model.dto.response.SelectionWordDto;
import com.meme.ala.domain.alacard.model.entity.AlaCard;
import com.meme.ala.domain.alacard.model.entity.SentenceWord;
import com.meme.ala.domain.alacard.model.entity.cardSetting.AlaCardSetting;
import com.meme.ala.domain.member.model.entity.Member;

import java.util.List;

public interface AlaCardService {
    void save(AlaCard alaCard);

    public List<AlaCardDto> getAlaCardDtoList(Member member);

    public SentenceWord toSentence(AlaCard alaCard, Member member);

    List<String> getBackgroundImageUrls() throws Exception;

    public void saveSetting(AlaCardSettingDto alaCardSettingDto);

    List<AlaCardSetting> getAlaCardSettings();
}
