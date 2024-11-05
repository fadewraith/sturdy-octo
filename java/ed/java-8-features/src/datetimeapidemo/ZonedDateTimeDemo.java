package datetimeapidemo;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

public class ZonedDateTimeDemo {

    public static void main(String[] args) {
//        ex1
        ZonedDateTime now = ZonedDateTime.now();
//        System.out.println(now);
        Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
//        availableZoneIds.forEach(System.out::println);

//        ex2
        ZonedDateTime zonedDateTimeOf = ZonedDateTime.of(2000, 12, 1, 14, 30, 30, 30, ZoneId.of("America/New_York"));
//        System.out.println(zonedDateTimeOf);

//        ex3
        ZonedDateTime timeIndia = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
        ZonedDateTime timeNewYork = ZonedDateTime.now(ZoneId.of("America/New_York"));
//        System.out.println(timeIndia);
//        System.out.println(timeNewYork);
//        ZoneDateTime.parse("2024-11-05T07:26:36.784168713-05:00[America/New_York]");

//        ex4
        ZoneId zoneId = timeIndia.getZone();
        System.out.println(zoneId);


    }
}
