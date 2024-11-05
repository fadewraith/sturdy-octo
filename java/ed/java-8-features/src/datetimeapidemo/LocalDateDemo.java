package datetimeapidemo;

import java.time.LocalDate;
import java.time.Month;

public class LocalDateDemo {

    public static void main(String[] args) {
//        ex1
        LocalDate now = LocalDate.now();
//        System.out.println(now);
//        ex2
        LocalDate customDate = LocalDate.of(1990, 2, 7);
        int dayOfMonth = now.getDayOfMonth();
        int monthValue = now.getMonthValue();
        Month month = now.getMonth();
        int year = now.getYear();
//        System.out.println(dayOfMonth);
//        System.out.println(monthValue);
//        System.out.println(month);
//        System.out.println(year);

//        ex3
        LocalDate today = LocalDate.now();
        System.out.println(today);
        LocalDate yesterday = today.minusDays(1);
        LocalDate pastMonth = today.minusMonths(1);
        LocalDate pastDate = today.minusMonths(100);
        System.out.println(yesterday);
        System.out.println(pastMonth);
        System.out.println(pastDate);
        if(today.isAfter(yesterday)) {
            System.out.println("Hello World!");
        }

    }
}
