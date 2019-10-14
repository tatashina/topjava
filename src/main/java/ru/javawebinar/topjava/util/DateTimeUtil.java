package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable<T>> boolean isBetween(T lt, T startDateTime, T endDateTime) {
        return lt.compareTo(startDateTime) >= 0 && lt.compareTo(endDateTime) <= 0;
    }

    public static LocalDate toLocalDate(String localDate) {
        return localDate == null || localDate.isEmpty() ? null : LocalDate.parse(localDate);
    }

    public static LocalTime toLocalTime(String localTime) {
        return localTime == null || localTime.isEmpty() ? null : LocalTime.parse(localTime);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

