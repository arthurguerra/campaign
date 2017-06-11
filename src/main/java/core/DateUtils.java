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
        Calendar cal = Calendar.getInstance();
        Calendar calToday = Calendar.getInstance();
        cal.setTime(date);
        calToday.setTime(new Date());

        boolean sameYear = cal.get(Calendar.YEAR) == calToday.get(Calendar.YEAR);
        boolean sameMonth = cal.get(Calendar.MONTH) == calToday.get(Calendar.MONTH);
        boolean sameDay = cal.get(Calendar.DAY_OF_YEAR) == calToday.get(Calendar.DAY_OF_YEAR);

        return sameYear && sameMonth && sameDay;
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
}
