import java.util.Hashtable;

public class HashTableDemo {

    public static void main(String[] args) {
//        Hashtable<Integer, String> table = new Hashtable<>(10, 0.5f);
        Hashtable<String, String> table = new Hashtable<>(21);
        table.put("100", "Spongebob");
        table.put("123", "Patrick");
        table.put("321", "Sandy");
        table.put("567", "Squidward");
        table.put("666", "Gary");

        System.out.println(table.get(100));

//        for(Integer key: table.keySet()) {
//            System.out.println((key.hashCode() % 10) + "\t" +key + "\t" + table.get(key));
//        }
//different data types will have different code formulas
        for(String key: table.keySet()) {
            System.out.println((key.hashCode() % 11) + "\t" +key + "\t" + table.get(key));
        }
        /*
        * key.hashCode() % capacity = index
        * linked similarly a linked list
        *
        * collision - hash function geenrates the same index for moer than one key
        *
        * bucket = an indexed storage location for one or more entries
        * can store multiple entries in case of a collision
        * */

        System.out.println(table.remove("666"));
    }
}
