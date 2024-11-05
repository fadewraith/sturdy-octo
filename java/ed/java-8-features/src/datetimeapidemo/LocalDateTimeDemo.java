package datetimeapidemo;

import java.time.LocalDateTime;

public class LocalDateTimeDemo {

    public static void main(String[] args) {
//        ex1
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);

//        ex2
        LocalDateTime localDateTime = LocalDateTime.parse("2023-01-11T13:48");
        System.out.println(localDateTime);
    }
}
