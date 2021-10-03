package com.meme.ala.domain.member.controller;

import com.meme.ala.common.AbstractControllerTest;
import com.meme.ala.common.EntityFactory;
import com.meme.ala.core.config.AlaWithAccount;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static com.meme.ala.core.config.ApiDocumentUtils.getDocumentRequest;
import static com.meme.ala.core.config.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// TODO: memberService를 MockBean으로 주입해야 함
public class MemberControllerTest extends AbstractControllerTest {
    @MockBean
    private MemberRepository memberRepository;

    @Value("${frontdomain}")
    private String frontUrl;

    @AlaWithAccount("test@gmail.com")
    @DisplayName("내 세팅 정보를 읽어오는 테스트")
    @Test
    public void 내_세팅_정보를_읽기_유닛테스트() throws Exception {
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
                                fieldWithPath("data.provider").description("사용자 provider"),
                                fieldWithPath("timestamp").description("타임스탬프")
                        )
                ));
    }

    @AlaWithAccount("test@gmail.com")
    @DisplayName("사용자 세팅 정보를 읽어오는 테스트")
    @Test
    public void 사용자_세팅_정보를_읽기_유닛테스트() throws Exception {
        given(memberRepository.findByMemberSettingNicknameAndMemberSetting_IsDeleted(any(String.class), eq(false))).willReturn(Optional.of(EntityFactory.testMember()));

        mockMvc.perform(get("/api/v1/member").param("nickname", "testNickname"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.nickname").value("testNickname"))
                .andDo(print())
                .andDo(document("api/v1/member/get",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("nickname").description("닉네임")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("설명"),
                                fieldWithPath("data").description("사용자 세팅 정보"),
                                fieldWithPath("data.email").description("사용자 이메일"),
                                fieldWithPath("data.nickname").description("사용자 닉네임"),
                                fieldWithPath("data.statusMessage").description("사용자 상태메시지"),
                                fieldWithPath("data.imgUrl").description("사용자 이미지 사진 URL"),
                                fieldWithPath("data.isOpen").description("사용자 공개 여부"),
                                fieldWithPath("data.provider").description("사용자 provider"),
                                fieldWithPath("timestamp").description("타임스탬프")
                        )
                ));
    }

    @AlaWithAccount("test@gmail.com")
    @DisplayName("사용자 세팅 정보 업데이트 테스트")
    @Test
    public void 사용자_세팅_정보_업데이트_테스트() throws Exception {
        String sampleRequestBody =
                "  {\n" +
                        "    \"nickname\": \"alala\", \n" +
                        "    \"statusMessage\": \"update status message\", \n" +
                        "    \"imgUrl\": \"/test/url.jpg\", \n" +
                        "    \"isOpen\": true }";

        when(memberRepository.save(any(Member.class))).then(AdditionalAnswers.returnsFirstArg());

        mockMvc.perform(patch("/api/v1/member/me")
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
                                fieldWithPath("data.provider").description("사용자 provider"),
                                fieldWithPath("timestamp").description("타임스탬프")
                        )
                ));
    }

    @DisplayName("사용자 닉네임 중복 처리 테스트")
    @Test
    public void 사용자_닉네임_중복_처리_테스트() throws Exception {
        when(memberRepository.existsMemberByMemberSettingNicknameAndMemberSetting_IsDeleted("testNickname", false)).thenReturn(true);

        mockMvc.perform(get("/api/v1/member/exists?nickname=testNickname"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("true"))
                .andDo(print())
                .andDo(document("api/v1/member/exists",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("nickname").description("닉네임")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("설명"),
                                fieldWithPath("data").description("닉네임 중복 여부"),
                                fieldWithPath("timestamp").description("타임스탬프")
                        )
                ));
    }

    @AlaWithAccount("test")
    @DisplayName("사용자 삭제 테스트")
    @Test
    public void 사용자_삭제_테스트() throws Exception {
        given(memberRepository.save(any(Member.class))).willReturn(EntityFactory.testMember());

        mockMvc.perform(delete("/api/v1/member"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("deleted"))
                .andDo(print())
                .andDo(document("api/v1/member/delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("설명"),
                                fieldWithPath("data").description("삭제 여부"),
                                fieldWithPath("timestamp").description("타임스탬프")
                        )
                ));
    }

    @DisplayName("사용자 닉네임으로 셀렉뷰 공유 링크")
    @Test
    public void 사용자_닉네임으로_셀렉뷰_공유_링크() throws Exception {
        mockMvc.perform(get("/api/v1/member/sharelink?nickname=testNickname"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(frontUrl + "select/" + "testNickname"))
                .andDo(print())
                .andDo(document("api/v1/member/sharelink",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("nickname").description("닉네임")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("설명"),
                                fieldWithPath("data").description("사용자 셀렉뷰 공유 링크"),
                                fieldWithPath("timestamp").description("타임스탬프")
                        )
                ));
    }

    @DisplayName("사용자 닉네임으로 마이페이지 공유 링크")
    @Test
    public void 사용자_닉네임으로_마이페이지_공유_링크() throws Exception {
        mockMvc.perform(get("/api/v1/member/mypagelink?nickname=testNickname"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(frontUrl + "mypage/" + "testNickname"))
                .andDo(print())
                .andDo(document("api/v1/member/mypagelink",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("nickname").description("닉네임")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("설명"),
                                fieldWithPath("data").description("사용자 마이페이지 공유 링크"),
                                fieldWithPath("timestamp").description("타임스탬프")
                        )
                ));
    }
}

