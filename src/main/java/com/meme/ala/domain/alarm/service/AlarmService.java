package com.meme.ala.domain.alarm.service;

import com.meme.ala.domain.alarm.model.dto.AlarmDto;
import com.meme.ala.domain.alarm.model.entity.Alarm;
import com.meme.ala.domain.member.model.entity.Member;

import java.util.List;

public interface AlarmService {
    void saveAlarm(Alarm alarm);

    List<AlarmDto> getAlarms(Member member);
}
