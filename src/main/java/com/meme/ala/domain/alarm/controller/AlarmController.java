package com.meme.ala.domain.alarm.controller;

import com.meme.ala.common.annotation.CurrentUser;
import com.meme.ala.common.dto.ResponseDto;
import com.meme.ala.common.message.ResponseMessage;
import com.meme.ala.domain.alacard.model.dto.response.AlaCardDto;
import com.meme.ala.domain.alarm.model.dto.AlarmDto;
import com.meme.ala.domain.alarm.model.entity.Alarm;
import com.meme.ala.domain.alarm.model.entity.AlarmCategory;
import com.meme.ala.domain.alarm.model.entity.FriendAlarm;
import com.meme.ala.domain.alarm.model.entity.NoticeAlarm;
import com.meme.ala.domain.alarm.service.AlarmService;
import com.meme.ala.domain.member.model.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/api/v1/alarm")
@RestController
@RequiredArgsConstructor
public class AlarmController {
    private final AlarmService alarmService;

    //TODO: 테스트 코드는 지울 예정
    @GetMapping("/test")
    public void saveTest(@CurrentUser Member member) {
        Alarm friendAlarm = FriendAlarm.builder().friendId(member.getId())
                .memberId(member.getId())
                .data("testFriendData")
                .category(AlarmCategory.FRIEND_ALARM)
                .build();

        Alarm noticeAlarm = NoticeAlarm.builder().redirectUrl("http://testurl.com")
                .memberId(member.getId())
                .data("testNoticeData")
                .category(AlarmCategory.NOTICE_ALARM)
                .build();
        alarmService.saveAlarm(friendAlarm);
        alarmService.saveAlarm(noticeAlarm);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<AlarmDto>>> getAlarms(@CurrentUser Member member) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.SUCCESS, alarmService.getAlarms(member)));
    }
}