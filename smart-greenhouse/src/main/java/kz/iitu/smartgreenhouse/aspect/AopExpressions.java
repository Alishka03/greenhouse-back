package kz.iitu.smartgreenhouse.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AopExpressions {
    @Pointcut("@annotation(kz.iitu.smartgreenhouse.aspect.annotations.LoggingAspect)")
    public void pointcutLogging() {
    }
}