package com.twtw.backend.aspect;

import com.twtw.backend.global.lock.DistributedLock;
import com.twtw.backend.utils.SpringELParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class DistributedLockAspect {
    private static final String REDISSON_LOCK_PREFIX = "LOCK:";
    private static final String INTERRUPTED_EXCEPTION = "INTERRUPTED EXCEPTION";
    private static final String UNLOCK_EXCEPTION = "UNLOCK EXCEPTION";

    private final RedissonClient redissonClient;
    private final TransactionAspect transactionAspect;
    private final SpringELParser springELParser;

    @Around("@annotation(com.twtw.backend.global.lock.DistributedLock)")
    public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);

        String key =
                REDISSON_LOCK_PREFIX
                        + springELParser.getDynamicValue(
                        signature.getParameterNames(),
                        joinPoint.getArgs(),
                        distributedLock.name());
        RLock rLock = redissonClient.getLock(key);

        try {
            boolean available =
                    rLock.tryLock(
                            distributedLock.waitTime(),
                            distributedLock.leaseTime(),
                            distributedLock.timeUnit());
            if (!available) {
                return false;
            }
            return transactionAspect.proceed(joinPoint);
        } catch (final InterruptedException e) {
            log.error(INTERRUPTED_EXCEPTION, e);
            throw e;
        } finally {
            try {
                rLock.unlock();
            } catch (final Exception e) {
                log.error(UNLOCK_EXCEPTION, e);
            }
        }
    }
}
