package com.book.search.common.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    public final static String SIMPLE_DATETIME_FORMAT = "[yyyy]-[MM]-[dd] [HH]:[mm]:[ss]";
    public static final ZoneId UTC = ZoneId.of("UTC");

    public static long getSecondsSinceEpochUTC() {
        return getSecondsSinceEpoch(UTC);
    }

    public static long getSecondsSinceEpoch(ZoneId zoneId) {
        return ZonedDateTime.now(zoneId).toEpochSecond();
    }

    public static String getDateTimeToString(LocalDateTime dateTime) {
        return getDateTimeToString(dateTime, SIMPLE_DATETIME_FORMAT);
    }

    public static String getDateTimeToString(LocalDateTime dateTime, String pattern) {
        return getDateTimeToString(dateTime, DateTimeFormatter.ofPattern(pattern));
    }

    public static String getDateTimeToString(LocalDateTime dateTime, DateTimeFormatter formatter) {
        if (dateTime != null) {
            return formatter.format(dateTime);
        } else {
            return null;
        }
    }
}
