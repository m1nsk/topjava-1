package ru.javawebinar.topjava.util.annotation;

import java.lang.annotation.*;

@Target(value={ElementType.METHOD,ElementType.FIELD,ElementType.PARAMETER})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface DateFormat {

}
