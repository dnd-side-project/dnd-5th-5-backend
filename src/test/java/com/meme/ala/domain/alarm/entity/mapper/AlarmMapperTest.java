package com.meme.ala.domain.alarm.entity.mapper;

import com.meme.ala.common.DtoFactory;
import com.meme.ala.common.EntityFactory;
import com.meme.ala.domain.alarm.model.dto.AlarmDto;
import com.meme.ala.domain.alarm.model.entity.FriendAlarm;
import com.meme.ala.domain.alarm.model.entity.NoticeAlarm;
import com.meme.ala.domain.alarm.model.mapper.AlarmMapper;
import com.meme.ala.domain.member.model.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

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
        assertEquals(mappedDto.getCategory(), friendAlarmDto.getCategory());
        assertEquals(mappedDto.getString(), friendAlarmDto.getString());
        for (Map.Entry<String, String> entry : friendAlarmDto.getAddInfo().entrySet()) {
            String key = entry.getKey();
            String val = entry.getValue();
            assertEquals(mappedDto.getAddInfo().get(key), val);
        }
    }

    @Test
    void 공지알람_엔티티에서_DTO변환_테스트() {
        AlarmDto mappedDto = alarmMapper.noticeAlarmToDto(noticeAlarm);
        assertEquals(mappedDto.getCategory(), noticeAlarmDto.getCategory());
        assertEquals(mappedDto.getString(), noticeAlarmDto.getString());
        for (Map.Entry<String, String> entry : noticeAlarmDto.getAddInfo().entrySet()) {
            String key = entry.getKey();
            String val = entry.getValue();
            assertEquals(mappedDto.getAddInfo().get(key), val);
        }
    }
}
