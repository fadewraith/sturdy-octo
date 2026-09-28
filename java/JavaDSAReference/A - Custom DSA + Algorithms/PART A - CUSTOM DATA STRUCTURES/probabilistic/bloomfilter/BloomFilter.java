package probabilistic.bloomfilter;

/**
 * BLOOM FILTER (FROM SCRATCH)
 * 
 * WHAT IT IS:
 * A space-efficient probabilistic data structure used to test whether an element 
 * is a member of a set. 
 * 
 * GUARANTEES:
 * - FALSE POSITIVES are possible.
 * - FALSE NEGATIVES are IMPOSSIBLE.
 * 
 * WHEN TO USE THIS:
 * - Cache filtering, Database engines, Malicious URL blocking.
 * 
 * COMPLEXITY:
 * Time: O(K) where K is the number of hash functions (usually very small, so O(1)).
 * Space: O(M) where M is the size of the bit array.
 */
public class BloomFilter<T> {

    private boolean[] bitArray;
    private int size;

    public BloomFilter(int size) {
        this.size = size;
        this.bitArray = new boolean[size];
    }

    private int hash1(String s) {
        int hash = 0;
        for (int i = 0; i < s.length(); i++) {
            hash = (hash * 31 + s.charAt(i)) % size;
        }
        return Math.abs(hash);
    }

    private int hash2(String s) {
        int hash = 5381;
        for (int i = 0; i < s.length(); i++) {
            hash = ((hash << 5) + hash) + s.charAt(i);
        }
        return Math.abs(hash % size);
    }

    private int hash3(String s) {
        int hash = 0;
        for (int i = 0; i < s.length(); i++) {
            hash = s.charAt(i) + (hash << 6) + (hash << 16) - hash;
        }
        return Math.abs(hash % size);
    }

    public void add(T element) {
        String s = element.toString();
        bitArray[hash1(s)] = true;
        bitArray[hash2(s)] = true;
        bitArray[hash3(s)] = true;
    }

    public boolean mightContain(T element) {
        String s = element.toString();
        return bitArray[hash1(s)] && 
               bitArray[hash2(s)] && 
               bitArray[hash3(s)];
    }

    public static void main(String[] args) {
        System.out.println("--- BLOOM FILTER DEMO ---");
        
        BloomFilter<String> filter = new BloomFilter<>(100);
        
        filter.add("google.com");
        filter.add("amazon.com");
        
        System.out.println("Contains 'google.com'? " + filter.mightContain("google.com")); // True
        System.out.println("Contains 'apple.com'? " + filter.mightContain("apple.com"));   // False
    }
}
