package com.meme.ala.domain.aggregation.service;

import com.meme.ala.core.annotation.PublishEvent;
import com.meme.ala.core.annotation.QuestCheck;
import com.meme.ala.core.error.ErrorCode;
import com.meme.ala.core.error.exception.BusinessException;
import com.meme.ala.core.error.exception.EntityNotFoundException;
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
    @QuestCheck
    @PublishEvent
    @Transactional
    public void submitWordList(Member member, Aggregation aggregation, List<String> wordIdList) throws UnsupportedEncodingException {
        Map<String, LinkedList<String>> dtoMap = dtoListToMapByMiddleCategory(wordIdList);
        for (Map.Entry<String, LinkedList<String>> entry : dtoMap.entrySet()) {
            applyToAggregation(entry, aggregation);
        }
        aggregationRepository.save(aggregation);
    }

    private void applyToAggregation(Map.Entry<String, LinkedList<String>> submitWordEntry, Aggregation aggregation) {
        List<WordCount> aggregationList = aggregation.getWordCountList();
        String middleCategory = submitWordEntry.getKey();
        List<String> wordNameList = submitWordEntry.getValue();
        for (int i = 0; i < aggregation.getWordCountList().size(); i++) {
            if (aggregationList.get(i).getMiddleCategoryName().equals(middleCategory)) {
                if (wordNameList.contains(aggregationList.get(i).getWord().getWordName())) {
                    aggregationList.get(i).setCount(aggregationList.get(i).getCount() + 1);
                } else {
                    WordCount wordCount = WordCount.builder()
                            .count(0)
                            .word(aggregationList.get(i).getWord())
                            .cardId(aggregationList.get(i).getCardId())
                            .middleCategoryName(middleCategory)
                            .build();
                    aggregation.getWordCountList().add(wordCount);
                    aggregationRepository.save(aggregation);
                }
            }
        }
    }

    @Override
    public Integer getUserCount() {
        UserCount userCount = userCountRepository.findAll().get(0);
        return userCount.getCount();
    }

    private Map<String, LinkedList<String>> dtoListToMapByMiddleCategory(List<String> wordIdList) throws UnsupportedEncodingException {
        Map<String, LinkedList<String>> wordMap = new HashMap();
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
                LinkedList<String> wordList = new LinkedList<>(Arrays.asList(wordName));
                wordMap.put(middleCategory, wordList);
            }
        }
        return wordMap;
    }

    @Override
    @Transactional
    public void addAggregation(Member member) {
        List<AlaCardSettingPair> pair = member.getAlaCardSettingPairList();

        List<WordCount> wordCountList = toNestedWordCountList(pair.get(pair.size() - 1))
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        Aggregation aggregation = aggregationRepository.findByMemberId(member.getId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        aggregation.getWordCountList().addAll(wordCountList);

        aggregationRepository.save(aggregation);
    }
}