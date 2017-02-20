package org.amhzing.clusterview.backend.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class LogExecutionTimeInterceptor {

    @Pointcut("execution(public * *(..))")
    private void anyPublicOperation() {
        // Any public method with or without arguments
    }

    @Around("anyPublicOperation() && @annotation(org.amhzing.clusterview.backend.annotation.LogExecutionTime)")
    public Object logExecutionTaken(final ProceedingJoinPoint joinPoint) throws Throwable
    {
        final String nameOfClass = joinPoint.getTarget().toString();
        final Logger logger = LoggerFactory.getLogger(nameOfClass);

        final String shortDescr = joinPoint.toShortString();
        final String nameOfMethod = joinPoint.getSignature().getName();

        final StopWatch sw = new StopWatch();

        // Start the stopwatch
        sw.start(nameOfMethod);

        // Invoke method
        final Object retVal = joinPoint.proceed();

        // Stop the stopwatch
        sw.stop();

        final Long totalTimeMillis = Long.valueOf(sw.getTotalTimeMillis());

        logger.info("{} took {}ms", new Object[] {shortDescr, totalTimeMillis});

        return retVal;
    }
}
