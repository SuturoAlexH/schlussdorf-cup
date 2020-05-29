package util;

import java.time.LocalDate;

public class DateUtil {

    private static LocalDate getCurrentDate(){
        return LocalDate.now();
    }

    public static String getCurrentYearAsString(){
        return String.valueOf(getCurrentDate().getYear());
    }
}