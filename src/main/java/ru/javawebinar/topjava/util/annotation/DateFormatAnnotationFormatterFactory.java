package ru.javawebinar.topjava.util.annotation;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class DateFormatAnnotationFormatterFactory
        implements AnnotationFormatterFactory<DateFormat>
{

    @Override
    public Set<Class<?>> getFieldTypes() {
        Set<Class<?>> setTypes = new HashSet<>();
        setTypes.add(LocalDate.class);
        return setTypes;
    }

    @Override
    public Parser<?> getParser(DateFormat annotation, Class<?> fieldType) {
        return new DateFormatter();
    }

    @Override
    public Printer<?> getPrinter(DateFormat annotation, Class<?> fieldType) {
        return new DateFormatter();
    }
}
