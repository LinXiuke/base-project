package com.zero.common.base.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Description: controller层调用日志拦截器
 * @Author: Hogwarts
 * @Date: 2018/10/16
 */


@Aspect
@Component
public class ControllerInterceptor {

    private final static Logger CONTROLLER_LOGGER = LoggerFactory.getLogger("CONTROLLER_LOGGER");

    @Around("bean(*Controller) && execution(* com.zero.project.web.controller..*.*(..))")
    public Object invoke(ProceedingJoinPoint invocation) throws Throwable {
        long startTime = System.currentTimeMillis();

        String className = invocation.getSignature().getDeclaringType().getSimpleName();
        String method = invocation.getSignature().getName();
        Object result;
        boolean success = true;

        CONTROLLER_LOGGER.info("接口: {}.{}", className, method);

        try {
            result = invocation.proceed();
        } catch (Throwable e) {
            success = false;
            throw  e;
        } finally {
            long time = System.currentTimeMillis() - startTime;
            CONTROLLER_LOGGER.info("结果: {}", success ? "success" : "failed");
            CONTROLLER_LOGGER.info("运行时间: {}豪秒", time);
        }

        return result;
    }
}
