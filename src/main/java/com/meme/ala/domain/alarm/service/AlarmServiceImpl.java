package com.meme.ala.domain.alarm.service;

import com.meme.ala.core.error.ErrorCode;
import com.meme.ala.core.error.exception.EntityNotFoundException;
import com.meme.ala.domain.alarm.model.dto.AlarmDto;
import com.meme.ala.domain.alarm.model.entity.Alarm;
import com.meme.ala.domain.alarm.model.entity.FriendAlarm;
import com.meme.ala.domain.alarm.model.entity.NoticeAlarm;
import com.meme.ala.domain.alarm.model.mapper.AlarmMapper;
import com.meme.ala.domain.alarm.repository.AlarmRepository;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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
                try {
                    Member friend = memberService.findByMemberId(friendAlarm.getFriendId());
                    return alarmMapper.friendAlarmToDto(friendAlarm, friend);
                } catch (EntityNotFoundException e){
                    // Todo: 삭제된 id 이므로 해당 알람 삭제하기
                    log.error("삭제된 ID: ", friendAlarm.getFriendId());
                }
            case NOTICE_ALARM:
                NoticeAlarm noticeAlarm = (NoticeAlarm) alarm;
                return alarmMapper.noticeAlarmToDto(noticeAlarm);
        }
        return AlarmDto.builder().build();
    }

    @Override
    @Transactional(readOnly = true)
    public void deleteAlarm(ObjectId memberId, ObjectId friendId){
        FriendAlarm alarm = alarmRepository.findAlarmByMemberIdAndFriendId(memberId, friendId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        alarmRepository.delete(alarm);
    }
}
