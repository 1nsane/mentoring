package edu.epam.mentoring.task7.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by eugen on 28.09.2016.
 */
@Aspect
public class LoggingAspect {
    private static Logger logger = Logger.getLogger(LoggingAspect.class);

    @Pointcut("@annotation(edu.epam.mentoring.task7.annotation.Logging)")
    public void methodsToBeProfiled(){}

    @Around("methodsToBeProfiled()")
    public Object logAnnotatedMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodArg = (String)joinPoint.getArgs()[0];
        String classNameAndMethodName = joinPoint.getTarget().getClass().getSimpleName()
                + "." + joinPoint.getSignature().getName();
        logger.info(classNameAndMethodName + "(\"" + methodArg + "\") start;");
        Object value = joinPoint.proceed();
        logger.info(classNameAndMethodName + " finish" + (value == null ? "" : " with result = " + value) + ";");
        return value;
    }
}
