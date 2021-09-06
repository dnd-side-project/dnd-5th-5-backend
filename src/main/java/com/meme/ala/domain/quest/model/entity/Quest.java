package com.meme.ala.domain.quest.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@SuperBuilder
@NoArgsConstructor
@Document(collection = "QUEST")
public abstract class Quest {
    @Id
    private ObjectId id;

    private ObjectId memberId;

    private QuestCategory category;

    public abstract boolean isAchieved(QuestCondition condition);
}
