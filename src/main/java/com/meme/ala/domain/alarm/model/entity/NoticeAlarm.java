package com.meme.ala.domain.alarm.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "ALARM")
@TypeAlias("NoticeAlarm")
public class NoticeAlarm extends Alarm {
    private String redirectUrl;

    @Builder
    public NoticeAlarm(ObjectId id, ObjectId memberId, String data, LocalDateTime createdAt, LocalDateTime expireAt, AlarmCategory category, String redirectUrl) {
        super(id, memberId, data, createdAt, expireAt, category);
        this.redirectUrl = redirectUrl;
    }
}
