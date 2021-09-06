package com.meme.ala.domain.event.model.entity;

import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.quest.model.entity.QuestCategory;
import com.meme.ala.domain.quest.model.entity.QuestCondition;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoticeEvent {
    private Member member;
    private QuestCondition condition;
}
