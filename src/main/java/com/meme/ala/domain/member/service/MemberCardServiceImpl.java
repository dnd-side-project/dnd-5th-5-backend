package com.meme.ala.domain.member.service;

import com.meme.ala.domain.aggregation.model.entity.Aggregation;
import com.meme.ala.domain.aggregation.model.entity.WordCount;
import com.meme.ala.domain.aggregation.service.AggregationService;
import com.meme.ala.domain.alacard.model.dto.response.AlaCardDto;
import com.meme.ala.domain.alacard.model.entity.AlaCard;
import com.meme.ala.domain.alacard.model.entity.MiddleCategory;
import com.meme.ala.domain.alacard.model.entity.SentenceWord;
import com.meme.ala.domain.alacard.model.mapper.AlaCardMapper;
import com.meme.ala.domain.alacard.repository.AlaCardRepository;
import com.meme.ala.domain.member.model.entity.AlaCardSettingPair;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.model.entity.cardSetting.AlaCardSetting;
import com.meme.ala.domain.member.model.entity.cardSetting.Background;
import com.meme.ala.domain.member.model.entity.cardSetting.Font;
import com.meme.ala.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberCardServiceImpl implements MemberCardService {
    private final MemberRepository memberRepository;
    private final AlaCardRepository alaCardRepository;
    private final AggregationService aggregationService;
    private final AlaCardMapper alaCardMapper;

    @Override
    @Transactional
    public void assignCard(Member member, int num) {
        List<AlaCard> memberAlaCardList = getAlaCardListFromMember(member);
        List<AlaCard> alaCardList = getAlaCardList();
        Collections.shuffle(alaCardList);
        List<AlaCard> selectedAlaCardList =
                alaCardList.stream()
                        .filter(alaCard -> !memberAlaCardList.contains(alaCard)).limit(num)
                        .collect(Collectors.toList());
        //TODO: 2021.07.27 Default AlaCardSetting에 대한 로직 추가해야 함 - 미리 정해진 데이터셋에서 랜덤으로 뽑기
        selectedAlaCardList.forEach(alaCard -> member.getAlaCardSettingPairList()
                .add(AlaCardSettingPair.builder()
                        .alaCard(alaCard)
                        .alaCardSetting(AlaCardSetting.builder().build())
                        .build()));
        memberRepository.save(member);
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
}
