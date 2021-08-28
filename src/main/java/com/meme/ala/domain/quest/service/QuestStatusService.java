package com.meme.ala.domain.quest.service;

import com.meme.ala.domain.event.model.entity.QuestEvent;
import com.meme.ala.domain.quest.model.entity.EvaluationQuest;

public interface QuestStatusService {
    EvaluationQuest updateEvaluation(QuestEvent event);
}
