package datetimeapidemo;

import java.time.LocalTime;

public class LocalTimeDemo {

    public static void main(String[] args) {
//        ex 1
        LocalTime now = LocalTime.now();
        System.out.println(now);
//        LocalTime customTime = LocalTime.of(14, 30, 30);
        LocalTime customTime = LocalTime.of(14, 30);
//        System.out.println(customTime);

//        ex2
        String timeInString = "15:30:45";
        LocalTime parsed = LocalTime.parse(timeInString);
//        System.out.println(parsed);

//        ex3
        LocalTime beforeOneHour = now.minusHours(1);
        System.out.println(beforeOneHour);

        if(now.isAfter(beforeOneHour)) {
            System.out.println("time passed");
        }
    }
}
