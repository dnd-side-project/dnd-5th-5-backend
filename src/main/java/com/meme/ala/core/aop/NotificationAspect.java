package com.meme.ala.core.aop;


import com.meme.ala.core.annotation.Notification;
import com.meme.ala.core.annotation.QuestCheck;
import com.meme.ala.domain.alarm.model.entity.AlarmCategory;
import com.meme.ala.domain.event.model.entity.FriendEvent;
import com.meme.ala.domain.event.model.entity.QuestEvent;
import com.meme.ala.domain.member.model.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class NotificationAspect implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher eventPublisher;

    @Pointcut("@annotation(notification)")
    public void pointcut(Notification notification) {
    }

    @AfterReturning(pointcut = "pointcut(notification)")
    public void afterReturning(JoinPoint joinPoint, Notification notification){
        Member member = null;

        switch (notification.category()){
            case FRIEND_ALARM:
                Member member1 = (Member) joinPoint.getArgs()[0];
                Member member2 = (Member) joinPoint.getArgs()[1];

                eventPublisher.publishEvent(new FriendEvent(member1, member2));
                break;
            case NOTICE_ALARM:

        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }
}
