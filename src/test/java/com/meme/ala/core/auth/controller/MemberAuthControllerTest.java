package com.meme.ala.core.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meme.ala.common.AbstractControllerTest;
import com.meme.ala.common.utils.NaverOauthUtil;
import com.meme.ala.core.auth.jwt.JwtProvider;
import com.meme.ala.core.auth.oauth.model.GoogleUser;
import com.meme.ala.core.auth.oauth.model.KakaoUser;
import com.meme.ala.core.auth.oauth.model.NaverUser;
import com.meme.ala.core.auth.oauth.model.OAuthProvider;
import com.meme.ala.core.auth.oauth.service.OAuthService;
import com.meme.ala.domain.member.model.dto.JwtVO;
import com.meme.ala.domain.member.service.MemberAuthService;
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
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @MockBean
    private MemberAuthService memberAuthService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("?????? OAuth ?????????/?????? ?????????")
    @Test
    public void ??????_OAuth_?????????_???????????????() throws Exception {
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
                                fieldWithPath("profileObj.imageUrl").description("????????? ????????? ??????"),
                                fieldWithPath("profileObj.email").description("????????? ?????????"),
                                fieldWithPath("profileObj.name").description("????????? ??????"),
                                fieldWithPath("profileObj.givenName").description("given name"),
                                fieldWithPath("profileObj.familyName").description("family name")
                        ),
                        responseFields(
                                fieldWithPath("status").description("?????? ??????"),
                                fieldWithPath("message").description("?????? ?????????: LOGIN / ?????? ?????????: JOIN"),
                                fieldWithPath("data").description("JWT ???"),
                                fieldWithPath("timestamp").description("???????????????")
                        )
                ));
    }

    @DisplayName("????????? OAuth ?????????/?????? ?????????")
    @Test
    public void ?????????_OAuth_?????????_???????????????() throws Exception {
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
                                fieldWithPath("profileObj.imageUrl").description("????????? ????????? ??????"),
                                fieldWithPath("profileObj.email").description("????????? ?????????"),
                                fieldWithPath("profileObj.name").description("????????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("status").description("?????? ??????"),
                                fieldWithPath("message").description("?????? ?????????: LOGIN / ?????? ?????????: JOIN"),
                                fieldWithPath("data").description("JWT ???"),
                                fieldWithPath("timestamp").description("???????????????")
                        )
                ));
    }

    @Test
    public void ?????????_OAuth_?????????_???????????????() throws Exception {
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
                                fieldWithPath("profile_image").description("????????? ????????? ??????"),
                                fieldWithPath("email").description("????????? ?????????"),
                                fieldWithPath("name").description("????????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("status").description("?????? ??????"),
                                fieldWithPath("message").description("?????? ?????????: LOGIN / ?????? ?????????: JOIN"),
                                fieldWithPath("data").description("JWT ???"),
                                fieldWithPath("timestamp").description("???????????????")
                        )
                ));
    }

    @Test
    public void ?????????_OAuth_GET_???????????????() throws Exception {
        String sampleAccessToken = "AAAAO9t3lY18fHzDi0xlDmoRPNNndkqUfYN7zSMAP17vAYOwxyKIHpKzeN6VxRSG7cfvA8hytclT2nydGBd8qiLCJKM";
        String sampleRequestBody =
                "  {\n" +
                        "    \"id\": \"afdasfdadsf\",\n" +
                        "    \"profile_image\": \"https://user-images.githubusercontent.com/46064193/125324764-2bc8e200-e37b-11eb-8d07-9ac29d0d1b1a.png\",\n" +
                        "    \"email\": \"test@gmail.com\",\n" +
                        "    \"name\": \"Jongmin Jung\"}";

        Map<String, Object> data = objectMapper.readValue(sampleRequestBody, Map.class);

        when(memberAuthService.tokenTojwt(any())).thenReturn(new JwtVO(OAuthProvider.NAVER, "dummy token"));

        mockMvc.perform(get("/api/v1/oauth/jwt/naver")
                .param("access_token", sampleAccessToken))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("dummy token"))
                .andDo(print())
                .andDo(document("api/v1/oauth/jwt/get/naver",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("access_token").description("????????? ????????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("status").description("?????? ??????"),
                                fieldWithPath("message").description("?????? ?????????: LOGIN / ?????? ?????????: JOIN"),
                                fieldWithPath("data").description("JWT ???"),
                                fieldWithPath("timestamp").description("???????????????")
                        )
                ));
    }
}
