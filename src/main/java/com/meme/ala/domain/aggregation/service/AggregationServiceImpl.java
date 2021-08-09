package com.meme.ala.domain.aggregation.service;

import com.meme.ala.core.annotation.PublishEvent;
import com.meme.ala.core.error.ErrorCode;
import com.meme.ala.core.error.exception.BusinessException;
import com.meme.ala.domain.aggregation.model.entity.Aggregation;
import com.meme.ala.domain.aggregation.model.entity.UserCount;
import com.meme.ala.domain.aggregation.model.entity.WordCount;
import com.meme.ala.domain.aggregation.repository.AggregationRepository;
import com.meme.ala.domain.aggregation.repository.UserCountRepository;
import com.meme.ala.domain.alacard.model.entity.MiddleCategory;
import com.meme.ala.domain.member.model.entity.AlaCardSettingPair;
import com.meme.ala.domain.member.model.entity.Member;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AggregationServiceImpl implements AggregationService {
    private final AggregationRepository aggregationRepository;
    private final UserCountRepository userCountRepository;

    @Override
    @Transactional(readOnly = true)
    public Aggregation findByMember(Member member) {
        return aggregationRepository.findByMemberId(member.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));
    }

    @Override
    @Transactional
    public void initAggregation(Member member) {
        List<WordCount> wordCountList = member.getAlaCardSettingPairList().stream()
                .map(this::toNestedWordCountList)
                .flatMap(Collection::stream)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        Aggregation aggregation = Aggregation.builder().memberId(member.getId()).wordCountList(wordCountList).build();
        aggregationRepository.save(aggregation);
    }

    @Override
    @Transactional
    public void save(Aggregation aggregation) {
        aggregationRepository.save(aggregation);
    }

    private List<List<WordCount>> toNestedWordCountList(AlaCardSettingPair alaCardSettingPair) {
        return alaCardSettingPair.getAlaCard().getMiddleCategoryList().stream()
                .map(middleCategory -> toWordCountList(middleCategory, alaCardSettingPair.getAlaCard().getId()))
                .collect(Collectors.toList());
    }

    private List<WordCount> toWordCountList(MiddleCategory middleCategory, ObjectId cardId) {
        return middleCategory.getWordList().stream()
                .map(word -> WordCount.builder()
                        .count(0)
                        .middleCategoryName(middleCategory.getMiddleCategoryName())
                        .word(word)
                        .cardId(cardId)
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @PublishEvent
    @Transactional
    public void submitWordList(Member member, Aggregation aggregation, List<String> wordIdList) throws UnsupportedEncodingException {
        Map<String, List<String>> dtoMap = dtoListToMapByMiddleCategory(wordIdList);
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
        aggregationRepository.save(aggregation);
    }

    @Override
    public Integer getUserCount() {
        UserCount userCount = userCountRepository.findAll().get(0);
        return userCount.getCount();
    }

    private Map<String, List<String>> dtoListToMapByMiddleCategory(List<String> wordIdList) throws UnsupportedEncodingException {
        Map<String, List<String>> wordMap = new HashMap();
        for (String id : wordIdList) {
            byte[] decodedIdBytes = Base64.getDecoder().decode(id.getBytes());
            String decodedId = new String(decodedIdBytes, "UTF-8");
            String[] tokens = decodedId.split("-");
            String middleCategory = tokens[1];
            String wordName = tokens[3];
            if (wordMap.containsKey(middleCategory)) {
                List<String> wordList = wordMap.get(middleCategory);
                wordList.add(wordName);
            } else {
                List<String> wordList = Arrays.asList(wordName);
                wordMap.put(middleCategory, wordList);
            }
        }
        return wordMap;
    }
}