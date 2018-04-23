package ru.javawebinar.topjava.util.annotation;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class TimeFormatAnnotationFormatterFactory
        implements AnnotationFormatterFactory<TimeFormat> 
{

    @Override
    public Set<Class<?>> getFieldTypes() {
        Set<Class<?>> setTypes = new HashSet<>();
        setTypes.add(LocalTime.class);
        return setTypes;
    }

    @Override
    public Parser<?> getParser(TimeFormat annotation, Class<?> fieldType) {
        return new TimeFormatter();
    }

    @Override
    public Printer<?> getPrinter(TimeFormat annotation, Class<?> fieldType) {
        return new TimeFormatter();
    }
}
