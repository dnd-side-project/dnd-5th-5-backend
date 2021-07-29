package com.meme.ala.domain.aggregation.entity.mapper;

import com.meme.ala.common.DtoFactory;
import com.meme.ala.common.EntityFactory;
import com.meme.ala.domain.aggregation.model.dto.response.WordCountDto;
import com.meme.ala.domain.aggregation.model.entity.WordCount;
import com.meme.ala.domain.aggregation.model.mapper.WordCountMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class WordCountMapperTest {
    @Autowired
    private WordCountMapper wordCountMapper;

    private final WordCount wordCount = EntityFactory.testWordCount();
    private final WordCountDto wordCountDto = DtoFactory.testWordCountDto();

    @Test
    void 엔티티에서_DTO변환_테스트() {
        WordCountDto mappedDto = wordCountMapper.toDto(wordCount);
        assertEquals(mappedDto.getWordName(), wordCountDto.getWordName());
        assertEquals(mappedDto.getCount(), wordCountDto.getCount());
    }
}