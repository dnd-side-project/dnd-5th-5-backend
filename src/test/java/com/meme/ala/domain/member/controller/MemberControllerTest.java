package com.meme.ala.domain.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.repository.MemberRepository;
import com.meme.ala.domain.member.service.MemberService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.HashMap;
import java.util.Map;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
public class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

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
        when(memberService.loginOrJoin(new ObjectMapper().readValue(sampleRequestBody, Map.class)))
                .thenReturn("dummy token");

        mockMvc.perform(post("/api/v1/oauth/jwt/google")
                .contentType(MediaType.APPLICATION_JSON)
                .content(sampleRequestBody))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("dummy token"));
    }
}
