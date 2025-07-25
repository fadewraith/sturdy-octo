package gfgjavacourse.basics2advanced;

import java.util.Iterator;
import java.util.stream.Stream;

public class CollectionsDemo {

    /**
     * List - ArrayList, LinkedList, Vector, Stack
     * Set - HashSet, TreeSet, LinkedHashSet
     * Queue - LinkedList, ArrayDeque, PriorityQueue
     * Deque - LinkedList, ArrayDeque
     * Map - HashMap, TreeMap, LinkedHashMap
     * Collections Class(Implementations of basic algorithms)
     * */

    /**
     * Collection Hierarchy
     * */

    /**
     * Generics -
     * write once, use for any non primitive type
     * java collections extensively use generics
     * generic class/interface and methods
     * type safety
     * */

    /**
     * Collection Interface -
     *
     * */

    public interface Collection<E> extends Iterable<E> {
        int size();
        boolean isEmpty();
        boolean contains(Object o);
        boolean add(E e);
        boolean remove(Object o);
        Iterator<E> iterator();
        Object[] toArray();
        // T[] toArray(T[] arr);
        Stream<E> stream();
        Stream<E> parallelStream();
        // ..
    }

    void removeEven(Collection<Integer> c) {
        Iterator<Integer> it = c.iterator();
        while(it.hasNext()) {
            int x = (Integer)it.next();
            if(x % 2 == 0) {
                it.remove();
            }
        }
    }

    /**
     * toArray() methods -
     * 1. there are 2 methods -
     * Object[] toArray(), T[] toArray(T [])
     * 2. Present in the collection interface -
     * (can be used for ArrayList, LinkedList, ArrayDeque, PriorityQueue, HashSet, TreeSet, etc)
     * Object[] arr = list.toArray()
     * for(Object x: arr) {
     *     sout(x);
     * }
     * toArray and the collection upon its called, wont show each other changes and wont impact. change done in toArray wont reflect in collections and vice versa
     * Arrays.asList() - converts array into list. its a wrapper over the existing array. any changes done inasList will reflect in array and vice versa and returned list is a fixed size list
     *
     * List<Integer> list = new ArrayList<>();
     * Integer[] arr = new Integer[list.size()]
     * arr = list.toArray(arr); // way 1
     * Integer[] arr = list.toArray(new Integer[0]); // this tells that its an integer type // way 2
     * if creating a new array only for conversion purpose, then we can use like new Integer[0]
     * */

    /**
     * Collection Bulk Operations
     * 1. boolean containsAll(Collection<?> c) - if there are multiple ocurrences in another list, then also it will be true
     * 2. boolean addAll(Collection<? extends E> c) - if original collection is modified then it will return true
     * 3. boolean removeAll(Collection<?> c) - return true if modifies the collection
     * 4. boolean retainAll(Collection<?> c) - if list1 is modified then return true
     * 5. boolean removeIf(Predicate<? super E> filter)
     * */

    /**
     * Iterating through collections -
     * Iterators, for each loop, forEach() method, stream
     * using Iterators we can reverse also. check in detail
     * look for demo for forEach() method
     * void forEach(Consumer<? super T> action)
     * */


}
