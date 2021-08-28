package com.meme.ala.domain.event.component;

import com.meme.ala.core.error.ErrorCode;
import com.meme.ala.core.error.exception.EntityNotFoundException;
import com.meme.ala.domain.aggregation.model.entity.UserCount;
import com.meme.ala.domain.aggregation.repository.UserCountRepository;
import com.meme.ala.domain.aggregation.service.AggregationService;
import com.meme.ala.domain.event.model.entity.DeleteEvent;
import com.meme.ala.domain.event.model.entity.InitEvent;
import com.meme.ala.domain.event.model.entity.QuestEvent;
import com.meme.ala.domain.event.model.entity.SubmitEvent;
import com.meme.ala.domain.friend.service.FriendInfoService;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.repository.MemberRepository;
import com.meme.ala.domain.member.service.MemberCardService;
import com.meme.ala.domain.quest.model.entity.EvaluationQuest;
import com.meme.ala.domain.quest.service.QuestConditionService;
import com.meme.ala.domain.quest.service.QuestStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class EventHandler {
    @Value("${member.alacardnum}")
    private int defaultCardNum;
    private final FriendInfoService friendInfoService;
    private final AggregationService aggregationService;
    private final MemberCardService memberCardService;
    private final MemberRepository memberRepository;
    private final UserCountRepository userCountRepository;
    private final QuestStatusService questStatusService;
    private final QuestConditionService questConditionService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void initMember(InitEvent event) {
        Member member = memberRepository.findByProviderIdAndMemberSetting_IsDeleted(event.getProviderId(), false)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        memberCardService.assignCard(member, defaultCardNum);
        aggregationService.initAggregation(member);
        friendInfoService.initFriendInfo(member);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void countUser(SubmitEvent event) {
        if (userCountRepository.count() == 0) {
            userCountRepository.save(UserCount.builder().count(1).build());
        } else {
            UserCount userCount = userCountRepository.findAll().get(0);
            userCount.setCount(userCount.getCount() + 1);
            userCountRepository.save(userCount);
        }
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void deleteMember(DeleteEvent event) {
        Member deletedMember = event.getDeletedMember();
        /**
         * Todo
         * 삭제 관련 로직 처리
         * 1.친구관계
         */
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void questCheck(QuestEvent event){
        switch(event.getCategory()){
            case EVALUATION:
                EvaluationQuest quest = questStatusService.updateEvaluation(event);
                questConditionService.checkEvaluation(event.getMember(), quest);
                break;
        }
    }
}