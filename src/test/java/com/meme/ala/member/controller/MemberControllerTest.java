package com.meme.ala.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meme.ala.common.AbstractControllerTest;
import com.meme.ala.core.auth.jwt.JwtProvider;
import com.meme.ala.core.auth.oauth.OAuthProvider;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.service.MemberService;
import com.meme.ala.member.entity.MemberCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;

import static com.meme.ala.core.config.ApiDocumentUtils.getDocumentRequest;
import static com.meme.ala.core.config.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberControllerTest extends AbstractControllerTest {
    @MockBean
    JwtProvider jwtProvider;

    @DisplayName("사용자 세팅 정보를 읽어오는 테스트")
    @Test
    public void 사용자_세팅_정보를_읽기_유닛테스트() throws Exception{
        Member member= MemberCreator.testMember;
        mockMvc.perform(get("/api/v1/member/me"));
    }
}

