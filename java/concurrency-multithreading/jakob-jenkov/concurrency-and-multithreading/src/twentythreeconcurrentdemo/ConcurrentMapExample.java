package twentythreeconcurrentdemo;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConcurrentMapExample {

    public static void main(String[] args) {
        Map hashMap = new HashMap();
        Map hashTable = new Hashtable();
        Map map = new ConcurrentHashMap();
        ConcurrentHashMap concurrentMap = new ConcurrentHashMap();

        concurrentMap.put("Key", "Value");
        Object object = concurrentMap.get("Key");

        ConcurrentMap<String, String> concurrentMap2 = new ConcurrentHashMap<>();
        concurrentMap2.put("Key2", "Value2");
        String value2 = concurrentMap2.get("Key2");

        concurrentMap2.remove("Key");

//        // slipped conditions
//        if(concurrentMap2.containsKey("Key2")) {
//            value2 = concurrentMap2.get("Key2");
//            concurrentMap2.remove("Key2");
//        }

        // slipped conditions
        if(!concurrentMap2.containsKey("Key2")) {
            concurrentMap2.put("Key2", "Value2");
        }

        concurrentMap2.putIfAbsent("Key2", "Value2");
        
        // fix for slipped conditions
        concurrentMap2.computeIfAbsent("Key2", (key) -> {
            System.out.println("Absent key: " + key);
            return "Val2";
        });


        concurrentMap2.computeIfPresent("Key2", (key, value) -> {
            System.out.println("Present key: " + key);
            return "";
        });

    }
}
