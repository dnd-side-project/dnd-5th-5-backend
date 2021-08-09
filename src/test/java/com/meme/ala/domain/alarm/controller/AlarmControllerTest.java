package com.meme.ala.domain.alarm.controller;

import com.meme.ala.common.AbstractControllerTest;
import com.meme.ala.common.DtoFactory;
import com.meme.ala.common.EntityFactory;
import com.meme.ala.core.config.AlaWithAccount;
import com.meme.ala.domain.alarm.service.AlarmService;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.service.MemberService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;

import static com.meme.ala.core.config.ApiDocumentUtils.getDocumentRequest;
import static com.meme.ala.core.config.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AlarmControllerTest extends AbstractControllerTest {
    @MockBean
    private MemberService memberService;
    @MockBean
    private AlarmService alarmService;

    @AlaWithAccount("test@gmail.com")
    @DisplayName("알람을 읽어오는 테스트")
    @Test
    public void 사용자_알람_읽기_테스트() throws Exception {
        given(memberService.findByMemberId(any(ObjectId.class)))
                .willReturn(EntityFactory.testMember());
        given(alarmService.getAlarms(any(Member.class)))
                .willReturn(Arrays.asList(DtoFactory.testNoticeAlarmDto(),
                        DtoFactory.testFriendAlarmDto()));

        mockMvc.perform(get("/api/v1/alarm"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("api/v1/alarm",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("설명"),
                                fieldWithPath("data").description("사용자의 알람을 시간 순서대로"),
                                fieldWithPath("data[*].string").description("알람 문자열"),
                                fieldWithPath("data[*].category").description("알람 종류"),
                                fieldWithPath("data[*].createdAt").description("알람 생성 시간"),
                                fieldWithPath("data[*].addInfo").description("부가적인 알람 정보 Map"),
                                fieldWithPath("data[*].addInfo.redirectUrl").description("[공지]리다이렉션 URL").optional(),
                                fieldWithPath("data[*].addInfo.imgUrl").description("[친구]친구의 사진").optional(),
                                fieldWithPath("data[*].addInfo.nickname").description("[친구]친구의 닉네임").optional(),
                                fieldWithPath("data[*].addInfo.statusMessage").description("[친구]친구의 상태메시지").optional(),
                                fieldWithPath("timestamp").description("타임스탬프")
                        )
                ));
    }
}
