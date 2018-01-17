package com.achievers.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {
    private static final String PATTERN = "EEEE, MMMM d, yyyy  HH:mm";
    private static final String PATTERN_ISO = "yyyy-MM-dd'T'HH:mm:ssZ";

    public static String format(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN, Locale.getDefault());
        return simpleDateFormat.format(date);
    }

    public static String formatToISO(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN_ISO, Locale.getDefault());
        return simpleDateFormat.format(date);
    }

    public static Date create(
            int year,
            int month,
            int day,
            int hour,
            int minute,
            int second) {

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);

        return calendar.getTime();
    }
}