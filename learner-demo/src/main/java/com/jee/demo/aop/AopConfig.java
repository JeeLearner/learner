package com.jee.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/5/19
 * @Version:v1.0
 */
@Configuration
@Aspect
public class AopConfig {

    @Around("@within(org.springframework.stereotype.Controller)")
    public Object simpleAop(final ProceedingJoinPoint pjp) throws Throwable {
        try {
            Object[] args = pjp.getArgs();
            System.out.println("args:" + Arrays.asList(args));
            //调用原有方法
            Object o = pjp.proceed();
            System.out.println("return:" + o);
            return o;
        } catch (Throwable e){
            throw e;
        }
    }

    /**
     * @Pointcut("execution(public * *(..))")  所有public方法
     * @Pointcut("execution(* set*(..))")  所有set开头的方法
     * @Pointcut("execution(public set*(..))") 所有set开头的public方法
     * @Pointcut("execution(public com.xxx.service.* set*(..))")   所有set开头的public方法,并且在com.service包下
     * @Pointcut("target(com.xxx.ICommonService)") 所有实现了ICommonService接口的类的方法
     * @Pointcut("target(org.springframework.transaction.annotation.Transactional)")  所有用@Transactional注解的方法
     * @Pointcut("@within(org.springframework.stereotype.Controller)")  类型声明了@Controller的所有方法
     * @Pointcut("@annotation(com.jee.web.aop.SysLog)")  注解类
     * target: 目标类
     * @within 类上注解
     */
    @Pointcut("@annotation(com.jee.demo.aop.SysLog)")
    public void testPointCut(){

    }

    @Around("testPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        //具体操作
        return null;
    }


}

