package edu.epam.mentoring.task7.postProcessor;

import edu.epam.mentoring.task7.annotation.Logging;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by eugen on 27.09.2016.
 */
public class LoggingAnnotationBeanPostProcessor implements BeanPostProcessor {
    private static Logger logger = Logger.getLogger(LoggingAnnotationBeanPostProcessor.class);

    private Map<String, List<String>> targetBeans = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class clazz = bean.getClass();
        List<String> annotatedMethodNames = classHasLoggingAnnotatedMethods(clazz);
        if (!annotatedMethodNames.isEmpty()) {
            targetBeans.put(beanName, annotatedMethodNames);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        List<String> annotatedMethodNames = targetBeans.get(beanName);
        if (annotatedMethodNames != null) {
            Class beanClass = bean.getClass();
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), (proxy, method, args) -> {
                String classNameAndMethodName = beanClass.getSimpleName() + "." + method.getName();
                logger.info(classNameAndMethodName + "(\"" + args[0] + "\") start;");
                Object value = method.invoke(bean, args);
                logger.info(classNameAndMethodName + " finish" + (value == null ? "" : " with result = " + value) + ";");
                return value;
            });
        }
        return bean;
    }

    private List<String> classHasLoggingAnnotatedMethods(Class clazz) {
        List<String> annotatedMethodNames = new ArrayList<>();
        for (Method method : clazz.getMethods()) {
            if (method.getAnnotation(Logging.class) != null) {
                annotatedMethodNames.add(method.getName());
            }
        }
        return annotatedMethodNames;
    }
}