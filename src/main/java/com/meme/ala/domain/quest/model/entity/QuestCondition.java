package com.meme.ala.domain.quest.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum QuestCondition {
    EVALUATION(30)
    ;

    private int condition;

    QuestCondition(final int condition) {
        this.condition = condition;
    }

    public int getCondition() {
        return condition;
    }
}
