package org.openjfx.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class DateUtil {

    private static LocalDate getCurrentDate(){
       return LocalDate.now();
    }

    public static String getCurrentDateAsString(){
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

        return simpleDateFormat.format(now);
    }

    public static String getCurrentYearAsString(){
        return String.valueOf(getCurrentDate().getYear());
    }
}
