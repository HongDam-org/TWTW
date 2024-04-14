package com.twtw.backend.aspect;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Slf4j
@Aspect
@Component
public class LogIntroduction {
    private static final String LOG_FORMAT = "METHOD : {}";

    @Pointcut("execution(* com.twtw.backend..*Controller*.*(..))")
    public void allController() {}

    @Pointcut("execution(* com.twtw.backend..*Service*.*(..))")
    public void allService() {}

    @Pointcut("execution(* com.twtw.backend..*Repository*.*(..))")
    public void allRepository() {}

    @Pointcut("execution(* com.twtw.backend..*Client*.*(..))")
    public void allClient() {}

    @Pointcut("execution(* com.twtw.backend..*Producer*.*(..))")
    public void allProducer() {}

    @Pointcut("execution(* com.twtw.backend..*Consumer*.*(..))")
    public void allConsumer() {}

    @Before("allController()")
    public void controllerLog(final JoinPoint joinPoint) {
        logging(joinPoint, log::info);
    }

    @Before("allService() || allRepository()")
    public void serviceAndRepositoryLog(final JoinPoint joinPoint) {
        logging(joinPoint, log::debug);
    }

    @Before("allClient() || allProducer() || allConsumer()")
    public void externalSystemLog(final JoinPoint joinPoint) {
        logging(joinPoint, log::debug);
    }

    private void logging(final JoinPoint joinPoint, final BiConsumer<String, String> consumer) {
        consumer.accept(LOG_FORMAT, joinPoint.getSignature().toShortString());
    }
}
