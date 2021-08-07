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
@TypeAlias("FriendAlarm")
public class FriendAlarm extends Alarm {
    private ObjectId friendId;

    @Builder
    public FriendAlarm(ObjectId id, ObjectId memberId, String data, LocalDateTime createdAt, LocalDateTime expireAt, AlarmCategory category, ObjectId friendId) {
        super(id, memberId, data, createdAt, expireAt, category);
        this.friendId = friendId;
    }
}
