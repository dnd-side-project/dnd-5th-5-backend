package com.meme.ala.core.aop;

import com.meme.ala.core.annotation.FlushFriendAlarm;
import com.meme.ala.domain.alarm.service.AlarmService;
import com.meme.ala.domain.friend.model.entity.FriendInfo;
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
        FriendInfo friendInfo1 = (FriendInfo) joinPoint.getArgs()[0];
        FriendInfo friendInfo2 = (FriendInfo) joinPoint.getArgs()[1];
        alarmService.deleteAlarm(friendInfo1.getMemberId(), friendInfo2.getMemberId());
        alarmService.deleteAlarm(friendInfo2.getMemberId(), friendInfo1.getMemberId());
    }
}

