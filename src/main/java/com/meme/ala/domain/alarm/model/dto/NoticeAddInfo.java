package com.meme.ala.domain.alarm.model.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class NoticeAddInfo extends AddInfo {
    private String redirectUrl;
}
