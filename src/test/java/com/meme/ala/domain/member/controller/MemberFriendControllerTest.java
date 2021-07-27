package com.meme.ala.domain.member.controller;

import com.meme.ala.common.AbstractControllerTest;
import com.meme.ala.common.EntityFactory;
import com.meme.ala.core.config.AlaWithAccount;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.service.MemberFriendService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static com.meme.ala.common.message.ResponseMessage.READ_MEMBER_FRIENDS;
import static com.meme.ala.core.config.ApiDocumentUtils.getDocumentRequest;
import static com.meme.ala.core.config.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberFriendControllerTest extends AbstractControllerTest {

    @MockBean
    private MemberFriendService memberFriendService;

    @AlaWithAccount("test@gmail.com")
    @Test
    public void 사용자_친구_목록_조회_테스트() throws Exception{

        List<Member> friends = Arrays.asList(EntityFactory.testMember());

        given(memberFriendService.getMemberFriend(any(Member.class))).willReturn(friends);

        mockMvc.perform(get("/api/v1/member/me/friends"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(READ_MEMBER_FRIENDS))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].nickname").value("testNickname"))
                .andDo(print())
                .andDo(document("api/v1/member/me/friends",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("설명"),
                                fieldWithPath("data[*].nickname").description("친구 닉네임"),
                                fieldWithPath("data[*].statusMessage").description("친구 상태메시지"),
                                fieldWithPath("data[*].imgUrl").description("친구 이미지 사진 URL"),
                                fieldWithPath("timestamp").description("타임스탬프")
                        )
                ));
    }
}
