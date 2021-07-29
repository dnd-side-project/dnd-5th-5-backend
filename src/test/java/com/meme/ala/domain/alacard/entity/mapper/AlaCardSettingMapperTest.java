package com.meme.ala.domain.alacard.entity.mapper;

import com.meme.ala.common.DtoFactory;
import com.meme.ala.common.EntityFactory;
import com.meme.ala.domain.alacard.model.dto.response.AlaCardSettingDto;
import com.meme.ala.domain.alacard.model.mapper.AlaCardSettingMapper;
import com.meme.ala.domain.member.model.entity.cardSetting.AlaCardSetting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AlaCardSettingMapperTest {
    @Autowired
    AlaCardSettingMapper alaCardSettingMapper;

    private final AlaCardSetting alaCardSetting = EntityFactory.testAlaCardSetting();
    private final AlaCardSettingDto alaCardSettingDto = DtoFactory.testAlaCardSettingDto();

    @Test
    void 엔티티에서_DTO변환_테스트() {
        AlaCardSettingDto mappedDto = alaCardSettingMapper.toDto(alaCardSetting);
        assertEquals(mappedDto.getBackgroundImgUrl(), alaCardSettingDto.getBackgroundImgUrl());
        assertEquals(mappedDto.getFont(), alaCardSettingDto.getFont());
        assertEquals(mappedDto.getFontColor(), alaCardSettingDto.getFontColor());
        assertEquals(mappedDto.getIsOpen(), alaCardSettingDto.getIsOpen());
    }
}