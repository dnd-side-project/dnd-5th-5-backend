package com.meme.ala.domain.quest.service;

import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.quest.model.entity.EvaluationQuest;

public interface QuestConditionService {
    void checkEvaluation(Member member, EvaluationQuest quest);
}
