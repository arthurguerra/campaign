package core;

import java.util.Calendar;
import java.util.Date;

/**
 * Utilities class for date operations.
 */
public class DateUtils {

    /**
     * Gets yesterday's date
     * @return {@link Date} object representing yesterday's date
     */
    public static Date yesterday() {
        return addDaysToCurrDate(-1);
    }

    /**
     * Gets today's date
     * @return {@link Date} object representing today's date
     */
    public static Date today() {
        return new Date();
    }

    /**
     * Gets tomorrows's date
     * @return {@link Date} object representing tomorrows's date
     */
    public static Date tomorrow() {
        return addDaysToCurrDate(1);
    }

    /**
     * Checks if the given date is today. This method only compares Year, Month and Day.
     * @param date date
     * @return True, if the given date has the same year, month and day of today. False, otherwise.
     */
    public static boolean isToday(Date date) {
        return datesAreTheSame(date, DateUtils.today());
    }

    /**
     * Adds a specific number of days to the current date.
     * For future dates, the given paramater must be positive.
     * For past dates, the given parameter must be negative
     * @param days number of days to add to the current date
     * @return {@link Date} object
     */
    private static Date addDaysToCurrDate(int days) {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    /**
     * Adds one day to the given date
     * @param date date
     * @return new date added 1 one
     */
    public static Date addOneDay(Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }

    /**
     * Check if two dates have the same Year, Month and Day.
     * @param date1 date
     * @param date2 date
     * @return True if both dates have the same year, month and day. False, otherwise.
     */
    public static boolean datesAreTheSame(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);

        boolean sameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
        boolean sameMonth = cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);

        return sameYear && sameMonth && sameDay;
    }
}
