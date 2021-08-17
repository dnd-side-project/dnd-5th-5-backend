package com.meme.ala.domain.alacard.controller;

import com.meme.ala.common.AbstractControllerTest;
import com.meme.ala.common.DtoFactory;
import com.meme.ala.common.EntityFactory;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.service.MemberCardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;

import static com.meme.ala.core.config.ApiDocumentUtils.getDocumentRequest;
import static com.meme.ala.core.config.ApiDocumentUtils.getDocumentResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AlaCardControllerV2Test extends AbstractControllerTest {
    @MockBean
    private MemberCardService memberCardService;

    private Member testMember = EntityFactory.testMember();

    @Test
    public void 알라_카드의_단어_리스트를_제공하는_테스트_V2() throws Exception {

        given(memberCardService.getWordList(any(String.class))).willReturn(Arrays.asList(DtoFactory.testSelectionWordDto()));

        mockMvc.perform(get("/api/v2/alacard/wordlist")
                .param("nickname", testMember.getMemberSetting().getNickname())
                .param("offset", "0")
                .param("cookieId", "testId"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("api/v2/alacard/wordlist/get",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("cookieId").description("쿠키 id - optional"),
                                parameterWithName("nickname").description("닉네임"),
                                parameterWithName("offset").description("오프셋")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("설명"),
                                fieldWithPath("data").description("선택된 카드"),
                                fieldWithPath("data.cookieId").description("쿠키 id"),
                                fieldWithPath("data.wordList[*].id").description("단어 고유 id"),
                                fieldWithPath("data.wordList[*].hint").description("힌트"),
                                fieldWithPath("data.wordList[*].wordName").description("단어"),
                                fieldWithPath("timestamp").description("타임스탬프")
                        )
                ));
    }
}