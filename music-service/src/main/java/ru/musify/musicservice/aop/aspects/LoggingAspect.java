package ru.musify.musicservice.aop.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.musify.musicservice.handler.exception.LoggingException;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

  @Around("@within(ru.musify.musicservice.aop.Loggable) || @annotation(ru.musify.musicservice.aop.Loggable)")
  public Object loggingAdvice(ProceedingJoinPoint joinPoint) {
    String method = joinPoint.getSignature().toShortString();
    Object[] args = joinPoint.getArgs();

    log.info("{} start with arguments {}", method, args);
    try {
      Object result = joinPoint.proceed();
      log.info("{} finish with result [{}]", method, result);
      return result;
    } catch (Throwable e) {
      log.error("{} is failed with arguments [{}]", method, args);
      throw new LoggingException(e.getMessage());
    }
  }
}