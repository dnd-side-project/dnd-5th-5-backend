package com.meme.ala.domain.alarm.component;

import com.meme.ala.domain.alarm.model.entity.AlarmCategory;
import com.meme.ala.domain.alarm.model.entity.FriendAlarm;
import com.meme.ala.domain.alarm.model.entity.NoticeAlarm;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.quest.model.entity.QuestCategory;
import com.meme.ala.domain.quest.model.entity.QuestCondition;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlarmFactory {
    public static FriendAlarm initFriendFollowerAlarm(ObjectId memberId, ObjectId friendId){

        return FriendAlarm.builder()
                .memberId(memberId)
                .friendId(friendId)
                .category(AlarmCategory.FRIEND_ALARM)
                .data("님에게 친구 신청을 보냈어요. 친구가 되면 알려드릴게요.")
                .build();
    }

    public static FriendAlarm initFriendFollowingAlarm(ObjectId memberId, ObjectId friendId){
        FriendAlarm followingAlarm = FriendAlarm.builder()
                .memberId(memberId)
                .friendId(friendId)
                .category(AlarmCategory.FRIEND_ALARM)
                .data("님이 친구 신청을 보냈어요.")
                .build();

        return followingAlarm;
    }

    public static FriendAlarm initFriendAlarm(ObjectId memberId, ObjectId friendId){

        return FriendAlarm.builder()
                .memberId(memberId)
                .friendId(friendId)
                .category(AlarmCategory.FRIEND_ALARM)
                .data("님과 친구가 되었어요. 지금 키워드를 선택하러 가볼까요?")
                .build();
    }

    public static NoticeAlarm initNoticeAlarm(ObjectId memberId, QuestCondition condition){

        return NoticeAlarm.builder()
                .memberId(memberId)
                .category(AlarmCategory.NOTICE_ALARM)
                .data(condition.getInfo() + " 달성 완료! 알라카드 한 장을 드릴게요!")
                .build();
    }

    public static NoticeAlarm initMemberSignUpAlarm(Member member){
        return NoticeAlarm.builder()
                .memberId(member.getId())
                .category(AlarmCategory.NOTICE_ALARM)
                .data(member.getMemberSetting().getNickname() + "님 안녕하세요! 알라 서비스 사용법을 보러 가실래요?")
                .redirectUrl("https://dnd-5.notion.site/c6c7e1c536f145b2b3643951212e715a")
                .build();
    }

}
