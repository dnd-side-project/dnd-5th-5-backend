package com.meme.ala.domain.alacard.service;

import com.meme.ala.core.error.ErrorCode;
import com.meme.ala.core.error.exception.EntityNotFoundException;
import com.meme.ala.domain.alacard.model.dto.response.SelectionWordDto;
import com.meme.ala.domain.alacard.model.entity.AlaCard;
import com.meme.ala.domain.alacard.model.mapper.AlaCardSaveMapper;
import com.meme.ala.domain.alacard.repository.AlaCardRepository;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.service.MemberCardService;
import com.meme.ala.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AlaCardServiceImpl implements AlaCardService {
    private final AlaCardRepository alaCardRepository;
    private final MemberService memberService;
    private final MemberCardService memberCardService;
    private final AlaCardSaveMapper alaCardSaveMapper;
    @Value("${alacard.maxwords}")
    private int maxWords;

    @Transactional
    @Override
    public void save(AlaCard alaCard) {
        alaCardRepository.save(alaCard);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SelectionWordDto> getWordList(String nickname, Boolean shuffle) {
        Member member = memberService.findByNickname(nickname).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        List<AlaCard> alaCardList = memberCardService.getAlaCardListFromMember(member);
        if (shuffle) {
            Collections.shuffle(alaCardList);
        }
        List<SelectionWordDto> wordDtoList = alaCardSaveMapper.alaCardListToSelectionWordDtoList(alaCardList);
        return new ArrayList<>(wordDtoList.subList(0, Math.min(maxWords, wordDtoList.size())));
    }
}
