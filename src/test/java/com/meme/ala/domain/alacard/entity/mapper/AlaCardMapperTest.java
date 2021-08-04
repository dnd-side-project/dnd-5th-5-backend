package com.meme.ala.domain.alacard.entity.mapper;

import com.meme.ala.common.DtoFactory;
import com.meme.ala.common.EntityFactory;
import com.meme.ala.domain.aggregation.model.entity.WordCount;
import com.meme.ala.domain.alacard.model.dto.response.AlaCardDto;
import com.meme.ala.domain.alacard.model.entity.AlaCard;
import com.meme.ala.domain.alacard.model.entity.cardSetting.AlaCardSetting;
import com.meme.ala.domain.alacard.model.mapper.AlaCardMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AlaCardMapperTest {
    @Autowired
    private AlaCardMapper alaCardMapper;

    private final AlaCard alaCard = EntityFactory.testAlaCard();
    private final AlaCardSetting alaCardSetting = EntityFactory.testAlaCardSetting();
    private final AlaCardDto alaCardDto = DtoFactory.testAlaCardDto();
    private final List<WordCount> wordCountList = new LinkedList<WordCount>(Collections.singletonList(EntityFactory.testWordCount()));

    @Test
    void 엔티티에서_DTO변환_테스트() {
        AlaCardDto mappedDto = alaCardMapper.toDto(alaCard, alaCardSetting, "testSentence", wordCountList, true);
        assertEquals(mappedDto.getSelectedWordList().get(0).getWordName(), alaCardDto.getSelectedWordList().get(0).getWordName());
        assertEquals(mappedDto.getAlaCardSettingDto().getFont(), alaCardDto.getAlaCardSettingDto().getFont());
        assertEquals(mappedDto.getAlaCardSettingDto().getFontColor(), alaCardDto.getAlaCardSettingDto().getFontColor());
        assertEquals(mappedDto.getAlaCardSettingDto().getBackgroundImgUrl(), alaCardDto.getAlaCardSettingDto().getBackgroundImgUrl());
    }
}