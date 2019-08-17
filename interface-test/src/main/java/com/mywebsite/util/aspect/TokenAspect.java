package com.mywebsite.util.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TokenAspect {

    @Pointcut("@annotation(com.mywebsite.util.annotion.GetUserInfo) ")
    public void getUserInfo(){}

    @Around("getUserInfo()")
    public Object Interceptor(ProceedingJoinPoint pjp) throws Throwable{
        System.out.println("定义切面！！！！！！！！！！！！！！！！！！！！！！！");
        return pjp.proceed();
    }
}
