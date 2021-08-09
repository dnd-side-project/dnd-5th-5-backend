package com.meme.ala.domain.alarm.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AlarmDto {
    private String string;

    private LocalDateTime createdAt;

    private String category;

    private AddInfo addInfo;
}
