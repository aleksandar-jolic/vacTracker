package dev.jola.VacTracker.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class AOP {

    @Before("execution(* dev.jola.VacTracker.controller.*.*(..))")
    public void before(JoinPoint joinPoint) {

        System.out.println("\n Pokrenuta je metoda " + joinPoint.getSignature().getName());
        System.out.println("Arguments: " + Arrays.toString(joinPoint.getArgs()) + "\n");

    }

    @AfterReturning(pointcut ="execution(* dev.jola.VacTracker.controller.*.*(..))", returning = "retVal")
    public void afterReturn(Object retVal) {

        System.out.println("Return value: "+retVal);

    }
}
