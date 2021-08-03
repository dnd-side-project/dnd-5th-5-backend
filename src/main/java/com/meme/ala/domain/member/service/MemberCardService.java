package com.meme.ala.domain.member.service;

import com.meme.ala.domain.alacard.model.dto.response.SelectionWordDto;
import com.meme.ala.domain.alacard.model.entity.AlaCard;
import com.meme.ala.domain.member.model.entity.Member;

import java.util.List;

public interface MemberCardService {
    public List<AlaCard> getAlaCardList();

    public List<AlaCard> getAlaCardListFromMember(Member member);

    List<SelectionWordDto> getWordList(String nickname, Boolean shuffle);

    public void assignCard(Member member, int num);
}