package com.meme.ala.domain.alarm.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@SuperBuilder
@Document(collection = "ALARM")
@TypeAlias("FriendAlarm")
public class FriendAlarm extends Alarm {
    private ObjectId friendId;
}
