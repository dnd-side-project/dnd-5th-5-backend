package com.meme.ala.domain.alacard.controller;

import com.meme.ala.common.AbstractControllerTest;
import com.meme.ala.common.DtoFactory;
import com.meme.ala.common.EntityFactory;
import com.meme.ala.common.message.ResponseMessage;
import com.meme.ala.core.config.AlaWithAccount;
import com.meme.ala.domain.aggregation.model.entity.Aggregation;
import com.meme.ala.domain.aggregation.service.AggregationService;
import com.meme.ala.domain.alacard.model.dto.response.AlaCardDto;
import com.meme.ala.domain.alacard.model.dto.response.AlaCardSettingDto;
import com.meme.ala.domain.alacard.model.dto.response.BackgroundDtoInSetting;
import com.meme.ala.domain.alacard.model.entity.cardSetting.Background;
import com.meme.ala.domain.alacard.service.AlaCardService;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.service.MemberCardService;
import com.meme.ala.domain.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static com.meme.ala.core.config.ApiDocumentUtils.getDocumentRequest;
import static com.meme.ala.core.config.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AlaCardControllerTest extends AbstractControllerTest {
    @Value("${cloud.aws.s3.bucket.url}")
    private String s3Url;

    @MockBean
    private AlaCardService alaCardService;
    @MockBean
    private AggregationService aggregationService;
    @MockBean
    private MemberCardService memberCardService;
    @MockBean
    private MemberService memberService;

    private Member testMember = EntityFactory.testMember();

    @DisplayName("?????? ????????? ?????? ???????????? ???????????? ?????????")
    @Test
    public void ??????_?????????_??????_????????????_????????????_?????????() throws Exception {

        given(memberCardService.getWordList(any(String.class))).willReturn(Arrays.asList(DtoFactory.testSelectionWordDto()));

        mockMvc.perform(get("/api/v1/alacard/wordlist")
                .param("nickname", testMember.getMemberSetting().getNickname())
                .param("offset", "0")
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("api/v1/alacard/wordlist/get",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("nickname").description("?????????"),
                                parameterWithName("offset").description("?????????")
                        ),
                        responseFields(
                                fieldWithPath("status").description("?????? ??????"),
                                fieldWithPath("message").description("??????"),
                                fieldWithPath("data").description("????????? ??????"),
                                fieldWithPath("data[*].id").description("?????? ?????? id"),
                                fieldWithPath("data[*].hint").description("??????"),
                                fieldWithPath("data[*].wordName").description("??????"),
                                fieldWithPath("timestamp").description("???????????????")
                        )
                ));
    }

    @DisplayName("???????????? ?????? ???????????? ?????? ???????????? ???????????? ?????????")
    @Test
    public void ????????????_??????_????????????_??????_????????????_????????????_?????????() throws Exception {
        Member member = EntityFactory.testMember();
        List<AlaCardDto> alaCardDtoList = new LinkedList<>(Arrays.asList(DtoFactory.testAlaCardDto()));

        given(alaCardService.getAlaCardDtoList(any(Member.class))).willReturn(alaCardDtoList);
        given(memberService.findByNickname(any(String.class))).willReturn(member);

        mockMvc.perform(get("/api/v1/alacard/alacardlist")
                .param("nickname", EntityFactory.testMember().getMemberSetting().getNickname())
        )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].sentence").value("testSentence"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].selectedWordList[0].wordName").value("testWord"))
                .andDo(print())
                .andDo(document("api/v1/alacard/alacardlist",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("nickname").description("?????? ?????? ?????????")
                        ),
                        responseFields(
                                fieldWithPath("status").description("?????? ??????"),
                                fieldWithPath("message").description("??????"),
                                fieldWithPath("data").description("???????????? ?????????????????? ?????? ?????????"),
                                fieldWithPath("data[*].sentence").description("??????"),
                                fieldWithPath("data[*].isCompleted").description("?????? ??????"),
                                fieldWithPath("data[*].selectedWordList").description("???????????? ????????? ???????????????"),
                                fieldWithPath("data[*].selectedWordList[*].wordName").description("???????????????"),
                                fieldWithPath("data[*].selectedWordList[*].count").description("?????????????????????"),
                                fieldWithPath("data[*].alaCardSettingDto").description("???????????? ????????????"),
                                fieldWithPath("data[*].alaCardSettingDto.alaCardId").description("??????????????? Id"),
                                fieldWithPath("data[*].alaCardSettingDto.backgroundImgUrl").description("?????? ?????????"),
                                fieldWithPath("data[*].alaCardSettingDto.fontColor").description("?????? ???"),
                                fieldWithPath("data[*].alaCardSettingDto.isOpen").description("?????? ?????? ??????"),
                                fieldWithPath("timestamp").description("???????????????")
                        )
                ));
    }

    @DisplayName("???????????? ?????? ???????????? ???????????? ?????????")
    @Test
    public void ????????????_??????_????????????_????????????_?????????() throws Exception {
        String sampleRequestBody =
                "{\n" +
                        "  \"idList\": [\n" +
                        "    \"6rKM7J6ELeuwsO2LgOq3uOudvOyatOuTnC3rsLDqt7gg7ZSM66CI7J20Le2UhOuhnOq4sOygiOufrA==\",\n" +
                        "    \"7Jyg66i4LeyghOyDnS3soITsg50tMTTshLjquLAg7ZSE656R7IqkIOq3gOyhsQ==\",\n" +
                        "    \"6rKM7J6ELeyYpOuyhOybjOy5mC3slrTsmrjrpqzripQg7LGU7ZS87Ja4Le2VnOyhsA==\"\n" +
                        "  ]\n" +
                        "}";

        doNothing().when(aggregationService).submitWordList(any(Aggregation.class), any(List.class));
        given(memberService.findByNickname(any(String.class))).willReturn(EntityFactory.testMember());
        given(aggregationService.findByMember(any(Member.class))).willReturn(EntityFactory.testAggregation());

        mockMvc.perform(patch("/api/v1/alacard/wordlist")
                .param("nickname", testMember.getMemberSetting().getNickname())
                .contentType(MediaType.APPLICATION_JSON)
                .content(sampleRequestBody))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(ResponseMessage.SUBMITTED))
                .andDo(print())
                .andDo(document("api/v1/alacard/wordlist/patch",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("nickname").description("?????? ?????? ?????????")
                        ),
                        requestFields(
                                fieldWithPath("idList").description("????????? ????????? id???")
                        ),
                        responseFields(
                                fieldWithPath("status").description("?????? ??????"),
                                fieldWithPath("message").description("??????"),
                                fieldWithPath("data").description("????????? ?????? ????????? ????????? ?????? ?????????"),
                                fieldWithPath("timestamp").description("???????????????")
                        )
                ));
    }

    @DisplayName("?????? ???????????? ???????????? ?????????")
    @Test
    public void ??????_????????????_????????????_?????????() throws Exception {
        given(alaCardService.getBackgroundImageUrls()).willReturn(Arrays.asList(s3Url + "/static/test.svg", s3Url + "/static/test2.svg"));

        mockMvc.perform(get("/api/v1/alacard/backgrounds"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0]").value(s3Url + "/static/test.svg"))
                .andDo(print())
                .andDo(document("api/v1/alacard/backgrounds",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("status").description("?????? ??????"),
                                fieldWithPath("message").description("??????"),
                                fieldWithPath("data").description("????????? URL"),
                                fieldWithPath("timestamp").description("???????????????")
                        )
                ));
    }

    @DisplayName("?????? ???????????? ??????????????? ???????????? ?????????")
    @Test
    public void ??????_????????????_???????????????_????????????_?????????() throws Exception {
        Background background = EntityFactory.testBackground();

        background.setImgUrl(s3Url + "/static/test.svg");

        BackgroundDtoInSetting dto = BackgroundDtoInSetting.builder()
                .thumbnailImgUrl(background.getThumbnailImgUrl())
                .backgroundImgUrl(background.getImgUrl())
                .fontColor(background.getFontColor())
                .build();

        Map<String, List<BackgroundDtoInSetting>> sampleMap = new HashMap<>();
        sampleMap.put("Gradient", Arrays.asList(dto));
        sampleMap.put("Photo", Arrays.asList(dto));
        sampleMap.put("Solid", Arrays.asList(dto));

        given(alaCardService.getBackgroundThumbCategory()).willReturn(sampleMap);

        mockMvc.perform(get("/api/v1/alacard/background"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.Gradient[0].fontColor").value(background.getFontColor()))
                .andDo(print())
                .andDo(document("api/v1/alacard/background",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("status").description("?????? ??????"),
                                fieldWithPath("message").description("??????"),
                                fieldWithPath("data").description("?????????"),
                                fieldWithPath("data.Gradient[*].thumbnailImgUrl").description("Gradient ????????? ????????? URL"),
                                fieldWithPath("data.Gradient[*].backgroundImgUrl").description("Gradient ????????? URL"),
                                fieldWithPath("data.Gradient[*].fontColor").description("Gradient ?????? ??????"),
                                fieldWithPath("data.Photo[*].thumbnailImgUrl").description("Photo ????????? ????????? URL"),
                                fieldWithPath("data.Photo[*].backgroundImgUrl").description("Photo ????????? URL"),
                                fieldWithPath("data.Photo[*].fontColor").description("Photo ?????? ??????"),
                                fieldWithPath("data.Solid[*].thumbnailImgUrl").description("Solid ????????? ????????? URL"),
                                fieldWithPath("data.Solid[*].backgroundImgUrl").description("Solid ????????? URL"),
                                fieldWithPath("data.Solid[*].fontColor").description("Solid ?????? ??????"),
                                fieldWithPath("timestamp").description("???????????????")
                        )
                ));
    }

    @AlaWithAccount("test@gmail.com")
    @DisplayName("???????????? ?????? ????????? ???????????? ?????????")
    @Test
    public void ????????????_??????_?????????_????????????_?????????() throws Exception {
        String sampleRequestBody =
                "{\n" +
                        "  \"alaCardId\": \"610034b5a221f126a4e7f500\",\n" +
                        "  \"backgroundImgUrl\": \"https://s3.ap-northeast-2.amazonaws.com/meme-ala-background/static/BG+Gradient+04.png\",\n" +
                        "  \"fontColor\": \"#FF0000F\",\n" +
                        "  \"isOpen\": false\n" +
                        "}";

        doNothing().when(memberCardService).saveSetting(any(Member.class), any(AlaCardSettingDto.class));

        mockMvc.perform(patch("/api/v1/alacard/alacardsetting")
                .contentType(MediaType.APPLICATION_JSON)
                .content(sampleRequestBody))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("api/v1/alacard/alacardsetting",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("alaCardId").description("????????? ??????????????? ObjectID - Essential"),
                                fieldWithPath("backgroundImgUrl").description("?????? ????????? - Optional"),
                                fieldWithPath("fontColor").description("?????? ?????? - Optional"),
                                fieldWithPath("isOpen").description("?????? ?????? - Optional")
                        ),
                        responseFields(
                                fieldWithPath("status").description("?????? ??????"),
                                fieldWithPath("message").description("??????"),
                                fieldWithPath("timestamp").description("???????????????")
                        )
                ));
    }
}
