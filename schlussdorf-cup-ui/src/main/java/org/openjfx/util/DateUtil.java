package org.openjfx.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * Provides util method for the current Date.
 */
public class DateUtil {

    private DateUtil(){}

    /**
     * Returns the current date as string. Formatted with day month and year.
     *
     * @return the current Date as string
     */
    public static String getCurrentDateAsString(){
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

        return simpleDateFormat.format(now);
    }

    /**
     * Returns the current year as string.
     *
     * @return the current year as string
     */
    public static String getCurrentYearAsString(){
        return String.valueOf(getCurrentDate().getYear());
    }

    private static LocalDate getCurrentDate(){
        return LocalDate.now();
    }
}
