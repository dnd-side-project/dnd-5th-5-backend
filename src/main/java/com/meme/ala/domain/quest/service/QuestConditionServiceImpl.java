package com.meme.ala.domain.quest.service;

import com.meme.ala.domain.aggregation.service.AggregationService;
import com.meme.ala.domain.event.model.entity.NoticeEvent;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.service.MemberCardService;
import com.meme.ala.domain.quest.model.entity.EvaluationQuest;
import com.meme.ala.domain.quest.model.entity.Quest;
import com.meme.ala.domain.quest.model.entity.QuestCategory;
import com.meme.ala.domain.quest.model.entity.QuestCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class QuestConditionServiceImpl implements QuestConditionService{
    private final MemberCardService memberCardService;
    private final AggregationService aggregationService;
    private final ApplicationEventPublisher eventPublisher;


    @Override
    @Transactional
    public void checkEvaluation(Member member, EvaluationQuest quest){
        if(!quest.isAchieved(QuestCondition.EVALUATION))
            return;

        obtainCard(member);

        eventPublisher.publishEvent(new NoticeEvent(member, QuestCondition.EVALUATION));
    }

    private void obtainCard(Member member){
        memberCardService.assignCard(member, 1);
        aggregationService.addAggregation(member);
    }

}
