package com.meme.ala.domain.event.model.entity;

import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.quest.model.entity.QuestCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuestEvent {
    private QuestCategory category;
    private Member member;
}
