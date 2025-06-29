package neetcode.algorithms.hashing;

public class HashMapDemo {

    int size;
    int capacity;
    Pair[] map;

    public HashMapDemo() {
        this.size = 0;
        this.capacity = 2;
        this.map = new Pair[2];
    }

    public int hash(String key) {
        int index = 0;
        for(int i = 0; i < key.length(); i++) {
            index += key.charAt(i);
        }
        return index % this.capacity;
    }

    public String get(String key) {
        int index = this.hash(key);
        while(this.map[index] != null) {
            if(this.map[index].key.equals(key)) {
                return this.map[index].value;
            }
            index = (index + 1) % this.capacity;
        }
        return null;
    }

    public void put(String key, String value) {
       int index = this.hash(key);
       while(true) {
           if(this.map[index] == null) {
               this.map[index] = new Pair(key, value);
               this.size++;
               if(this.size >= this.capacity / 2) {
                   this.rehash();
               }
               return;
           } else if(this.map[index].key.equals(key)) {
               this.map[index].value = value;
               return;
           }
           index = (index + 1) % this.capacity;
       }
    }

    public void remove(String key) {
        if(this.get(key) == null) {
            return;
        }
        // Rehashing
        int index = this.hash(key);
        while(this.map[index] != null) {
            if(this.map[index].key.equals(key)) {
                // Removing an element using open-addressing actually causes a bug,
                // because we may create a hole in the list, and our get() may
                // stop searching early when it reaches this hole.
                this.map[index] = null;
                this.size--;
                return;
            }
            index = (index + 1) % this.capacity;
        }
    }

    public void rehash() {
        this.capacity *= 2;
        Pair[] oldMap = this.map;
        this.map = new Pair[this.capacity];
        this.size = 0;
        for(Pair p: oldMap) {
            if(p != null) {
                this.put(p.key, p.value);
            }
        }
    }

    public void print() {
        for(Pair p: this.map) {
            if(p != null) {
                System.out.println("Key: " + p.key + ", Value: " + p.value);
            }
        }
    }

    public static void main(String[] args) {
        // write the test to est all the methods of the HashMapDemo class
        HashMapDemo map = new HashMapDemo();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        map.put("key4", "value4");
        map.print();
    }

}


