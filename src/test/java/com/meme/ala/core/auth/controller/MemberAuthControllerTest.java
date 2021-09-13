package com.meme.ala.core.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meme.ala.common.AbstractControllerTest;
import com.meme.ala.core.auth.jwt.JwtProvider;
import com.meme.ala.core.auth.oauth.model.GoogleUser;
import com.meme.ala.core.auth.oauth.model.KakaoUser;
import com.meme.ala.core.auth.oauth.model.NaverUser;
import com.meme.ala.core.auth.oauth.model.OAuthProvider;
import com.meme.ala.core.auth.oauth.service.OAuthService;
import com.meme.ala.domain.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;

import static com.meme.ala.core.config.ApiDocumentUtils.getDocumentRequest;
import static com.meme.ala.core.config.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class MemberAuthControllerTest extends AbstractControllerTest {

    @MockBean
    private MemberService memberService;

    @MockBean
    private OAuthService oAuthService;

    @MockBean
    private JwtProvider jwtProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("구글 OAuth 로그인 테스트")
    @Test
    public void 구글_OAuth_로그인_유닛테스트() throws Exception {
        String sampleRequestBody =
                "{\n" +
                        "  \"profileObj\": {\n" +
                        "    \"googleId\": \"1155\",\n" +
                        "    \"imageUrl\": \"https://user-images.githubusercontent.com/46064193/125324764-2bc8e200-e37b-11eb-8d07-9ac29d0d1b1a.png\",\n" +
                        "    \"email\": \"test@gmail.com\",\n" +
                        "    \"name\": \"Jongmin Jung\",\n" +
                        "    \"givenName\": \"Jongmin\",\n" +
                        "    \"familyName\": \"Jung\"\n" +
                        "  }\n" +
                        "}";

        Map<String, Object> data = objectMapper.readValue(sampleRequestBody, Map.class);

        when(oAuthService.getMemberByProvider(new ObjectMapper().readValue(sampleRequestBody, Map.class), OAuthProvider.GOOGLE)).thenReturn(new GoogleUser(data));

        when(memberService.existsProviderId(any())).thenReturn(Boolean.TRUE);

        when(jwtProvider.createToken(any())).thenReturn("dummy token");

        mockMvc.perform(post("/api/v1/oauth/jwt/google")
                .contentType(MediaType.APPLICATION_JSON)
                .content(sampleRequestBody))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("dummy token"))
                .andDo(print())
                .andDo(document("api/v1/oauth/jwt/google",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("profileObj.googleId").description("Identifier"),
                                fieldWithPath("profileObj.imageUrl").description("사용자 프로필 링크"),
                                fieldWithPath("profileObj.email").description("사용자 이메일"),
                                fieldWithPath("profileObj.name").description("사용자 이름"),
                                fieldWithPath("profileObj.givenName").description("given name"),
                                fieldWithPath("profileObj.familyName").description("family name")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("기존 사용자: LOGIN / 신규 사용자: JOIN"),
                                fieldWithPath("data").description("JWT 값"),
                                fieldWithPath("timestamp").description("타임스탬프")
                        )
                ));
    }

    @DisplayName("카카오 OAuth 로그인 테스트")
    @Test
    public void 카카오_OAuth_로그인_유닛테스트() throws Exception {
        String sampleRequestBody =
                "{\n" +
                        "  \"profileObj\": {\n" +
                        "    \"kakaoId\": \"1155\",\n" +
                        "    \"imageUrl\": \"https://user-images.githubusercontent.com/46064193/125324764-2bc8e200-e37b-11eb-8d07-9ac29d0d1b1a.png\",\n" +
                        "    \"email\": \"test@gmail.com\",\n" +
                        "    \"name\": \"Jongmin Jung\"\n" +
                        "  }\n" +
                        "}";

        Map<String, Object> data = objectMapper.readValue(sampleRequestBody, Map.class);

        when(oAuthService.getMemberByProvider(new ObjectMapper().readValue(sampleRequestBody, Map.class), OAuthProvider.KAKAO)).thenReturn(new KakaoUser(data));

        when(memberService.existsProviderId(any())).thenReturn(Boolean.TRUE);

        when(jwtProvider.createToken(any())).thenReturn("dummy token");

        mockMvc.perform(post("/api/v1/oauth/jwt/kakao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(sampleRequestBody))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("dummy token"))
                .andDo(print())
                .andDo(document("api/v1/oauth/jwt/kakao",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("profileObj.kakaoId").description("Identifier"),
                                fieldWithPath("profileObj.imageUrl").description("사용자 프로필 링크"),
                                fieldWithPath("profileObj.email").description("사용자 이메일"),
                                fieldWithPath("profileObj.name").description("사용자 이름")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("기존 사용자: LOGIN / 신규 사용자: JOIN"),
                                fieldWithPath("data").description("JWT 값"),
                                fieldWithPath("timestamp").description("타임스탬프")
                        )
                ));
    }

    @Test
    public void 네이버_OAuth_로그인_유닛테스트() throws Exception {
        String sampleRequestBody =
                "  {\n" +
                        "    \"id\": \"afdasfdadsf\",\n" +
                        "    \"profile_image\": \"https://user-images.githubusercontent.com/46064193/125324764-2bc8e200-e37b-11eb-8d07-9ac29d0d1b1a.png\",\n" +
                        "    \"email\": \"test@gmail.com\",\n" +
                        "    \"name\": \"Jongmin Jung\"}";

        Map<String, Object> data = objectMapper.readValue(sampleRequestBody, Map.class);

        when(oAuthService.getMemberByProvider(new ObjectMapper().readValue(sampleRequestBody, Map.class), OAuthProvider.NAVER)).thenReturn(new NaverUser(data));

        when(memberService.existsProviderId(any())).thenReturn(Boolean.TRUE);

        when(jwtProvider.createToken(any())).thenReturn("dummy token");

        mockMvc.perform(post("/api/v1/oauth/jwt/naver")
                .contentType(MediaType.APPLICATION_JSON)
                .content(sampleRequestBody))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("dummy token"))
                .andDo(print())
                .andDo(document("api/v1/oauth/jwt/naver",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("id").description("Identifier"),
                                fieldWithPath("profile_image").description("사용자 프로필 링크"),
                                fieldWithPath("email").description("사용자 이메일"),
                                fieldWithPath("name").description("사용자 이름")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("기존 사용자: LOGIN / 신규 사용자: JOIN"),
                                fieldWithPath("data").description("JWT 값"),
                                fieldWithPath("timestamp").description("타임스탬프")
                        )
                ));
    }

    @DisplayName("카카오 가입 테스트")
    @Test
    public void 회원가입() throws Exception {
        String sampleRequestBody = "{\n" +
                "  \"nickname\": \"babojjm\",\n" +
                "  \"profileObj\": {\n" +
                "    \"kakaoId\": \"1155\",\n" +
                "    \"imageUrl\": \"https://user-images.githubusercontent.com/46064193/125324764-2bc8e200-e37b-11eb-8d07-9ac29d0d1b1a.png\",\n" +
                "    \"email\": \"test@gmail.com\",\n" +
                "    \"name\": \"Jongmin Jung\"\n" +
                "  }\n" +
                "}";
        Map<String, Object> data = objectMapper.readValue(sampleRequestBody, Map.class);

        when(oAuthService.getMemberByProvider(new ObjectMapper().readValue(sampleRequestBody, Map.class), OAuthProvider.KAKAO)).thenReturn(new KakaoUser(data));
        when(jwtProvider.createToken(any())).thenReturn("dummy token");

        mockMvc.perform(post("/api/v1/oauth/join/kakao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(sampleRequestBody))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("dummy token"))
                .andDo(print())
                .andDo(document("api/v1/oauth/join/kakao",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("nickname").description("설정할 닉네임"),
                                fieldWithPath("profileObj.kakaoId").description("Identifier"),
                                fieldWithPath("profileObj.imageUrl").description("사용자 프로필 링크"),
                                fieldWithPath("profileObj.email").description("사용자 이메일"),
                                fieldWithPath("profileObj.name").description("사용자 이름")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("기존 사용자: LOGIN / 신규 사용자: JOIN"),
                                fieldWithPath("data").description("JWT 값"),
                                fieldWithPath("timestamp").description("타임스탬프")
                        )
                ));
    }
}
