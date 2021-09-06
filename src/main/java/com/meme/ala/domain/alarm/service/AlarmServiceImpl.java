package com.meme.ala.domain.alarm.service;

import com.meme.ala.domain.alarm.model.dto.AlarmDto;
import com.meme.ala.domain.alarm.model.entity.Alarm;
import com.meme.ala.domain.alarm.model.entity.FriendAlarm;
import com.meme.ala.domain.alarm.model.entity.NoticeAlarm;
import com.meme.ala.domain.alarm.model.mapper.AlarmMapper;
import com.meme.ala.domain.alarm.repository.AlarmRepository;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AlarmServiceImpl implements AlarmService {
    private final MemberService memberService;
    private final AlarmRepository alarmRepository;
    private final AlarmMapper alarmMapper;

    @Override
    @Transactional
    public void saveAlarm(Alarm alarm) {
        alarmRepository.save(alarm);
    }

    @Override
    @Transactional
    public void saveAllAlarm(List<Alarm> alarmList) {
        alarmRepository.saveAll(alarmList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlarmDto> getAlarms(Member member) {
        return alarmRepository.findAllByMemberIdOrderByCreatedAtDesc(member.getId())
                .stream().map(this::transformAlarm).collect(Collectors.toList());
    }

    private AlarmDto transformAlarm(Alarm alarm) {
        switch (alarm.getCategory()) {
            case FRIEND_ALARM:
                FriendAlarm friendAlarm = (FriendAlarm) alarm;
                Member friend = memberService.findByMemberId(friendAlarm.getFriendId());
                return alarmMapper.friendAlarmToDto(friendAlarm, friend);
            case NOTICE_ALARM:
                NoticeAlarm noticeAlarm = (NoticeAlarm) alarm;
                return alarmMapper.noticeAlarmToDto(noticeAlarm);
        }
        return AlarmDto.builder().build();
    }
}
