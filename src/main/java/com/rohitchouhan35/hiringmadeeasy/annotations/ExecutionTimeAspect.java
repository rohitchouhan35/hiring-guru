package com.rohitchouhan35.hiringmadeeasy.annotations;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeAspect {

    @Around("@annotation(MeasureExecutionTime)")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        String border = "===============================================";
        String timestamp = java.time.LocalDateTime.now().toString();
        String message = "\n" +
                "╭───────────────────────────────────────────────╮\n" +
                "│                Execution Time                  │\n" +
                "├───────────────────────────────────────────────┤\n" +
                "│ Timestamp: " + timestamp + "                │\n" +
                "│ Method: " + joinPoint.getSignature().toShortString() + " │\n" +
                "│ Execution Time: " + executionTime + " ms            │\n" +
                "╰───────────────────────────────────────────────╯" + " \n";

        System.out.println(border);
        System.out.println(message);
        System.out.println(border);

        return result;
    }
}
