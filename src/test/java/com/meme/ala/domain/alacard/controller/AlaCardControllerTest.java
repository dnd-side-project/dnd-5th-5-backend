package com.meme.ala.domain.alacard.controller;

import com.meme.ala.common.AbstractControllerTest;
import com.meme.ala.common.DtoFactory;
import com.meme.ala.common.EntityFactory;
import com.meme.ala.common.message.ResponseMessage;
import com.meme.ala.core.config.AlaWithAccount;
import com.meme.ala.domain.aggregation.model.entity.Aggregation;
import com.meme.ala.domain.aggregation.service.AggregationService;
import com.meme.ala.domain.alacard.model.dto.response.AlaCardDto;
import com.meme.ala.domain.alacard.model.dto.response.AlaCardSettingDto;
import com.meme.ala.domain.alacard.model.dto.response.BackgroundDtoInSetting;
import com.meme.ala.domain.alacard.model.entity.cardSetting.Background;
import com.meme.ala.domain.alacard.service.AlaCardService;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.service.MemberCardService;
import com.meme.ala.domain.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static com.meme.ala.core.config.ApiDocumentUtils.getDocumentRequest;
import static com.meme.ala.core.config.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AlaCardControllerTest extends AbstractControllerTest {
    @Value("${cloud.aws.s3.bucket.url}")
    private String s3Url;

    @MockBean
    private AlaCardService alaCardService;
    @MockBean
    private AggregationService aggregationService;
    @MockBean
    private MemberCardService memberCardService;
    @MockBean
    private MemberService memberService;

    private Member testMember = EntityFactory.testMember();

    @DisplayName("알라 카드의 단어 리스트를 제공하는 테스트")
    @Test
    public void 알라_카드의_단어_리스트를_제공하는_테스트() throws Exception {

        given(memberCardService.getWordList(any(String.class))).willReturn(Arrays.asList(DtoFactory.testSelectionWordDto()));

        mockMvc.perform(get("/api/v1/alacard/wordlist")
                .param("nickname", testMember.getMemberSetting().getNickname())
                .param("offset", "0")
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("api/v1/alacard/wordlist/get",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("nickname").description("닉네임"),
                                parameterWithName("offset").description("오프셋")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("설명"),
                                fieldWithPath("data").description("선택된 카드"),
                                fieldWithPath("data[*].id").description("단어 고유 id"),
                                fieldWithPath("data[*].hint").description("힌트"),
                                fieldWithPath("data[*].wordName").description("단어"),
                                fieldWithPath("timestamp").description("타임스탬프")
                        )
                ));
    }

    @DisplayName("사용자의 문장 리스트와 단어 카운트를 제공하는 테스트")
    @Test
    public void 사용자의_문장_리스트와_단어_카운트를_제공하는_테스트() throws Exception {
        Member member = EntityFactory.testMember();
        List<AlaCardDto> alaCardDtoList = new LinkedList<>(Arrays.asList(DtoFactory.testAlaCardDto()));

        given(alaCardService.getAlaCardDtoList(any(Member.class))).willReturn(alaCardDtoList);
        given(memberService.findByNickname(any(String.class))).willReturn(member);

        mockMvc.perform(get("/api/v1/alacard/alacardlist")
                .param("nickname", EntityFactory.testMember().getMemberSetting().getNickname())
        )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].sentence").value("testSentence"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].selectedWordList[0].wordName").value("testWord"))
                .andDo(print())
                .andDo(document("api/v1/alacard/alacardlist",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("nickname").description("평가 대상 닉네임")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("설명"),
                                fieldWithPath("data").description("사용자의 문장리스트와 단어 카운트"),
                                fieldWithPath("data[*].sentence").description("문장"),
                                fieldWithPath("data[*].isCompleted").description("완성 여부"),
                                fieldWithPath("data[*].selectedWordList").description("문장에서 선택된 단어카운트"),
                                fieldWithPath("data[*].selectedWordList[*].wordName").description("선택된단어"),
                                fieldWithPath("data[*].selectedWordList[*].count").description("선택된단어횟수"),
                                fieldWithPath("data[*].alaCardSettingDto").description("알라카드 세팅정보"),
                                fieldWithPath("data[*].alaCardSettingDto.alaCardId").description("알라카드의 Id"),
                                fieldWithPath("data[*].alaCardSettingDto.backgroundImgUrl").description("배경 이미지"),
                                fieldWithPath("data[*].alaCardSettingDto.fontColor").description("글씨 색"),
                                fieldWithPath("data[*].alaCardSettingDto.isOpen").description("카드 공개 여부"),
                                fieldWithPath("timestamp").description("타임스탬프")
                        )
                ));
    }

    @DisplayName("사용자의 단어 리스트를 제출받는 테스트")
    @Test
    public void 사용자의_단어_리스트를_제출받는_테스트() throws Exception {
        String sampleRequestBody =
                "{\n" +
                        "  \"idList\": [\n" +
                        "    \"6rKM7J6ELeuwsO2LgOq3uOudvOyatOuTnC3rsLDqt7gg7ZSM66CI7J20Le2UhOuhnOq4sOygiOufrA==\",\n" +
                        "    \"7Jyg66i4LeyghOyDnS3soITsg50tMTTshLjquLAg7ZSE656R7IqkIOq3gOyhsQ==\",\n" +
                        "    \"6rKM7J6ELeyYpOuyhOybjOy5mC3slrTsmrjrpqzripQg7LGU7ZS87Ja4Le2VnOyhsA==\"\n" +
                        "  ]\n" +
                        "}";

        doNothing().when(aggregationService).submitWordList(any(Aggregation.class), any(List.class));
        given(memberService.findByNickname(any(String.class))).willReturn(EntityFactory.testMember());
        given(aggregationService.findByMember(any(Member.class))).willReturn(EntityFactory.testAggregation());

        mockMvc.perform(patch("/api/v1/alacard/wordlist")
                .param("nickname", testMember.getMemberSetting().getNickname())
                .contentType(MediaType.APPLICATION_JSON)
                .content(sampleRequestBody))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(ResponseMessage.SUBMITTED))
                .andDo(print())
                .andDo(document("api/v1/alacard/wordlist/patch",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("nickname").description("평가 대상 닉네임")
                        ),
                        requestFields(
                                fieldWithPath("idList").description("선택된 단어의 id들")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("설명"),
                                fieldWithPath("data").description("사용자 단어 리스트 제출이 성공 메시지"),
                                fieldWithPath("timestamp").description("타임스탬프")
                        )
                ));
    }

    @DisplayName("배경 리스트를 제공하는 테스트")
    @Test
    public void 배경_리스트를_제공하는_테스트() throws Exception {
        given(alaCardService.getBackgroundImageUrls()).willReturn(Arrays.asList(s3Url + "/static/test.svg", s3Url + "/static/test2.svg"));

        mockMvc.perform(get("/api/v1/alacard/backgrounds"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0]").value(s3Url + "/static/test.svg"))
                .andDo(print())
                .andDo(document("api/v1/alacard/backgrounds",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("설명"),
                                fieldWithPath("data").description("배경의 URL"),
                                fieldWithPath("timestamp").description("타임스탬프")
                        )
                ));
    }

    @DisplayName("배경 리스트와 카테고리를 제공하는 테스트")
    @Test
    public void 배경_리스트와_카테고리를_제공하는_테스트() throws Exception {
        Background background = EntityFactory.testBackground();

        background.setImgUrl(s3Url + "/static/test.svg");

        BackgroundDtoInSetting dto = BackgroundDtoInSetting.builder()
                .thumbnailImgUrl(background.getThumbnailImgUrl())
                .backgroundImgUrl(background.getImgUrl())
                .fontColor(background.getFontColor())
                .build();

        Map<String, List<BackgroundDtoInSetting>> sampleMap = new HashMap<>();
        sampleMap.put("Gradient", Arrays.asList(dto));
        sampleMap.put("Photo", Arrays.asList(dto));
        sampleMap.put("Solid", Arrays.asList(dto));

        given(alaCardService.getBackgroundThumbCategory()).willReturn(sampleMap);

        mockMvc.perform(get("/api/v1/alacard/background"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.Gradient[0].fontColor").value(background.getFontColor()))
                .andDo(print())
                .andDo(document("api/v1/alacard/background",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("설명"),
                                fieldWithPath("data").description("배경들"),
                                fieldWithPath("data.Gradient[*].thumbnailImgUrl").description("Gradient 썸네일 이미지 URL"),
                                fieldWithPath("data.Gradient[*].backgroundImgUrl").description("Gradient 이미지 URL"),
                                fieldWithPath("data.Gradient[*].fontColor").description("Gradient 폰트 컬러"),
                                fieldWithPath("data.Photo[*].thumbnailImgUrl").description("Photo 썸네일 이미지 URL"),
                                fieldWithPath("data.Photo[*].backgroundImgUrl").description("Photo 이미지 URL"),
                                fieldWithPath("data.Photo[*].fontColor").description("Photo 폰트 컬러"),
                                fieldWithPath("data.Solid[*].thumbnailImgUrl").description("Solid 썸네일 이미지 URL"),
                                fieldWithPath("data.Solid[*].backgroundImgUrl").description("Solid 이미지 URL"),
                                fieldWithPath("data.Solid[*].fontColor").description("Solid 폰트 컬러"),
                                fieldWithPath("timestamp").description("타임스탬프")
                        )
                ));
    }

    @AlaWithAccount("test@gmail.com")
    @DisplayName("사용자의 카드 세팅을 변경하는 테스트")
    @Test
    public void 사용자의_카드_세팅을_변경하는_테스트() throws Exception {
        String sampleRequestBody =
                "{\n" +
                        "  \"alaCardId\": \"610034b5a221f126a4e7f500\",\n" +
                        "  \"backgroundImgUrl\": \"https://s3.ap-northeast-2.amazonaws.com/meme-ala-background/static/BG+Gradient+04.png\",\n" +
                        "  \"fontColor\": \"#FF0000F\",\n" +
                        "  \"isOpen\": false\n" +
                        "}";

        doNothing().when(memberCardService).saveSetting(any(Member.class), any(AlaCardSettingDto.class));

        mockMvc.perform(patch("/api/v1/alacard/alacardsetting")
                .contentType(MediaType.APPLICATION_JSON)
                .content(sampleRequestBody))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("api/v1/alacard/alacardsetting",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("alaCardId").description("매칭될 알라카드의 ObjectID - Essential"),
                                fieldWithPath("backgroundImgUrl").description("배경 이미지 - Optional"),
                                fieldWithPath("fontColor").description("폰트 색깔 - Optional"),
                                fieldWithPath("isOpen").description("공개 여부 - Optional")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("설명"),
                                fieldWithPath("timestamp").description("타임스탬프")
                        )
                ));
    }
}
