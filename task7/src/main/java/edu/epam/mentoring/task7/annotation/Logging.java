package edu.epam.mentoring.task7.annotation;

import java.lang.annotation.*;

/**
 * Created by eugen on 26.09.2016.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Logging {
    Level value() default Level.DEBUG;
}
