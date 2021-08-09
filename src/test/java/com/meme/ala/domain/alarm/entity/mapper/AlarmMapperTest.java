package com.meme.ala.domain.alarm.entity.mapper;

import com.meme.ala.common.DtoFactory;
import com.meme.ala.common.EntityFactory;
import com.meme.ala.domain.alarm.model.dto.AlarmDto;
import com.meme.ala.domain.alarm.model.dto.FriendAddInfo;
import com.meme.ala.domain.alarm.model.dto.NoticeAddInfo;
import com.meme.ala.domain.alarm.model.entity.FriendAlarm;
import com.meme.ala.domain.alarm.model.entity.NoticeAlarm;
import com.meme.ala.domain.alarm.model.mapper.AlarmMapper;
import com.meme.ala.domain.member.model.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AlarmMapperTest {

    @Autowired
    private AlarmMapper alarmMapper;

    private final FriendAlarm friendAlarm = EntityFactory.testFriendAlarm();
    private final NoticeAlarm noticeAlarm = EntityFactory.testNoticeAlarm();
    private final AlarmDto friendAlarmDto = DtoFactory.testFriendAlarmDto();
    private final AlarmDto noticeAlarmDto = DtoFactory.testNoticeAlarmDto();
    private final Member friend = EntityFactory.testMember();

    @Test
    void 친구알람_엔티티에서_DTO변환_테스트() {
        AlarmDto mappedDto = alarmMapper.friendAlarmToDto(friendAlarm, friend);
        FriendAddInfo mappedFriendAddInfo = (FriendAddInfo) mappedDto.getAddInfo();
        FriendAddInfo friendAddInfo = (FriendAddInfo) friendAlarmDto.getAddInfo();

        assertEquals(mappedDto.getCategory(), friendAlarmDto.getCategory());
        assertEquals(mappedDto.getString(), friendAlarmDto.getString());
        assertEquals(mappedFriendAddInfo.getImgUrl(), friendAddInfo.getImgUrl());
        assertEquals(mappedFriendAddInfo.getNickname(), friendAddInfo.getNickname());
        assertEquals(mappedFriendAddInfo.getStatusMessage(), friendAddInfo.getStatusMessage());
    }

    @Test
    void 공지알람_엔티티에서_DTO변환_테스트() {
        AlarmDto mappedDto = alarmMapper.noticeAlarmToDto(noticeAlarm);
        NoticeAddInfo mappedNoticeAddInfo = (NoticeAddInfo) mappedDto.getAddInfo();
        NoticeAddInfo noticeAddInfo = (NoticeAddInfo) noticeAlarmDto.getAddInfo();

        assertEquals(mappedDto.getCategory(), noticeAlarmDto.getCategory());
        assertEquals(mappedDto.getString(), noticeAlarmDto.getString());
        assertEquals(mappedNoticeAddInfo.getRedirectUrl(), noticeAddInfo.getRedirectUrl());
    }
}