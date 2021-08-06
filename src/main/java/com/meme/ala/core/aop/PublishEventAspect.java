package com.meme.ala.core.aop;

import com.meme.ala.domain.event.model.entity.InitEvent;
import com.meme.ala.domain.event.model.entity.SubmitEvent;
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
public class PublishEventAspect implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher eventPublisher;

    @Pointcut("@annotation(com.meme.ala.core.annotation.PublishEvent)")
    public void eventPointcut() {
    }

    @AfterReturning(pointcut = "eventPointcut()", returning = "returnObj")
    public void afterReturning(JoinPoint joinPoint, Object returnObj){
        String method = joinPoint.getSignature().getName();
        log.info(method + "가 실행됩니다.");
        if(method.equals("join")){
            InitEvent event = new InitEvent((String) returnObj);
            eventPublisher.publishEvent(event);
        }
        else if(method.equals("submitWordList")){
            SubmitEvent event = new SubmitEvent();
            eventPublisher.publishEvent(event);
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }
}
