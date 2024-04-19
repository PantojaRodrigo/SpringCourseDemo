package com.luv2code.springboot.thymeleafdemo.aspect;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Aspect
@Component
public class LoggingAspect {

    private Logger myLogger = Logger.getLogger(getClass().getName());

    @Pointcut("execution(* com.luv2code.springboot.thymeleafdemo.controller.*.*(..))")
    public void isControllerPackage(){}

    @Pointcut("execution(* com.luv2code.springboot.thymeleafdemo.service.*.*(..))")
    public void isServicePackage(){}

    @Pointcut("execution(* com.luv2code.springboot.thymeleafdemo.deo.*.*(..))")
    public void isDaoPackage(){}

    @Pointcut("isControllerPackage() || isServicePackage() || isDaoPackage()")
    public void allPackages(){}

    @Before("allPackages()")
    public void before(JoinPoint joinPoint){
        String method = joinPoint.getSignature().toShortString();
        myLogger.info("=====>>> in @Before : calling method : "+method);

        Object[]  args = joinPoint.getArgs();

        for(Object temp : args){
            myLogger.info("=====>> argument : "+temp);
        }
    }

    @AfterReturning(pointcut = "allPackages()", returning = "result")
    public void after(JoinPoint joinPoint, Object result){
        String method = joinPoint.getSignature().toShortString();
        myLogger.info("=====>>> in @AfterReturning : from method : "+method);

        if(result instanceof List<?>){
            ((List<Employee>) result).get(0).setFirstName("EL HECHICEROOOO!!");
        }
            myLogger.info("=====>> result : "+result);

    }
}
