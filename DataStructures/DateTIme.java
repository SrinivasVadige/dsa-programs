package DataStructures;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 19 June 2025


    1) Legacy API (java.util.Date, java.util.Calendar): Introduced early, but had design flaws (mutability, thread-safety issues, confusing methods).

    2) Modern API (java.time.*): Introduced in Java 8 (JSR-310) to provide a comprehensive, immutable, and thread-safe date/time library inspired by Joda-Time. It covers:
    Dates (LocalDate, YearMonth, Year)
    Times (LocalTime, OffsetTime)
    Date-times (LocalDateTime, ZonedDateTime, OffsetDateTime)
    Instants (Instant)
    Durations and periods (Duration, Period)
    Time zones (ZoneId, ZoneOffset)
    Enums for months, days, etc.

    3) SimpleDateFormat works with java.util.Date and java.util.Calendar (legacy API).
    4) DateTimeFormatter works with the modern Java 8+ date/time classes like LocalDate, LocalDateTime, etc.
 */
public class DateTIme {

    public static void main(String[] args) throws ParseException {
        // print the current date and time --------------


        // 1. Using java.time.LocalDate 🔥 🔥 🔥
        java.time.LocalDate localDate = LocalDate.now();
        localDate = LocalDate.of(2025, 6, 19); // manual date
        localDate = LocalDate.parse("2025-06-19"); // --- "String" to LocalDate with default ISO format (yyyy-MM-dd).
        localDate = LocalDate.parse("20250619", java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd")); // --- "String" to LocalDate with custom format
        String localDateString = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); // --- LocalDate to "String"
        localDate = LocalDate.parse("19-Jun-2025", DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
        localDate = localDate.plusDays(2);
        localDate = localDate.minusDays(2);
        localDate = localDate.minus(Period.of(1, 2, 3));
        localDate = localDate.plus(Period.of(1, 2, 3));
        LocalDate localDate2 = LocalDate.of(2025, 6, 19);
        System.out.println(localDate.equals(localDate2));
        System.out.println(localDate.isAfter(localDate2));
        System.out.println(localDate);




        // 2. Using java.util.Date
        java.util.Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // String to Date
        date = sdf.parse("2025-06-19"); // throws ParseException
        // Date to String
        String dateString = sdf.format(date);
        System.out.println(date);



        // 3. Using java.util.Calendar
        java.util.Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.YEAR, 2025);
        calendar.set(Calendar.MONTH, 5);
        calendar.set(Calendar.DAY_OF_MONTH, 19);
        // formatter from String to Date and vice versa
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        // Set a specific date
        calendar.set(2025, Calendar.JUNE, 19);
        // Calendar to String
        String calString = sdf.format(calendar.getTime());
        System.out.println("Calendar Date: " + calString);
        // Parse String to Date and set to Calendar
        calendar.setTime(sdf.parse("20/06/2025"));
        System.out.println("Updated Calendar: " + sdf.format(calendar.getTime()));
        String strDate = sdf.format(date);
        System.out.println(calendar);




        // 4. Using java.time.Instant ---- IN UTC
        java.time.Instant instant = Instant.now();
        System.out.println(instant);


        // 5. Using java.time.LocalTime
        java.time.LocalTime localTime = LocalTime.now();
        System.out.println(localTime);

        // 6. Using java.time.LocalDateTime
        java.time.LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);

        // 7. Using java.time.ZonedDateTime
        java.time.ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.println(zonedDateTime);

        // 8. Using java.time.OffsetDateTime
        java.time.OffsetDateTime offsetDateTime = OffsetDateTime.now();
        System.out.println(offsetDateTime);

        // 9. Using java.time.OffsetTime
        java.time.OffsetTime offsetTime = OffsetTime.now();
        System.out.println(offsetTime);

        // 10. Using java.time.ZoneId
        java.time.ZoneId zoneId = ZoneId.systemDefault();
        System.out.println(zoneId);

        // 11. Using java.time.ZoneOffset
        java.time.ZoneOffset zoneOffset = (ZoneOffset) ZoneOffset.systemDefault();
        System.out.println(zoneOffset);

        // 12. Using java.time.Duration
        java.time.Duration duration = Duration.ofDays(2);
        System.out.println(duration);

        // 13. Using java.time.Period
        java.time.Period period = Period.ofDays(2);
        System.out.println(period);
    }
}
