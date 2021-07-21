package com.meme.ala.domain.member.controller;

import com.meme.ala.common.AbstractControllerTest;
import com.meme.ala.core.config.AlaWithAccount;
import com.meme.ala.domain.member.service.MemberService;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.meme.ala.core.config.ApiDocumentUtils.getDocumentRequest;
import static com.meme.ala.core.config.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberControllerTest extends AbstractControllerTest {

    @MockBean
    private MemberService memberService;

    @AlaWithAccount("test@gmail.com")
    @DisplayName("사용자 세팅 정보를 읽어오는 테스트")
    @Test
    public void 사용자_세팅_정보를_읽기_유닛테스트() throws Exception{
        mockMvc.perform(get("/api/v1/member/me"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.nickname").value("testNickname"))
                .andDo(print())
                .andDo(document("api/v1/member/me/get",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("설명"),
                                fieldWithPath("data").description("사용자 세팅 정보"),
                                fieldWithPath("data.email").description("사용자 이메일"),
                                fieldWithPath("data.nickname").description("사용자 닉네임"),
                                fieldWithPath("data.statusMessage").description("사용자 상태메시지"),
                                fieldWithPath("data.imgUrl").description("사용자 이미지 사진 URL"),
                                fieldWithPath("data.isOpen").description("사용자 공개 여부"),
                                fieldWithPath("timestamp").description("타임스탬프")
                        )
                ));
    }

    @AlaWithAccount("test@gmail.com")
    @DisplayName("사용자 세팅 정보 업데이트 테스트")
    @Test
    public void 사용자_세팅_정보_업데이트_테스트() throws Exception{
        String sampleRequestBody=
                "  {\n" +
                        "    \"nickname\": \"alala\", \n" +
                        "    \"statusMessage\": \"update status message\", \n" +
                        "    \"imgUrl\": \"/test/url.jpg\", \n" +
                        "    \"isOpen\": true }";

        mockMvc.perform(put("/api/v1/member/me")
                        .content(sampleRequestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.nickname").value("alala"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.statusMessage").value("update status message"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.imgUrl").value("/test/url.jpg"))
                .andDo(print())
                .andDo(document("api/v1/member/me/post",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("nickname").description("사용자 닉네임 / 선택"),
                                fieldWithPath("statusMessage").description("사용자 상태메시지 / 선택"),
                                fieldWithPath("imgUrl").description("사용자 이미지 사진 URL / 선택"),
                                fieldWithPath("isOpen").description("사용자 계정 공개 여부 / 선택")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("설명"),
                                fieldWithPath("data").description("사용자 세팅 정보"),
                                fieldWithPath("data.email").description("사용자 이메일"),
                                fieldWithPath("data.nickname").description("사용자 닉네임"),
                                fieldWithPath("data.statusMessage").description("사용자 상태메시지"),
                                fieldWithPath("data.imgUrl").description("사용자 이미지 사진 URL"),
                                fieldWithPath("data.isOpen").description("사용자 계정 공개 여부"),
                                fieldWithPath("timestamp").description("타임스탬프")
                        )
                ));
    }
}

