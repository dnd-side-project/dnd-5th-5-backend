package com.meme.ala.domain.quest.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum QuestCondition {
    EVALUATION(30, "인기쟁이(친구들의 평가횟수 30번)"),
    FRIEND(3, "시작은 설레(3명의 친구 맺기)"),

    ;

    private int condition;
    private String info;

    QuestCondition(final int condition, String info) {
        this.condition = condition;
        this.info = info;
    }

    public int getCondition() {
        return condition;
    }

    public String getInfo() {
        return info;
    }
}
