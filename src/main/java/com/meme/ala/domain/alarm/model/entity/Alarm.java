package com.meme.ala.domain.alarm.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@Document(collection = "ALARM")
public class Alarm {
    @Id
    private ObjectId id;

    private ObjectId memberId;

    private String data;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    @Indexed(expireAfterSeconds = 60)
    private LocalDateTime expireAt = LocalDateTime.now().plusSeconds(60);

    private AlarmCategory category;
}
