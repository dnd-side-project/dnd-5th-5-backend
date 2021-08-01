package com.meme.ala.domain.alacard.service;

import com.meme.ala.domain.aggregation.model.entity.Aggregation;
import com.meme.ala.domain.alacard.model.dto.response.AlaCardDto;
import com.meme.ala.domain.alacard.model.dto.response.SelectionWordDto;
import com.meme.ala.domain.alacard.model.entity.AlaCard;
import com.meme.ala.domain.alacard.model.entity.SentenceWord;
import com.meme.ala.domain.member.model.entity.Member;

import java.util.List;

public interface AlaCardService {
    void save(AlaCard alaCard);

    List<SelectionWordDto> getWordList(String nickname, Boolean shuffle);

    void submitWordList(Member member, Aggregation aggregation, List<SelectionWordDto> wordDtoList);

    public List<AlaCardDto> getAlaCardDtoList(Member member);

    public SentenceWord toSentence(AlaCard alaCard, Member member);
}
