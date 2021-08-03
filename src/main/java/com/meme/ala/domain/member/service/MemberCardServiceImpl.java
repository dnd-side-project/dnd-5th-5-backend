package com.meme.ala.domain.member.service;

import com.meme.ala.core.error.ErrorCode;
import com.meme.ala.core.error.exception.EntityNotFoundException;
import com.meme.ala.domain.alacard.model.dto.response.SelectionWordDto;
import com.meme.ala.domain.alacard.model.entity.AlaCard;
import com.meme.ala.domain.alacard.model.entity.cardSetting.AlaCardSetting;
import com.meme.ala.domain.alacard.model.mapper.AlaCardSaveMapper;
import com.meme.ala.domain.alacard.repository.AlaCardRepository;
import com.meme.ala.domain.alacard.service.AlaCardService;
import com.meme.ala.domain.member.model.entity.AlaCardSettingPair;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberCardServiceImpl implements MemberCardService {
    private final MemberRepository memberRepository;
    private final AlaCardRepository alaCardRepository;
    private final MemberService memberService;
    private final AlaCardService alaCardService;
    private final AlaCardSaveMapper alaCardSaveMapper;

    @Value("${alacard.maxwords}")
    private int maxWords;

    @Override
    @Transactional
    public void assignCard(Member member, int num) {
        List<AlaCard> memberAlaCardList = getAlaCardListFromMember(member);
        List<AlaCard> alaCardList = getAlaCardList();
        List<AlaCardSetting> alaCardSettingList = alaCardService.getAlaCardSettings();
        Collections.shuffle(alaCardList);
        Collections.shuffle(alaCardSettingList);
        List<AlaCard> selectedAlaCardList =
                alaCardList.stream()
                        .filter(alaCard -> !memberAlaCardList.contains(alaCard)).limit(num)
                        .collect(Collectors.toList());
        for (int i = 0; i < num; i++) {
            member.getAlaCardSettingPairList()
                    .add(AlaCardSettingPair.builder()
                            .alaCard(selectedAlaCardList.get(i))
                            .alaCardSetting(alaCardSettingList.get(i % alaCardSettingList.size()))
                            .build());
            memberRepository.save(member);
        }
    }

    @Cacheable
    @Transactional(readOnly = true)
    public List<AlaCard> getAlaCardList() {
        return alaCardRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<AlaCard> getAlaCardListFromMember(Member member) {
        return member.getAlaCardSettingPairList()
                .stream().map(AlaCardSettingPair::getAlaCard)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SelectionWordDto> getWordList(String nickname, Boolean shuffle) {
        Member member = memberService.findByNickname(nickname).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        List<AlaCard> alaCardList = getAlaCardListFromMember(member);
        if (shuffle) {
            Collections.shuffle(alaCardList);
        }
        List<SelectionWordDto> wordDtoList = alaCardSaveMapper.alaCardListToSelectionWordDtoList(alaCardList);
        return new ArrayList<>(wordDtoList.subList(0, Math.min(maxWords, wordDtoList.size())));
    }
}
