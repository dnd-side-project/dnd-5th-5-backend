package com.meme.ala.domain.alarm.component;

import com.meme.ala.domain.alarm.model.entity.AlarmCategory;
import com.meme.ala.domain.alarm.model.entity.FriendAlarm;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlarmFactory {
    public static FriendAlarm initFriendFollowerAlarm(ObjectId memberId, ObjectId friendId){
        FriendAlarm followerAlarm = FriendAlarm.builder()
                .memberId(memberId)
                .friendId(friendId)
                .category(AlarmCategory.FRIEND_ALARM)
                .data("님에게 친구 신청을 보냈어요. 친구가 되면 알려드릴게요.")
                .build();

        return followerAlarm;
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
        FriendAlarm friendAlarm = FriendAlarm.builder()
                .memberId(memberId)
                .friendId(friendId)
                .category(AlarmCategory.FRIEND_ALARM)
                .data("님과 친구가 되었어요. 지금 키워드를 선택하러 가볼까요?")
                .build();

        return friendAlarm;
    }


}
