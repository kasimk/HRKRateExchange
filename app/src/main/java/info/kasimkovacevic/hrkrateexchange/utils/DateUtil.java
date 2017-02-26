package info.kasimkovacevic.hrkrateexchange.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Kasim Kovacevic
 */
public class DateUtil {

    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    private static long DAY_IN_MS = 1000 * 60 * 60 * 24;

    /**
     * Format provided date in provided format
     *
     * @param date   represent {@link Date}
     * @param format represent format which will be used for formatting provided date
     * @return String instance of formatted date
     */
    public static String getFormattedDate(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return (dateFormat.format(date));
    }

    /**
     * Return instance of current {@link Date} formatted with provided format
     *
     * @param format represent format which will be used for formatting
     * @return instance of {@link Date} formatted with provided format
     */
    public static Date getCurrentDateWithoutTime(String format) {
        DateFormat formatter = new SimpleDateFormat(format);
        Date today = new Date();
        try {
            return formatter.parse(formatter.format(today));
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Return instance of week ago {@link Date} formatted with provided format
     *
     * @param format represent format which will be used for formatting
     * @return instance of {@link Date} formatted with provided format
     */
    public static Date getDateWeekAgoWithoutTime(String format) {
        DateFormat formatter = new SimpleDateFormat(format);
        Date date = getDateWeekAgo();
        try {
            return formatter.parse(formatter.format(date));
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Parse date in {@link String} to {@link Date}
     *
     * @param dateInString represent date value in {@link String}
     * @param format       represent format of string value of {@link String}
     * @return instance of {@link Date} parsed from provided date in {@link String}
     */
    public static Date parseDate(String dateInString, String format) {
        DateFormat df = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = df.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private static Date getDateWeekAgo() {
        return new Date(System.currentTimeMillis() - (7 * DAY_IN_MS));
    }
}
