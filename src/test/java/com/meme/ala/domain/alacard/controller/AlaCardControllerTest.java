package com.meme.ala.domain.alacard.controller;

import com.meme.ala.common.AbstractControllerTest;
import com.meme.ala.common.DtoFactory;
import com.meme.ala.common.EntityFactory;
import com.meme.ala.common.message.ResponseMessage;
import com.meme.ala.core.config.AlaWithAccount;
import com.meme.ala.domain.alacard.model.dto.response.AlaCardDto;
import com.meme.ala.domain.alacard.service.AlaCardService;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.service.MemberCardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AlaCardControllerTest extends AbstractControllerTest {
    @MockBean
    private AlaCardService alaCardService;
    private Member testMember = EntityFactory.testMember();

    @DisplayName("알라 카드의 단어 리스트를 제공하는 테스트")
    @Test
    public void 알라_카드의_단어_리스트를_제공하는_테스트() throws Exception {
        given(alaCardService.getWordList(any(String.class), any(Boolean.class))).willReturn(Arrays.asList(DtoFactory.testSelectionWordDto()));

        mockMvc.perform(get("/api/v1/alacard/wordlist?nickname=" + testMember.getMemberSetting().getNickname()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].bigCategory").value("test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].middleCategory").value("testMiddle"))
                .andDo(print())
                .andDo(document("api/v1/alacard/wordlist",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("nickname").description("닉네임")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("설명"),
                                fieldWithPath("data").description("선택된 카드"),
                                fieldWithPath("data[*].bigCategory").description("대분류"),
                                fieldWithPath("data[*].middleCategory").description("중분류"),
                                fieldWithPath("data[*].hint").description("힌트"),
                                fieldWithPath("data[*].wordName").description("단어"),
                                fieldWithPath("timestamp").description("타임스탬프")
                        )
                ));
    }

    @AlaWithAccount("test@gmail.com")
    @DisplayName("사용자의 문장 리스트와 단어 카운트를 제공하는 테스트")
    @Test
    public void 사용자의_문장_리스트와_단어_카운트를_제공하는_테스트() throws Exception {
        List<AlaCardDto> alaCardDtoList = new LinkedList<>(Arrays.asList(DtoFactory.testAlaCardDto()));

        given(alaCardService.getAlaCardDtoList(any(Member.class))).willReturn(alaCardDtoList);

        mockMvc.perform(get("/api/v1/alacard/alacardlist"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].sentence").value("testSentence"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].selectedWordList[0].wordName").value("testWord"))
                .andDo(print())
                .andDo(document("api/v1/alacard/alacardlist",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("설명"),
                                fieldWithPath("data").description("사용자의 문장리스트와 단어 카운트"),
                                fieldWithPath("data[*].sentence").description("문장"),
                                fieldWithPath("data[*].selectedWordList").description("문장에서 선택된 단어카운트"),
                                fieldWithPath("data[*].selectedWordList[*].wordName").description("선택된단어"),
                                fieldWithPath("data[*].selectedWordList[*].count").description("선택된단어횟수"),
                                fieldWithPath("data[*].alaCardSettingDto").description("알라카드 세팅정보"),
                                fieldWithPath("data[*].alaCardSettingDto.backgroundImgUrl").description("배경 이미지"),
                                fieldWithPath("data[*].alaCardSettingDto.fontColor").description("글씨 색"),
                                fieldWithPath("data[*].alaCardSettingDto.font").description("글씨체"),
                                fieldWithPath("data[*].alaCardSettingDto.isOpen").description("카드 공개 여부"),
                                fieldWithPath("timestamp").description("타임스탬프")
                        )
                ));
    }

    @AlaWithAccount("test@gmail.com")
    @DisplayName("사용자의 단어 리스트를 제출받는 테스트")
    @Test
    public void 사용자의_단어_리스트를_제출받는_테스트() throws Exception {
        List<AlaCardDto> alaCardDtoList = new LinkedList<>(Arrays.asList(DtoFactory.testAlaCardDto()));

        doNothing().when(alaCardService).submitWordList(any(Member.class), any(List.class));

        mockMvc.perform(post("/api/v1/alacard/wordlist"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(ResponseMessage.SUBMITTED))
                .andDo(print())
                .andDo(document("api/v1/alacard/wordlist",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("[*].bigCategory").description("대분류"),
                                parameterWithName("[*].middleCategory").description("중분류"),
                                parameterWithName("[*].hint").description("힌트"),
                                parameterWithName("[*].wordName").description("단어명")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("설명"),
                                fieldWithPath("data").description("사용자 단어 리스트 제출이 성공 메시지"),
                                fieldWithPath("data[*].sentence").description("문장"),
                                fieldWithPath("timestamp").description("타임스탬프")
                        )
                ));
    }
}
