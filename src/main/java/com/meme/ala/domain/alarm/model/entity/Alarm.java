package com.meme.ala.domain.alarm.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Document(collection = "ALARM")
public class Alarm {
    @Id
    private ObjectId id;

    private ObjectId memberId;

    private String data;

    private LocalDateTime createdAt;

    private LocalDateTime expireAt;

    private AlarmCategory category;
}
