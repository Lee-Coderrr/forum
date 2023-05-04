package com.example.testproject.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class PerformanceAspect {
    @Pointcut("@annotation(com.example.testproject.annotation.RunningTime)")
    private void enableRunningTime() {
    }

    @Pointcut("execution(* com.example.testproject..*.*(..))")
    private void cut() {
    }

    @Around("cut() && enableRunningTime()")
    public void loggingRunningTime(ProceedingJoinPoint joinPoint) throws Throwable {
        // 메소드 수행 전
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // 메소드 수행
        joinPoint.proceed();
        // 메소드 종료 후
        stopWatch.stop();
        String methodName = joinPoint.getSignature()
                .getName();
        log.info("{}의 총 수행 시간 => {} sec", methodName, stopWatch.getTotalTimeSeconds());
    }
}