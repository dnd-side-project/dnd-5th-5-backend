package com.meme.ala.domain.friend.controller;

import com.meme.ala.common.AbstractControllerTest;
import com.meme.ala.common.EntityFactory;
import com.meme.ala.core.config.AlaWithAccount;
import com.meme.ala.domain.friend.model.entity.FriendRelation;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.friend.service.FriendInfoService;
import com.meme.ala.domain.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static com.meme.ala.common.message.ResponseMessage.*;
import static com.meme.ala.core.config.ApiDocumentUtils.getDocumentRequest;
import static com.meme.ala.core.config.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FriendControllerTest extends AbstractControllerTest {

    @MockBean
    private FriendInfoService friendInfoService;

    @MockBean
    private MemberService memberService;

    @AlaWithAccount("test@gmail.com")
    @Test
    public void 사용자_친구_목록_조회_테스트() throws Exception{

        List<Member> friends = Arrays.asList(EntityFactory.testMember());

        given(memberService.findByNickname(any(String.class))).willReturn(EntityFactory.testMember());
        given(friendInfoService.getMemberFriend(any(Member.class))).willReturn(friends);

        mockMvc.perform(get("/api/v1/friend"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(READ_MEMBER_FRIENDS))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].nickname").value("testNickname"))
                .andDo(print())
                .andDo(document("api/v1/friend",
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

    @AlaWithAccount("test@gmail.com")
    @Test
    public void 사용자와_상대방_관계_조회() throws Exception{

        given(memberService.findByNickname(any(String.class))).willReturn(EntityFactory.testMember());
        given(friendInfoService.getRelation(any(Member.class), any(Member.class))).willReturn(FriendRelation.FRIEND);

        mockMvc.perform(get("/api/v1/friend/relation/{nickname}", "testNickname"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(READ_MEMBER_AND_PERSON_RELATION))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.nickname").value("testNickname"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.relation").value(FriendRelation.FRIEND.getKrRelation()))
                .andDo(print())
                .andDo(document("api/v1/friend/relation/nickname",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("nickname").description("친구 닉네임")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("설명"),
                                fieldWithPath("data.nickname").description("상대방 닉네임"),
                                fieldWithPath("data.relation").description("상대방과의 관계(일반, 팔로잉, 팔로워, 친구)"),
                                fieldWithPath("timestamp").description("타임스탬프")
                        )
                ));
    }

    @AlaWithAccount("test@gmail.com")
    @Test
    public void 사용자_친구_추가_테스트() throws Exception{

        given(memberService.findByNickname(any(String.class))).willReturn(EntityFactory.testMember());

        mockMvc.perform(patch("/api/v1/friend/{nickname}", "testNickname"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(FOLLOWED))
                .andDo(print())
                .andDo(document("api/v1/friend/nickname",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("nickname").description("친구 닉네임")
                        )
                ));
    }

    @AlaWithAccount("test@gmail.com")
    @Test
    public void 사용자_친구_수락_테스트() throws Exception{

        given(memberService.findByNickname(any(String.class))).willReturn(EntityFactory.testMember());

        mockMvc.perform(patch("/api/v1/friend/accept/{nickname}", "testNickname"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ACCEPTED))
                .andDo(print())
                .andDo(document("api/v1/friend/accept/nickname",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("nickname").description("친구 닉네임")
                        )
                ));
    }

    @AlaWithAccount("test@gmail.com")
    @Test
    public void 사용자_친구_거절_테스트() throws Exception{

        given(memberService.findByNickname(any(String.class))).willReturn(EntityFactory.testMember());

        mockMvc.perform(patch("/api/v1/friend/decline/{nickname}", "testNickname"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(DECLINED))
                .andDo(print())
                .andDo(document("api/v1/friend/decline/nickname",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("nickname").description("친구 닉네임")
                        )
                ));
    }

    @AlaWithAccount("test@gmail.com")
    @Test
    public void 사용자_팔로잉_취소_테스트() throws Exception{

        given(memberService.findByNickname(any(String.class))).willReturn(EntityFactory.testMember());

        mockMvc.perform(patch("/api/v1/friend/cancel/{nickname}", "testNickname"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(CANCELED))
                .andDo(print())
                .andDo(document("api/v1/friend/cancel/nickname",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("nickname").description("친구 닉네임")
                        )
                ));
    }

    @AlaWithAccount("test@gmail.com")
    @Test
    public void 사용자_친구_삭제_테스트() throws Exception {
        mockMvc.perform(delete("/api/v1/friend/{nickname}", "testNickname"))
                .andExpect(status().isNoContent())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(DELETED))
                .andDo(print())
                .andDo(document("api/v1/friend/nickname/delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("nickname").description("친구 닉네임")
                        )
                ));
    }
}
