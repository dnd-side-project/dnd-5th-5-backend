package com.meme.ala.domain.aggregation.controller;

import com.meme.ala.common.AbstractControllerTest;
import com.meme.ala.domain.aggregation.service.AggregationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.meme.ala.core.config.ApiDocumentUtils.getDocumentRequest;
import static com.meme.ala.core.config.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AggregationControllerTest extends AbstractControllerTest {
    @MockBean
    AggregationService aggregationService;

    @DisplayName("사용자의 수를 제공하는 테스트")
    @Test
    public void 사용자의_수를_제공하는_테스트() throws Exception {
        given(aggregationService.getUserCount()).willReturn(5);
        mockMvc.perform(get("/api/v1/aggregation/usercount"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(5))
                .andDo(print())
                .andDo(document("api/v1/aggregation/usercount",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("설명"),
                                fieldWithPath("data").description("선택된 카드"),
                                fieldWithPath("timestamp").description("타임스탬프")
                        )
                ));
    }
}