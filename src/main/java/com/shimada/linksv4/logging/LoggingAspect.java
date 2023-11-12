package com.shimada.linksv4.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* com.shimada.linksv4.web.controllers.AuthController.*(..))")
    public void auth() {
    }

    @Pointcut("execution(* com.shimada.linksv4.web.controllers.UserController.*(..))")
    public void user() {
    }

    @Pointcut("execution(* com.shimada.linksv4.web.controllers.LinkController.*(..))")
    public void link() {
    }


    @Around("auth() || user() || link()")
    public Object loggingRegister(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        log.info("Trying to {}", methodName);

        try {
            var result = pjp.proceed();
            log.info("{} success!", methodName);
            return result;
        } catch (Exception e) {
            log.warn("Problem with "+ methodName + "! " + e.getMessage());
            return new ResponseEntity<>("Problem with "+ methodName + "! " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
