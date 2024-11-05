package datetimeapidemo;

import java.time.Instant;

public class InstantDemo {

    public static void main(String[] args) {
//        the difference, measured in milliseconds, between the current time and midnight, January 1, 1970 UTC
//        ex1
        long currentTimeMillis = System.currentTimeMillis();
        System.out.println(currentTimeMillis);

//        ex2
        Instant now = Instant.now();
        System.out.println(now);
        System.out.println(now.getEpochSecond());

    }
}
