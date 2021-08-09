package com.meme.ala.domain.alarm.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Builder
public class AlarmDto {
    private String string;

    private LocalDateTime createdAt;

    private String category;

    private Map<String, String> addInfo;
}
