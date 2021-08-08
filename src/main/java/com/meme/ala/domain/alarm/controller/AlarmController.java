package com.meme.ala.domain.alarm.controller;

import com.meme.ala.domain.alarm.model.entity.Alarm;
import com.meme.ala.domain.alarm.model.entity.AlarmCategory;
import com.meme.ala.domain.alarm.model.entity.FriendAlarm;
import com.meme.ala.domain.alarm.model.entity.NoticeAlarm;
import com.meme.ala.domain.alarm.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/api/v1/alarm")
@RestController
@RequiredArgsConstructor
public class AlarmController {
    private final AlarmRepository alarmRepository;

    //TODO: 테스트 코드는 지울 예정
    @GetMapping("/test")
    public void saveTest() {
        Alarm friendAlarm = FriendAlarm.builder().friendId(new ObjectId())
                .memberId(new ObjectId())
                .data("testFriendData")
                .category(AlarmCategory.FRIEND_ALARM)
                .build();

        Alarm noticeAlarm = NoticeAlarm.builder().redirectUrl("http://testurl.com")
                .memberId(new ObjectId())
                .data("testNoticeData")
                .category(AlarmCategory.NOTICE_ALARM)
                .build();
        alarmRepository.save(friendAlarm);
        alarmRepository.save(noticeAlarm);
    }
}
