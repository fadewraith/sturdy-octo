package datetimeapidemo;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeFormatterDemo {

    public static void main(String[] args) {
//        LocalDate now = LocalDate.now();
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        String format = now.format(dateTimeFormatter);
//        System.out.println(format);
//        String date = "25/04/1998";
//        LocalDate parsed = LocalDate.parse(date, dateTimeFormatter);
//        System.out.println(parsed);

//        ex2
//        String dateTimeString = "2023-04-24 10:30:45 IST";
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        String dateTimeString = "2023-04-24 10:30:45+05:30";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssXXX");
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateTimeString, dateTimeFormatter);
        System.out.println(zonedDateTime);
    }
}

//y - year
//M - month of year
//d - day of month
//H - hour of day 0-23
//h - hour of am/pm 1-12
//m - minute of hour
//s - second of minute
//S - fraction of second
//a - am/pm marker
//E - day of week
//D - day of year
//F - day of week in month
//w - week of year
//W - week of month
//k - hour of day 1-24
//K- hour of am/pm (0-11)
//z - time zone name
//Z - time zone offset