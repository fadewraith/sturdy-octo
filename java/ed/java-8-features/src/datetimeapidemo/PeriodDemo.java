package datetimeapidemo;

import java.time.LocalDate;
import java.time.Period;

public class PeriodDemo {

    public static void main(String[] args) {
//        Instant start = Instant.now();
//        Instant end = Instant.now();
//        Duration duration=  Duration.between(start, end);
//        LocalDateTime a = LocalDateTime.now();
//        Thread.sleep(2000);
//        LocalDateTime b = LocalDateTime.now();
//        Duration bt = Duration.between(a, b);
//        System.out.println(bt);

        LocalDate now = LocalDate.now();
        LocalDate then = LocalDate.of(1990, 2, 2);
        Period period = Period.between(now, then);
        System.out.println(period);
    }
}
