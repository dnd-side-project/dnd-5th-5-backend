package com.meme.ala.domain.aggregation.service;

import com.meme.ala.common.EntityFactory;
import com.meme.ala.domain.aggregation.model.entity.Aggregation;
import com.meme.ala.domain.aggregation.repository.AggregationRepository;
import com.meme.ala.domain.alacard.model.entity.AlaCard;
import com.meme.ala.domain.alacard.model.entity.MiddleCategory;
import com.meme.ala.domain.alacard.model.entity.Word;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class AggregationServiceImplTest {
    @InjectMocks
    private AggregationServiceImpl aggregationService;

    @Mock
    private AggregationRepository aggregationRepository;

    @DisplayName("키워드 Aggregation")
    @Test
    void submitWordList() throws UnsupportedEncodingException {
        // 사용자의 aggregation을 읽어들여, 제공받은 키워드 리스트를 +1 해야한다.
        // 만약 기존 aggregation에 존재하지 않는다면, 새로 추가해야 한다.
        Aggregation aggregation = EntityFactory.testAggregation();

        AlaCard alaCard = EntityFactory.testAlaCard();

        String a = makeId(alaCard, alaCard.getMiddleCategoryList().get(0), alaCard.getMiddleCategoryList().get(0).getWordList().get(0));
        String b = makeId(alaCard, alaCard.getMiddleCategoryList().get(0), alaCard.getMiddleCategoryList().get(0).getWordList().get(1));

        List<String> wordList = new LinkedList<>(Arrays.asList(a, b));

        aggregationService.submitWordList(aggregation, wordList);

        assertEquals(aggregation.getWordCountList().get(0).getWord().getWordName(), "testWord");
        assertEquals(aggregation.getWordCountList().get(0).getCount(), 10);
        assertEquals(aggregation.getWordCountList().get(1).getWord().getWordName(), "공부");
        assertEquals(aggregation.getWordCountList().get(1).getCount(), 1);
        assertEquals(aggregation.getWordCountList().get(2).getWord().getWordName(), "수영");
        assertEquals(aggregation.getWordCountList().get(2).getCount(), 1);

        aggregation.getWordCountList().forEach(el -> System.out.println(el.getMiddleCategoryName() + ", " + el.getWord().getWordName() + " : " + el.getCount()));

    }

    private String makeId(AlaCard alaCard, MiddleCategory middleCategory, Word word){

        return Base64.getEncoder()
                .encodeToString((
                        alaCard.getBigCategory() +"-" +
                                middleCategory.getMiddleCategoryName() + "-" +
                                middleCategory.getHint() + "-" +
                                word.getWordName())
                        .getBytes());
    }
}