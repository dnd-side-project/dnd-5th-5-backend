package com.meme.ala.core.aop;

import com.meme.ala.core.annotation.QuestCheck;
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
public class QuestAspect implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher eventPublisher;

    @Pointcut("@annotation(questCheck)")
    public void pointcut(QuestCheck questCheck) {
    }

    @AfterReturning(pointcut = "pointcut(questCheck)", returning = "returnObj")
    public void afterReturning(JoinPoint joinPoint, QuestCheck questCheck, Object returnObj){
        Member member = null;

        for(Object arg : joinPoint.getArgs()){
            if(arg instanceof Member) {
                member = (Member) arg;
            }
        }

        eventPublisher.publishEvent(new QuestEvent(questCheck.category(), member));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }
}
