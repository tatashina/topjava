package ru.javawebinar.topjava.web.formatter;

import org.springframework.format.Formatter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;

public class DateFormatter implements Formatter<LocalDate> {
    @Override
    public LocalDate parse(String s, Locale locale) throws ParseException {
        return StringUtils.isEmpty(s) ? null : LocalDate.parse(s);
    }

    @Override
    public String print(LocalDate localDate, Locale locale) {
        return localDate.toString();
    }
}
