package com.meme.ala.core.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meme.ala.core.auth.jwt.JwtTokenProvider;
import com.meme.ala.core.auth.oauth.OAuthProvider;
import com.meme.ala.core.config.WebSecurityConfig;
import com.meme.ala.domain.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import java.util.Map;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletException;

import static com.meme.ala.core.config.ApiDocumentUtils.getDocumentRequest;
import static com.meme.ala.core.config.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(value = MemberAuthController.class, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfig.class)})
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class MemberAuthControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) throws ServletException {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @MockBean
    private MemberService memberService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("구글 OAuth 로그인/가입 테스트")
    @Test
    public void 구글_OAuth_로그인_유닛테스트() throws Exception{
        String sampleRequestBody=
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
        when(memberService.loginOrJoin(new ObjectMapper().readValue(sampleRequestBody, Map.class), OAuthProvider.GOOGLE))
                .thenReturn("dummy token");

        mockMvc.perform(post("/api/v1/oauth/jwt/google")
                .contentType(MediaType.APPLICATION_JSON)
                .content(sampleRequestBody))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("dummy token"))
                .andDo(print())
                .andDo(document("/api/v1/oauth/jwt/google",
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
                                fieldWithPath("message").description("설명"),
                                fieldWithPath("data").description("JWT 값"),
                                fieldWithPath("timestamp").description("타임스탬프")
                        )
                ));
    }

    @Test
    public void 네이버_OAuth_로그인_유닛테스트() throws Exception{
        String sampleRequestBody=
                "  {\n" +
                        "    \"id\": \"afdasfdadsf\",\n" +
                        "    \"profile_image\": \"https://user-images.githubusercontent.com/46064193/125324764-2bc8e200-e37b-11eb-8d07-9ac29d0d1b1a.png\",\n" +
                        "    \"email\": \"test@gmail.com\",\n" +
                        "    \"name\": \"Jongmin Jung\"}";

        when(memberService.loginOrJoin(new ObjectMapper().readValue(sampleRequestBody, Map.class),OAuthProvider.NAVER))
                .thenReturn("dummy token");

        mockMvc.perform(post("/api/v1/oauth/jwt/naver")
                .contentType(MediaType.APPLICATION_JSON)
                .content(sampleRequestBody))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("dummy token"))
                .andDo(print())
                .andDo(document("/api/v1/oauth/jwt/naver",
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
                                fieldWithPath("message").description("설명"),
                                fieldWithPath("data").description("JWT 값"),
                                fieldWithPath("timestamp").description("타임스탬프")
                        )
                ));
    }
}
