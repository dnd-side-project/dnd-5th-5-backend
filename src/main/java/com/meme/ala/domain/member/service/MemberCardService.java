package com.meme.ala.domain.member.service;

import com.meme.ala.domain.alacard.model.dto.response.AlaCardDto;
import com.meme.ala.domain.alacard.model.entity.AlaCard;
import com.meme.ala.domain.alacard.model.entity.SentenceWord;
import com.meme.ala.domain.member.model.entity.Member;

import java.util.List;

public interface MemberCardService {
    public void assignCard(Member member, int num);

    public List<AlaCard> getAlaCardList();

    public List<AlaCard> getAlaCardListFromMember(Member member);

    public List<AlaCardDto> getAlaCardDtoList(Member member);

    public SentenceWord toSentence(AlaCard alaCard, Member member);
}