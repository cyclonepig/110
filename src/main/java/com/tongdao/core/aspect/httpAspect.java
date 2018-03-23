//package com.tongdao.core.aspect;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * Created by jing on 2017/9/22.
// */
//@Aspect
//@Component
//public class httpAspect {
//
//    private final static Logger logger = LoggerFactory.getLogger(httpAspect.class);
//
//    @Pointcut("execution(public * com.tongdao.base.controller.*.*(..))")
//    public void log(){
//    }
//
//    @Before("log()")
//    public void doBefore(JoinPoint join){
//
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request=attributes.getRequest();
//
//        //url
//       logger.info("url={}",request.getRequestURI());
//
//       //请求类型
//        logger.info("type={}",request.getMethod());
//
//        //ip
//        logger.info("ip={}",request.getRemoteAddr());
//
//        //类方法
//        logger.info("class_method={}",join.getSignature().getDeclaringTypeName()+"."+join.getSignature().getName());
//
//        //参数
//        logger.info("args={}",join.getArgs());
//    }
//    @After("log()")
//    public void doAfter(JoinPoint joinPoint){
//        //类方法
//        logger.info("class_method={}",joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName()+"   执行完毕.");
//    }
//
//    @AfterReturning(returning = "object",pointcut = "log()")
//    public void retuenObj(Object object){
//        logger.info("respose={}",object.toString());
//    }
//}
