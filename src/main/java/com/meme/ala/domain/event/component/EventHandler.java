package com.meme.ala.domain.event.component;

import com.meme.ala.core.error.ErrorCode;
import com.meme.ala.core.error.exception.EntityNotFoundException;
import com.meme.ala.domain.aggregation.model.entity.Aggregation;
import com.meme.ala.domain.aggregation.model.entity.UserCount;
import com.meme.ala.domain.aggregation.repository.AggregationRepository;
import com.meme.ala.domain.aggregation.repository.UserCountRepository;
import com.meme.ala.domain.aggregation.service.AggregationService;
import com.meme.ala.domain.alarm.component.AlarmFactory;
import com.meme.ala.domain.alarm.model.entity.FriendAlarm;
import com.meme.ala.domain.alarm.repository.AlarmRepository;
import com.meme.ala.domain.alarm.service.AlarmService;
import com.meme.ala.domain.event.model.entity.*;
import com.meme.ala.domain.friend.model.entity.FriendRelation;
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

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Component
public class EventHandler {
    @Value("${member.alacardnum}")
    private int defaultCardNum;
    private final FriendInfoService friendInfoService;
    private final AggregationService aggregationService;
    private final AggregationRepository aggregationRepository;
    private final MemberCardService memberCardService;
    private final MemberRepository memberRepository;
    private final UserCountRepository userCountRepository;
    private final QuestStatusService questStatusService;
    private final QuestConditionService questConditionService;
    private final AlarmService alarmService;

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

        Aggregation deletedAggregation = aggregationService.findByMember(deletedMember);
        aggregationRepository.delete(deletedAggregation);

        List<Member> friendInfoList = friendInfoService.getMemberAllFriendInfo(deletedMember);
        friendInfoList.forEach(member -> friendInfoService.flushFriendInfo(member, deletedMember));
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void questCheck(QuestEvent event) {
        switch (event.getCategory()) {
            case EVALUATION:
                EvaluationQuest quest = questStatusService.updateEvaluation(event);
                questConditionService.checkEvaluation(event.getMember(), quest);
                break;
        }
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publishFriendEvent(FriendEvent event) {

        if(friendInfoService.getRelation(event.getMember1(), event.getMember2()) == FriendRelation.FOLLOWING){

            FriendAlarm followerAlarm = AlarmFactory.initFriendFollowerAlarm(event.getMember1().getId(), event.getMember2().getId());
            FriendAlarm followingAlarm = AlarmFactory.initFriendFollowingAlarm(event.getMember2().getId(), event.getMember1().getId());

           alarmService.saveAllAlarm(Arrays.asList(followerAlarm, followingAlarm));

        } else if(friendInfoService.getRelation(event.getMember1(), event.getMember2()) == FriendRelation.FRIEND) {

            FriendAlarm member1Alarm = AlarmFactory.initFriendAlarm(event.getMember1().getId(), event.getMember2().getId());
            FriendAlarm member2Alarm = AlarmFactory.initFriendAlarm(event.getMember2().getId(), event.getMember1().getId());

            alarmService.saveAllAlarm(Arrays.asList(member1Alarm, member2Alarm));

        }
    }
}