package com.meme.ala.common;

import com.meme.ala.domain.aggregation.model.dto.response.WordCountDto;
import com.meme.ala.domain.alacard.model.dto.request.AlaCardSaveDto;
import com.meme.ala.domain.alacard.model.dto.request.MiddleCategoryDto;
import com.meme.ala.domain.alacard.model.dto.request.SentenceComponentDto;
import com.meme.ala.domain.alacard.model.dto.request.WordDto;
import com.meme.ala.domain.alacard.model.dto.response.AlaCardDto;
import com.meme.ala.domain.alacard.model.dto.response.AlaCardSettingDto;
import com.meme.ala.domain.alacard.model.dto.response.SelectionWordDto;
import com.meme.ala.domain.alarm.model.dto.AlarmDto;
import com.meme.ala.domain.alarm.model.dto.FriendAddInfo;
import com.meme.ala.domain.alarm.model.dto.NoticeAddInfo;
import com.meme.ala.domain.alarm.model.entity.AlarmCategory;
import com.meme.ala.domain.friend.model.dto.FriendDto;
import com.meme.ala.domain.member.model.dto.MemberPrincipalDto;
import com.meme.ala.domain.member.model.entity.Member;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class DtoFactory {
    public static MemberPrincipalDto testMemberPrincipalDto() {
        return MemberPrincipalDto.builder()
                .email("test@gmail.com")
                .nickname("testNickname")
                .statusMessage("너 자신을 ala")
                .imgUrl("https://user-images.githubusercontent.com/46064193/126342816-399b6cfa-869c-485f-b676-a303af41f2ec.png")
                .isOpen(true)
                .build();
    }

    public static AlaCardSaveDto alaCardSaveDto() {
        List<WordDto> wordDtoList = Arrays.asList(
                new WordDto("공부"),
                new WordDto("수영"),
                new WordDto("테스트")
        );
        List<MiddleCategoryDto> middleCategoryDtoList = Arrays.asList(
                MiddleCategoryDto.builder()
                        .middleCategoryName("testMiddle")
                        .hint("testHint")
                        .prefix("???는 ")
                        .form("???하는 것을 좋아")
                        .sentenceComponent(SentenceComponentDto.builder().eomi("한다").josa("하고, ").build())
                        .wordList(wordDtoList).build()
        );
        return AlaCardSaveDto.builder()
                .bigCategory("test")
                .middleCategoryList(middleCategoryDtoList)
                .build();
    }

    public static FriendDto testMemberFriendDto() {
        return FriendDto.builder()
                .nickname("testNickname")
                .statusMessage("너 자신을 ala")
                .imgUrl("https://user-images.githubusercontent.com/46064193/126342816-399b6cfa-869c-485f-b676-a303af41f2ec.png")
                .build();
    }

    public static WordCountDto testWordCountDto() {
        return WordCountDto.builder()
                .count(10)
                .wordName("testWord")
                .build();
    }

    public static AlaCardDto testAlaCardDto() {
        return AlaCardDto.builder()
                .selectedWordList(Arrays.asList(DtoFactory.testWordCountDto()))
                .sentence("testSentence")
                .alaCardSettingDto(testAlaCardSettingDto())
                .build();
    }

    public static AlaCardSettingDto testAlaCardSettingDto() {
        return AlaCardSettingDto.builder()
                .backgroundImgUrl("http://test/background.jpg")
                .fontColor("#FFFFFF")
                .isOpen(true)
                .build();
    }

    public static SelectionWordDto testSelectionWordDto() {
        String bigCategory = "test";
        String middleCategory = "testMiddle";
        String wordName = "testWord";
        String hint = "testHint";
        String id = Base64.getEncoder().encodeToString((bigCategory + "-" + middleCategory + "-" + hint + "-" + wordName).getBytes());
        return SelectionWordDto.builder()
                .wordName("testWord")
                .hint("testHint")
                .id(id)
                .build();
    }

    public static AlarmDto testFriendAlarmDto() {
        Member member = EntityFactory.testMember();
        FriendAddInfo friendAddInfo = FriendAddInfo.builder()
                .imgUrl(member.getMemberSetting().getImgUrl())
                .nickname(member.getMemberSetting().getNickname())
                .statusMessage(member.getMemberSetting().getStatusMessage())
                .build();
        return AlarmDto.builder()
                .string("testFriendData")
                .category(AlarmCategory.FRIEND_ALARM.name())
                .addInfo(friendAddInfo)
                .createdAt(LocalDateTime.of(2021, 1, 1, 18, 18, 50))
                .build();
    }

    public static AlarmDto testNoticeAlarmDto() {
        NoticeAddInfo noticeAddInfo = NoticeAddInfo.builder()
                .redirectUrl("http://testurl.com")
                .build();
        return AlarmDto.builder()
                .string("testNoticeData")
                .category(AlarmCategory.NOTICE_ALARM.name())
                .addInfo(noticeAddInfo)
                .createdAt(LocalDateTime.of(2021, 1, 2, 18, 18, 18, 50))
                .build();
    }
}
