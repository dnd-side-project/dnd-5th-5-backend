package com.meme.ala.domain.alacard.service;

import com.meme.ala.core.error.ErrorCode;
import com.meme.ala.core.error.exception.EntityNotFoundException;
import com.meme.ala.domain.aggregation.model.entity.Aggregation;
import com.meme.ala.domain.aggregation.model.entity.WordCount;
import com.meme.ala.domain.aggregation.service.AggregationService;
import com.meme.ala.domain.alacard.model.dto.response.AlaCardDto;
import com.meme.ala.domain.alacard.model.dto.response.SelectionWordDto;
import com.meme.ala.domain.alacard.model.entity.AlaCard;
import com.meme.ala.domain.alacard.model.entity.MiddleCategory;
import com.meme.ala.domain.alacard.model.entity.SentenceWord;
import com.meme.ala.domain.alacard.model.mapper.AlaCardMapper;
import com.meme.ala.domain.alacard.model.mapper.AlaCardSaveMapper;
import com.meme.ala.domain.alacard.repository.AlaCardRepository;
import com.meme.ala.domain.member.model.entity.AlaCardSettingPair;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.model.entity.cardSetting.AlaCardSetting;
import com.meme.ala.domain.member.model.entity.cardSetting.Background;
import com.meme.ala.domain.member.model.entity.cardSetting.Font;
import com.meme.ala.domain.member.service.MemberCardService;
import com.meme.ala.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class AlaCardServiceImpl implements AlaCardService {
    private final AlaCardRepository alaCardRepository;
    private final MemberService memberService;
    private final MemberCardService memberCardService;
    private final AggregationService aggregationService;
    private final AlaCardMapper alaCardMapper;
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

    @Override
    @Transactional
    public void submitWordList(Member member, Aggregation aggregation, List<SelectionWordDto> wordDtoList) {
        Map<String, List<String>> dtoMap = dtoListToMapByMiddleCategory(wordDtoList);
        for (Map.Entry<String, List<String>> entry : dtoMap.entrySet()) {
            String middleCategory = entry.getKey();
            List<String> wordNameList = entry.getValue();
            List<WordCount> aggregationList = aggregation.getWordCountList();
            for (int i = 0; i < aggregation.getWordCountList().size(); i++) {
                if (aggregationList.get(i).getMiddleCategoryName().equals(middleCategory) &&
                        wordNameList.contains(aggregationList.get(i).getWord().getWordName())) {
                    aggregationList.get(i).setCount(aggregationList.get(i).getCount() + 1);
                }
            }
        }
        aggregationService.save(aggregation);
    }

    private Map<String, List<String>> dtoListToMapByMiddleCategory(List<SelectionWordDto> wordDtoList) {
        Map<String, List<String>> dtoMap = new HashMap();
        for (SelectionWordDto dto : wordDtoList) {
            String middleCategory = dto.getMiddleCategory();
            if (dtoMap.containsKey(middleCategory)) {
                List<String> dtoList = dtoMap.get(middleCategory);
                dtoList.add(dto.getWordName());
            } else {
                List<String> dtoList = Stream.of(dto).map(SelectionWordDto::getWordName).collect(Collectors.toList());
                dtoMap.put(middleCategory, dtoList);
            }
        }
        return dtoMap;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlaCardDto> getAlaCardDtoList(Member member) {
        List<AlaCardSettingPair> alaCardSettingPairList = member.getAlaCardSettingPairList();
        return alaCardSettingPairList.stream()
                .map(alaCardSettingPair -> alaCardMapper.toDto(
                        alaCardSettingPair.getAlaCard(),
                        AlaCardSetting.builder()
                                .background(Background.builder()
                                        .fontColor("#ffffff")
                                        .imgUrl("https://user-images.githubusercontent.com/46064193/127431607-cd319904-0147-4ccc-8880-373ca25f2d8a.png")
                                        .build())
                                .font(Font.builder()
                                        .fontName("noto sans")
                                        .build())
                                .build(),
                        this.toSentence(alaCardSettingPair.getAlaCard(), member).getSentence(),
                        this.toSentence(alaCardSettingPair.getAlaCard(), member).getWordCountList()
                )).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public SentenceWord toSentence(AlaCard alaCard, Member member) {
        List<WordCount> wordCountResultList = new ArrayList<>();
        Aggregation aggregation = aggregationService.findByMember(member);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < alaCard.getMiddleCategoryList().size(); i++) {
            MiddleCategory middleCategory = alaCard.getMiddleCategoryList().get(i);
            List<WordCount> wordCountList = toSortedWordCountList(aggregation, middleCategory.getMiddleCategoryName());
            WordCount selectedWordCount = wordCountList.get(0);
            wordCountResultList.add(selectedWordCount);
            stringBuilder.append(toMiddleSentence(middleCategory,
                    member.getMemberSetting().getNickname(),
                    selectedWordCount.getWord().getWordName(),
                    i, alaCard.getMiddleCategoryList().size()));
        }
        return SentenceWord.builder()
                .sentence(stringBuilder.toString())
                .wordCountList(wordCountResultList)
                .build();
    }

    private List<WordCount> toSortedWordCountList(Aggregation aggregation, String middleCategoryName) {
        return aggregation.getWordCountList().stream()
                .filter(wordCount -> wordCount.getMiddleCategoryName().equals(middleCategoryName))
                .sorted(Comparator.comparing(WordCount::getCount).reversed())
                .collect(Collectors.toList());
    }

    private String toMiddleSentence(MiddleCategory middleCategory, String nickName, String wordName, int idx, int size) {
        String formPrefix = middleCategory.getPrefix()
                .replace("???", nickName);
        String formSentence = middleCategory.getForm()
                .replace("???", wordName);
        if (idx == 0) {
            return formPrefix + formSentence + middleCategory.getSentenceComponent().getJosa();
        } else if (idx == size - 1) {
            return formSentence + middleCategory.getSentenceComponent().getEomi();
        } else {
            return formSentence + middleCategory.getSentenceComponent().getJosa();
        }
    }
}
