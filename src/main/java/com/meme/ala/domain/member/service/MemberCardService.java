package com.meme.ala.domain.member.service;

import com.meme.ala.domain.alacard.model.dto.response.SelectionWordDto;
import com.meme.ala.domain.alacard.model.entity.AlaCard;
import com.meme.ala.domain.member.model.entity.Member;

import java.util.List;

public interface MemberCardService {
    List<AlaCard> getAlaCardList();

    List<AlaCard> getAlaCardListFromMember(Member member);

    void assignCard(Member member, int num);

    void setTemporalWordList(String cookieId, String nickname, Boolean shuffle);

    List<SelectionWordDto> getWordList(String cookieId);
}