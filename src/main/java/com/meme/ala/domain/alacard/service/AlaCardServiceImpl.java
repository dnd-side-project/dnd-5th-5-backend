package com.meme.ala.domain.alacard.service;

import com.meme.ala.common.utils.AmazonS3ImageUtil;
import com.meme.ala.domain.aggregation.model.entity.Aggregation;
import com.meme.ala.domain.aggregation.model.entity.WordCount;
import com.meme.ala.domain.aggregation.service.AggregationService;
import com.meme.ala.domain.alacard.model.dto.response.AlaCardDto;
import com.meme.ala.domain.alacard.model.dto.response.AlaCardSettingDto;
import com.meme.ala.domain.alacard.model.entity.AlaCard;
import com.meme.ala.domain.alacard.model.entity.MiddleCategory;
import com.meme.ala.domain.alacard.model.entity.SentenceWord;
import com.meme.ala.domain.alacard.model.mapper.AlaCardMapper;
import com.meme.ala.domain.alacard.model.mapper.AlaCardSettingMapper;
import com.meme.ala.domain.alacard.repository.AlaCardRepository;
import com.meme.ala.domain.alacard.repository.AlaCardSettingRepository;
import com.meme.ala.domain.alacard.repository.BackgroundRepository;
import com.meme.ala.domain.alacard.repository.FontRepository;
import com.meme.ala.domain.member.model.entity.AlaCardSettingPair;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.alacard.model.entity.cardSetting.AlaCardSetting;
import com.meme.ala.domain.alacard.model.entity.cardSetting.Background;
import com.meme.ala.domain.alacard.model.entity.cardSetting.Font;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AlaCardServiceImpl implements AlaCardService {
    private final AlaCardRepository alaCardRepository;
    private final AlaCardSettingRepository alaCardSettingRepository;
    private final BackgroundRepository backgroundRepository;
    private final FontRepository fontRepository;
    private final AggregationService aggregationService;
    private final AmazonS3ImageUtil amazonS3ImageUtil;
    private final AlaCardMapper alaCardMapper;
    private final AlaCardSettingMapper alaCardSettingMapper;

    @Transactional
    @Override
    public void save(AlaCard alaCard) {
        alaCardRepository.save(alaCard);
    }


    @Override
    @Transactional(readOnly = true)
    public List<AlaCardDto> getAlaCardDtoList(Member member) {
        List<AlaCardSettingPair> alaCardSettingPairList = member.getAlaCardSettingPairList();
        return alaCardSettingPairList.stream()
                .map(alaCardSettingPair -> alaCardMapper.toDto(
                        alaCardSettingPair.getAlaCard(),
                        alaCardSettingPair.getAlaCardSetting(),
                        toSentence(alaCardSettingPair.getAlaCard(), member).getSentence(),
                        toSentence(alaCardSettingPair.getAlaCard(), member).getWordCountList()
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
                    selectedWordCount.getWord().getWordName(),
                    i, alaCard.getMiddleCategoryList().size()));
        }
        return SentenceWord.builder()
                .sentence(stringBuilder.toString())
                .wordCountList(wordCountResultList)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getBackgroundImageUrls() throws Exception {
        return amazonS3ImageUtil.getObjectItemUrls();
    }

    @Override
    @Transactional
    public void saveSetting(AlaCardSettingDto alaCardSettingDto) {
        AlaCardSetting alaCardSetting = alaCardSettingMapper.toEntity(alaCardSettingDto);
        fontRepository.save(alaCardSetting.getFont());
        backgroundRepository.save(alaCardSetting.getBackground());
        alaCardSettingRepository.save(alaCardSetting);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlaCardSetting> getAlaCardSettings() {
        return alaCardSettingRepository.findAll();
    }

    private List<WordCount> toSortedWordCountList(Aggregation aggregation, String middleCategoryName) {
        return aggregation.getWordCountList().stream()
                .filter(wordCount -> wordCount.getMiddleCategoryName().equals(middleCategoryName))
                .sorted(Comparator.comparing(WordCount::getCount).reversed())
                .collect(Collectors.toList());
    }

    private String toMiddleSentence(MiddleCategory middleCategory, String wordName, int idx, int size) {
        String formSentence = middleCategory.getForm()
                .replace("???", wordName);
        if (idx == size - 1) {
            return formSentence + middleCategory.getSentenceComponent().getEomi();
        } else {
            return formSentence + middleCategory.getSentenceComponent().getJosa();
        }
    }
}
