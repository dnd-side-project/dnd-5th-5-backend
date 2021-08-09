package com.meme.ala.domain.alarm.model.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class FriendAddInfo extends AddInfo {
    private String imgUrl;
    private String nickname;
    private String statusMessage;
}
