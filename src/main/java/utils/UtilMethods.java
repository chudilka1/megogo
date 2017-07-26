package utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UtilMethods {

    /**Util method for date conversion into necessary formats
     * (XML and JSON may have different date formats) */
    public static String dateConverter(String date) throws ParseException {
        DateFormat originalFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aa", Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parsedDate = originalFormat.parse(date);
        return targetFormat.format(parsedDate);
    }
}
