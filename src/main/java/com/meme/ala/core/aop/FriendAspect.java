package com.meme.ala.core.aop;

import com.meme.ala.core.annotation.FlushFriendAlarm;
import com.meme.ala.domain.alarm.service.AlarmService;
import com.meme.ala.domain.member.model.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@Aspect
public class FriendAspect {
    private final AlarmService alarmService;

    @Pointcut("@annotation(flushFriendAlarm)")
    public void pointcut(FlushFriendAlarm flushFriendAlarm) {
    }

    @AfterReturning(pointcut = "pointcut(flushFriendAlarm)", argNames = "joinPoint,flushFriendAlarm")
    public void afterReturning(JoinPoint joinPoint, FlushFriendAlarm flushFriendAlarm) {
        Member member1 = (Member) joinPoint.getArgs()[0];
        Member member2 = (Member) joinPoint.getArgs()[1];
        alarmService.deleteAlarm(member1.getId(), member2.getId());
        alarmService.deleteAlarm(member2.getId(), member1.getId());
    }
}

