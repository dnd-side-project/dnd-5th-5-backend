package com.meme.ala.domain.alacard.controller;

import com.meme.ala.common.AbstractControllerTest;
import com.meme.ala.common.EntityFactory;
import com.meme.ala.core.config.AlaWithAccount;
import com.meme.ala.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static com.meme.ala.core.config.ApiDocumentUtils.getDocumentRequest;
import static com.meme.ala.core.config.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AlaCardControllerTest extends AbstractControllerTest {
    @MockBean
    private MemberRepository memberRepository;

    @AlaWithAccount("test@gmail.com")
    @DisplayName("알라 카드의 단어 리스트를 제공하는 테스트")
    @Test
    public void 알라_카드의_단어_리스트를_제공하는_테스트() throws Exception{
        when(memberRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(EntityFactory.testMember()));

        mockMvc.perform(get("/api/v1/alacard/wordlist"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].bigCategory").value("test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].middleCategory").value("testMiddle"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].middleCategory").value("공부"))
                .andDo(print())
                .andDo(document("api/v1/alacard/wordlist",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("설명"),
                                fieldWithPath("data").description("선택된 카드"),
                                fieldWithPath("data[*].bigCategory").description("대분류"),
                                fieldWithPath("data[*].middleCategory").description("중분류"),
                                fieldWithPath("data[*].hint").description("힌트"),
                                fieldWithPath("data[*].wordName").description("단어")
                        )
                ));
    }
}
