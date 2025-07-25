package practiceQuestions;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class CustomArrayListNoDuplicatesAllowed extends ArrayList {

    // demo of custom arraylist that doesnt allows duplicates


    @Override
    public boolean add(Object o) {
        if(this.contains(o)) {
            return true;
        } else {
            return super.add(o);
        }
    }

    public static void main(String[] args) {
        CustomArrayListNoDuplicatesAllowed l1 = new CustomArrayListNoDuplicatesAllowed();
        l1.add(1);
        l1.add(1);
        l1.add(2);
        System.out.println(l1);

        Map<String, String> map = new TreeMap<>();
        map.put("a", "abc");
        map.put("b", "def");
        map.put("c", "ghi");

        // treemap internally does
        "d".compareTo("a");
        // greater than, inserts right side
        // less than, inserts left side


    }
}
