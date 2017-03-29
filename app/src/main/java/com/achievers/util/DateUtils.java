package com.achievers.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static final String PATTERN = "EEEE, MMMM d, yyyy  HH:mm";
    private static final String PATTERN_ISO = "yyyy-MM-dd'T'HH:mm:ssZ";

    public static String format(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN);
        return simpleDateFormat.format(date);
    }

    public static String formatToISO(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN_ISO);
        return simpleDateFormat.format(date);
    }
}