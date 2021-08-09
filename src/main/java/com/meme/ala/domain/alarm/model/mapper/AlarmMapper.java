package com.meme.ala.domain.alarm.model.mapper;

import com.meme.ala.domain.alarm.model.dto.AlarmDto;
import com.meme.ala.domain.alarm.model.dto.FriendAddInfo;
import com.meme.ala.domain.alarm.model.dto.NoticeAddInfo;
import com.meme.ala.domain.alarm.model.entity.FriendAlarm;
import com.meme.ala.domain.alarm.model.entity.NoticeAlarm;
import com.meme.ala.domain.member.model.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AlarmMapper {

    default AlarmDto friendAlarmToDto(FriendAlarm friendAlarm, Member friend) {
        FriendAddInfo friendAddInfo = FriendAddInfo.builder()
                .imgUrl(friend.getMemberSetting().getImgUrl())
                .nickname(friend.getMemberSetting().getNickname())
                .statusMessage(friend.getMemberSetting().getStatusMessage())
                .build();
        return AlarmDto.builder()
                .addInfo(friendAddInfo)
                .category(friendAlarm.getCategory().name())
                .string(friendAlarm.getData())
                .createdAt(friendAlarm.getCreatedAt())
                .build();
    }

    default AlarmDto noticeAlarmToDto(NoticeAlarm noticeAlarm) {
        NoticeAddInfo noticeAddInfo = NoticeAddInfo.builder()
                .redirectUrl(noticeAlarm.getRedirectUrl())
                .build();
        return AlarmDto.builder()
                .addInfo(noticeAddInfo)
                .category(noticeAlarm.getCategory().name())
                .string(noticeAlarm.getData())
                .createdAt(noticeAlarm.getCreatedAt())
                .build();
    }

}