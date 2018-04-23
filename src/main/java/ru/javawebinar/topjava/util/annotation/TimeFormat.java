package ru.javawebinar.topjava.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.METHOD,ElementType.FIELD,ElementType.PARAMETER})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface TimeFormat {

}
