package com.meme.ala.domain.alarm.model.mapper;

import com.meme.ala.domain.alarm.model.dto.AlarmDto;
import com.meme.ala.domain.alarm.model.entity.FriendAlarm;
import com.meme.ala.domain.alarm.model.entity.NoticeAlarm;
import com.meme.ala.domain.member.model.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.HashMap;
import java.util.Map;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AlarmMapper {

    default AlarmDto friendAlarmToDto(FriendAlarm friendAlarm, Member friend) {
        Map<String, String> addInfoMap = new HashMap<>();
        addInfoMap.put("nickname", friend.getMemberSetting().getNickname());
        addInfoMap.put("imgUrl", friend.getMemberSetting().getImgUrl());
        addInfoMap.put("statusMessage", friend.getMemberSetting().getStatusMessage());
        return AlarmDto.builder()
                .addInfo(addInfoMap)
                .category(friendAlarm.getCategory().name())
                .string(friendAlarm.getData())
                .createdAt(friendAlarm.getCreatedAt())
                .build();
    }

    default AlarmDto noticeAlarmToDto(NoticeAlarm noticeAlarm) {
        Map<String, String> addInfoMap = new HashMap<>();
        addInfoMap.put("redirectUrl", noticeAlarm.getRedirectUrl());
        return AlarmDto.builder()
                .addInfo(addInfoMap)
                .category(noticeAlarm.getCategory().name())
                .string(noticeAlarm.getData())
                .createdAt(noticeAlarm.getCreatedAt())
                .build();
    }

}