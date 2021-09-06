package com.meme.ala.common;

import com.meme.ala.domain.aggregation.model.entity.Aggregation;
import com.meme.ala.domain.aggregation.model.entity.WordCount;
import com.meme.ala.domain.alacard.model.entity.AlaCard;
import com.meme.ala.domain.alacard.model.entity.MiddleCategory;
import com.meme.ala.domain.alacard.model.entity.SentenceComponent;
import com.meme.ala.domain.alacard.model.entity.Word;
import com.meme.ala.domain.alacard.model.entity.cardSetting.AlaCardSetting;
import com.meme.ala.domain.alacard.model.entity.cardSetting.Background;
import com.meme.ala.domain.alarm.model.entity.AlarmCategory;
import com.meme.ala.domain.alarm.model.entity.FriendAlarm;
import com.meme.ala.domain.alarm.model.entity.NoticeAlarm;
import com.meme.ala.domain.friend.model.entity.FriendInfo;
import com.meme.ala.domain.member.model.entity.AlaCardSettingPair;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.model.entity.MemberSetting;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class EntityFactory {
    public static String testObjectId() {

        return "00000000000000000000000";
    }

    public static Member testMember() {
        List<AlaCardSettingPair> alaCardSettingPairList = new LinkedList<>();
        alaCardSettingPairList.add(AlaCardSettingPair.builder()
                .alaCard(EntityFactory.testAlaCard())
                .alaCardSetting(EntityFactory.testAlaCardSetting())
                .build());
        return Member.builder()
                .id(new ObjectId("60f3f89c9f21ff292724eb38"))
                .email("test@gmail.com")
                .providerId("12312345648")
                .memberSetting(MemberSetting.builder()
                        .nickname("testNickname")
                        .imgUrl("https://user-images.githubusercontent.com/46064193/126342816-399b6cfa-869c-485f-b676-a303af41f2ec.png")
                        .isAlarmed(true)
                        .isDeleted(false)
                        .isOpen(true)
                        .statusMessage("너 자신을 ala")
                        .build()
                )
                .alaCardSettingPairList(alaCardSettingPairList)
                .build();
    }

    public static AlaCard testAlaCard() {
        return AlaCard.builder()
                .id(new ObjectId())
                .bigCategory("test")
                .middleCategoryItem(MiddleCategory.builder()
                        .middleCategoryName("testMiddle")
                        .hint("testHint")
                        .prefix("???는 ")
                        .form("???을/를 하는 것을 좋아")
                        .sentenceComponent(SentenceComponent.builder().eomi("한다").josa("하고, ").build())
                        .wordItem(Word.builder().wordName("공부").build())
                        .wordItem(Word.builder().wordName("수영").build())
                        .wordItem(Word.builder().wordName("테스트").build()).build()
                )
                .middleCategoryItem(MiddleCategory.builder()
                        .middleCategoryName("testMiddle")
                        .hint("testHint")
                        .prefix("???는 ")
                        .form("??? 하는 것을 좋아")
                        .sentenceComponent(SentenceComponent.builder().eomi("한다").josa("하고, ").build())
                        .wordItem(Word.builder().wordName("공부").build())
                        .wordItem(Word.builder().wordName("수영").build())
                        .wordItem(Word.builder().wordName("테스트").build()).build())
                .build();
    }

    public static AlaCardSetting testAlaCardSetting() {
        return AlaCardSetting.builder()
                .background(Background.builder()
                        .fontColor("#FFFFFF")
                        .imgUrl("http://test/background.jpg")
                        .category("solid")
                        .build()
                )
                .isOpen(true)
                .build();
    }

    public static FriendInfo testFriendInfo() {
        List<ObjectId> friends = new LinkedList<>(Arrays.asList(new ObjectId(testObjectId() + "2"), new ObjectId(testObjectId() + "3")));
        List<ObjectId> pendings = new LinkedList<>(Arrays.asList(new ObjectId(testObjectId() + "4"), new ObjectId(testObjectId() + "5")));

        return FriendInfo.builder()
                .memberId(new ObjectId(testObjectId() + "1"))
                .friends(friends)
                .myAcceptancePendingList(pendings)
                .build();
    }

    public static WordCount testWordCount() {
        return WordCount.builder()
                .word(Word.builder().wordName("testWord")
                        .build())
                .middleCategoryName("testMiddle")
                .count(10)
                .build();
    }

    public static Aggregation testAggregation() {
        return Aggregation.builder()
                .wordCountList(Arrays.asList(EntityFactory.testWordCount()))
                .build();
    }

    public static FriendAlarm testFriendAlarm(){
        return FriendAlarm.builder()
                .friendId(testMember().getId())
                .category(AlarmCategory.FRIEND_ALARM)
                .memberId(testMember().getId())
                .createdAt(LocalDateTime.of(2021,1,1,18,18,50))
                .data("testFriendData")
                .build();
    }

    public static NoticeAlarm testNoticeAlarm(){
        return NoticeAlarm.builder().redirectUrl("http://testurl.com")
                .memberId(testMember().getId())
                .data("testNoticeData")
                .createdAt(LocalDateTime.of(2021,1,2,18,18,50))
                .category(AlarmCategory.NOTICE_ALARM)
                .build();
    }

    public static Background testBackground() {
        return Background.builder()
                .fontColor("FFEEFF")
                .build();
    }
}
