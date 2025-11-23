package eightraceconditionsdemo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RaceConditionsExample5 {

    public static void main(String[] args) {
        Map<String, String> sharedMap = new ConcurrentHashMap<>();

        Thread t1 = new Thread(getRunnable(sharedMap));
        Thread t2 = new Thread(getRunnable(sharedMap));

        t1.start();
        t2.start();
    }

//    private static Runnable getRunnable(Map<String, String> sharedMap) {
//        return () -> {
//            for (int i = 0; i < 1_000_000; i++) {
//                if(sharedMap.containsKey("key")) {
//                    String val = sharedMap.remove("key");
//                    if(val == null) {
//                        System.out.println("Iteration: " + i + " : Value for 'key' was null");
//                    }
//                } else {
//                    sharedMap.put("key", "value");
//                }
//            }
//        };
//    }

    private static Runnable getRunnable(Map<String, String> sharedMap) {
        return () -> {
            for (int i = 0; i < 1_000_000; i++) {
                synchronized (sharedMap) {
                    if(sharedMap.containsKey("key")) {
                        String val = sharedMap.remove("key");
                        if(val == null) {
                            System.out.println("Iteration: " + i + " : Value for 'key' was null");
                        }
                    } else {
                        sharedMap.put("key", "value");
                    }
                }
            }
        };
    }
}
