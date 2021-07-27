package com.meme.ala.domain.member.service;

import com.meme.ala.domain.alacard.model.entity.AlaCard;
import com.meme.ala.domain.alacard.repository.AlaCardRepository;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.model.entity.cardSetting.AlaCardSetting;
import com.meme.ala.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberCardServiceImpl implements MemberCardService {
    private final MemberRepository memberRepository;
    private final AlaCardRepository alaCardRepository;

    @Override
    @Transactional
    public void assignCard(Member member, int num) {
        Set<AlaCard> memberAlaCardSet = member.getAlaCardAlaCardSettingMap().keySet();
        List<AlaCard> alaCardList = getAlaCardList();
        Collections.shuffle(alaCardList);
        List<AlaCard> selectedAlaCardList =
                alaCardList.stream()
                        .filter(alaCard -> !memberAlaCardSet.contains(alaCard)).limit(num)
                        .collect(Collectors.toList());
        //TODO: 2021.07.27 Default AlaCardSetting에 대한 로직 추가해야 함 - 미리 정해진 데이터셋에서 랜덤으로 뽑기
        AlaCardSetting defaultSetting = AlaCardSetting.builder().build();
        Map<AlaCard, AlaCardSetting> alaCardAlaCardSettingMap = new HashMap<>();
        selectedAlaCardList.forEach(alaCard -> alaCardAlaCardSettingMap.put(alaCard, defaultSetting));
        member.getAlaCardAlaCardSettingMap().putAll(alaCardAlaCardSettingMap);
        memberRepository.save(member);
    }

    @Cacheable
    @Transactional(readOnly = true)
    public List<AlaCard> getAlaCardList() {
        return alaCardRepository.findAll();
    }
}
