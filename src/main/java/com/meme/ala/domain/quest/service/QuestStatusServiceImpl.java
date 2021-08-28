package com.meme.ala.domain.quest.service;

import com.meme.ala.core.error.exception.EntityNotFoundException;
import com.meme.ala.domain.event.model.entity.QuestEvent;
import com.meme.ala.domain.quest.model.entity.EvaluationQuest;
import com.meme.ala.domain.quest.model.entity.QuestCategory;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class QuestStatusServiceImpl implements QuestStatusService {
    private final QuestService questService;

    @Transactional
    public EvaluationQuest updateEvaluation(QuestEvent event){
        ObjectId memberId = event.getMember().getId();

        EvaluationQuest quest = null;

        try {
            quest = (EvaluationQuest) questService.findByMemberIdAndCategory(memberId, QuestCategory.EVALUATION);
        } catch (EntityNotFoundException e){
            quest = EvaluationQuest.builder().memberId(memberId).category(QuestCategory.EVALUATION).build();
        }

        quest.setStatus(quest.getStatus() + 1);

        return (EvaluationQuest) questService.save(quest);
    }
}
