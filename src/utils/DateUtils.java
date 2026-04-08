package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static final String DATE_FORMAT = "dd/MM/yyyy";

    private static final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

    static {
        sdf.setLenient(false); // Prevent unreal date (ex 30/02)
    }

    public static String dateToString(Date date) {
        if (date == null) {
            return "";
        }
        return sdf.format(date);
    }

    public static Date stringToDate(String str) throws ParseException {
        if (str == null || str.trim().isEmpty()) {
            return null;
        }

        String cleanStr = str.trim().replaceAll("[^0-9/]", "");
        return sdf.parse(cleanStr);
    }

}
