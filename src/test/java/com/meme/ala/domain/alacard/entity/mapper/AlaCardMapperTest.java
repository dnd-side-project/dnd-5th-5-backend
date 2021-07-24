package com.meme.ala.domain.alacard.entity.mapper;

import com.meme.ala.common.DtoFactory;
import com.meme.ala.common.EntityFactory;
import com.meme.ala.domain.alacard.model.dto.request.AlaCardSaveDto;
import com.meme.ala.domain.alacard.model.dto.response.SelectionWordDto;
import com.meme.ala.domain.alacard.model.entity.AlaCard;
import com.meme.ala.domain.alacard.model.entity.Word;
import com.meme.ala.domain.alacard.model.mapper.AlaCardSaveMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AlaCardMapperTest {

    @Autowired
    private AlaCardSaveMapper alaCardSaveMapper;

    private final AlaCardSaveDto dto = DtoFactory.alaCardSaveDto();
    private final AlaCard alaCard = EntityFactory.testAlaCard();

    @Test
    void DTO로부터_엔티티_업데이트_테스트(){
        AlaCard mappedEntity = alaCardSaveMapper.toEntity(dto);
        assertEquals(dto.getBigCategory(),mappedEntity.getBigCategory());
        assertEquals(dto.getMiddleCategoryList().get(0).getMiddleCategoryName(),
                mappedEntity.getMiddleCategoryList().get(0).getMiddleCategoryName());
    }
    @Test
    void 엔티티로부터_SelectionWordDto리스트_변환_테스트(){
        List<SelectionWordDto> dtoList = alaCardSaveMapper.alaCardToSelectionWordDtoList(alaCard);
        assertEquals(dtoList.get(0).getWordName(),alaCard.getMiddleCategoryList().get(0).getWordList().get(0).getWordName());
        assertEquals(dtoList.get(0).getBigCategory(),alaCard.getBigCategory());
        assertEquals(dtoList.get(0).getHint(),alaCard.getMiddleCategoryList().get(0).getHint());
    }
}
