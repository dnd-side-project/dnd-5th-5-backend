package com.meme.ala.domain.event.component;

import com.meme.ala.core.error.ErrorCode;
import com.meme.ala.core.error.exception.EntityNotFoundException;
import com.meme.ala.domain.aggregation.service.AggregationService;
import com.meme.ala.domain.event.model.entity.InitEvent;
import com.meme.ala.domain.friend.service.FriendService;
import com.meme.ala.domain.member.model.entity.Member;
import com.meme.ala.domain.member.repository.MemberRepository;
import com.meme.ala.domain.member.service.MemberCardService;
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
    private final FriendService friendService;
    private final AggregationService aggregationService;
    private final MemberCardService memberCardService;
    private final MemberRepository memberRepository;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void initMember(InitEvent event){
        Member member = memberRepository.findByEmail(event.getEmail())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        aggregationService.initAggregation(member);
        memberCardService.assignCard(member, defaultCardNum);
        friendService.initFriendInfo(member);
    }
}