import java.util.*;

public class Main {
	/*
	 * Collection Framework - 
	 * Array limitations - 
	 * 1. fixed in size
	 * 2. homogeneous
	 * 3. Array is not implemented by any DS, no inbuilt method
	 * 
	 * --------------------------
	 * 
	 * Collections - 
	 * 1. growable in nature
	 * 2. can hold both homogeneous & heterogeneous
	 * 3. every collection class is implemented based on some standard data structures
	 * 
	 * --------------------------
	 * 
	 * if we know the size in advance, highly recommended to go for array concepts.
	 * so in collections, for dynamic growable size, we have to pay the price of performance
	 * when we create an array list -> for 10 items, and we want to insert 11th item, then our array -
	 * - list will grow in size, and data will be copied from first array list to new array list and 11th item will be entered - 
	 * - in newly increased in size dynamically internally, and reassigned the old arraylist reference variable to new arraylist object.
	 * and old object will be automatically eligible for garbage collection, this is the scenario for adding one object
	 * 
	 * 
	 * --------------------------
	 * 
	 * difference between arrays & collections - 
	 * 1. fixed in size & collections are growable
	 * 2. memory wise arrays are not recommended & wrt memory, collections are recommended
	 * 3. wrt performance, arrays are good & collections are not recommended
	 * 4. only homogeneous but collections can hold both homogeneous and heterogeneous
	 * 5. no base data structure available and no ready-made method is available but every collection is implemented based on some ready-made data structures
	 * 6. int[] & Integer[], using arrays can hold primitives and objects but in collections only objects can be hold only objects not primitives
	 * 
	 * ---------------------------
	 * 
	 * collection & collection framework - 
	 * 
	 * a group of individual objects as a single entity, it is a collection.
	 * collection framework defines several classes and interfaces, which can be used a group of objects as single entity
	 * 
	 * java / c++
	 * collection / container
	 * collection framework / stl
	 * 
	 * ---------------------------
	 * 
	 * In collection framework, there are 9 key interfaces - 
	 * 
	 * 1. collection - to represent a group of individual objects as single entity go for collection, defines most common method & 
	 *   - collection interface is considered as root interface of collection framework.
	 *   note - there is no concrete class which implements collection interface directly
	 *   
	 * ---------------------------
	 * 
	 * Collection vs Collections - 
	 * 
	 * collection is interface / collections is a class(defines several utility like sorting, searching)
	 * 
	 * ---------------------------
	 * 
	 * List interface - in the group, duplicates are allowed, insertion order must be preserved, use list interface
	 * various implementation classes - ArrayList(1.2v) / LinkedList(1.2v) / Vector(1.0) -> Stack(these are legacy classes, coming from old versions)
	 * collection (1.2v)
	 * 
	 * ---------------------------
	 * 
	 * no duplicates and no insertion order(use set interface)
	 * Set interface(1.2v) - child interface of collection (implementation classes - HashSet(1.2v) -> LinkedHashSet(1.4v))
	 * 
	 * ---------------------------
	 * 
	 * interface - sortedSet interface
	 * 
	 * no duplicates but all elements should be inserted in some sorting order, use SortedSet(1.2v)
	 * 
	 * ---------------------------
	 * 
	 * navigableset(defines several methods for navigable purpose)(1.6v) -> TreeSet(1.6v)
	 * 
	 * ---------------------------
	 * 
	 *  queue interface - 
	 *  
	 *  before processing, if we want to represent the group of individual objects, then we should go for Queue,
	 *  various implementation classes for the queue,(all in 1.5v) PriorityQueue / BlockingQueue -> LinkedBlockingQueue / PriorityBlockingQueue
	 * 	if we want to represent a group of objects as key value pairs, then we should go for Map Interface - 
	 * 
	 * ---------------------------
	 * 
	 * map interface - 
	 * map is not the child interface of collection 
	 * 
	 * duplicate keys are not allowed, but values can be duplicates
	 * implementation classes of map(1.2v) - ( HashMap -> LinkedHashMap(1.4v) ) / WeakHashMap / (IdentityHashMap(1.4v)) / HashTable(child of dictionary(which is abstract class(1.0v)) -> Properties 
	 * 
	 * ---------------------------
	 * 
	 * sortedMAp(1.2v) / navigablemap(1.6v)
	 * sortedMap - key/value pairs with some sorting order
	 * navigableMap is child interface of sortedMap -> defines several utility methods for sorted map object(for navigation purpose)
	 * TreeMap(1.2v) is child intreface of navigableMAp
	 *
	 *---------------------
	 *
	 *if we want default natural sorting order, then use Comparable(I) and if we want customized sorting, then use Comparator(I)
	 *to get objects one by one from collections, then cursor concepts comes(Enumeration / Iterator / ListIterator)
	 *2 utility classes in the collection framework - collections / arrays
	 *
	 *---------------------
	 *
	 *Collection interface details - methods
	 * - add(Object o)
	 * - to add group of objects - addAll(Collection c)
	 * - remove(Object o) // remove 1 student
	 * - removeAll(Collection c) // remove all morning batch students
	 * - clear() // remove all objects // class completed, all people go out
	 * - retainAll(Collection c) // except morning batch, all the remaining people can go
	 * - isEmpty()
	 * - size()
	 * - contains(Object o) // whether particular object exists or not
	 * - containsAll(Collection c) // group of object is available or not
	 * - convert this collection to array -> Object[] a = c.toArray();
	 * - Iterator = iterator() // if we want to get one by one, then iterator method is used
	 * 
	 * ---------------------
	 * 
	 * collection framework - list interface
	 * whatever methods are in collection are available in list
	 * specific methods of list - List.add(int index, Object o)
	 * addAll(int index, Collection c)
	 * remove(int index)
	 * indexOf("a") // returns first occurrance
	 * lastIndexOf("a")
	 * get(int index)
	 * listIterator()
	 * set(int index, Object o);
	 * 
	 * 
	 * -----------------------
	 * 
	 * ArrayList (underlying data structures)- resizable array / growable array
	 * 2. duplicates
	 * 3. insertion order
	 * 4. heterogeneous objects (not allowed in TreeSet and TreeMap only)
	 * 5. "null" insertion is possible
	 * 
	 * constructors - 
	 * 1. ArrayList l = new ArrayList(); //default initial capacity is 10, once it reaches its max, creates a new capacity = ((current capacity * (3 / 2)) + 1)
	 * 2. ArrayList l = new ArrayList(int initialCapacity);
	 * 3. ArrayList l = new ArrayList(Collection c); // meant for interconversion between Collection objects
	 * 
	 * --------------------------
	 * 
	 * if we want to transfer object from one place to another place, every collection class already implements serializable
	 * every object class already implements clonable interface.
	 * 
	 * arraylist and vector implements RandomAccess interface, if frequent operation is retreiving operation, use arraylist and vector
	 * RandomAccess(I) present in util package and it doesnt contains any method, it is marker interface
	 * 
	 * RandomAccess 
	 * 
	 * --------------------------
	 * 
	 * Difference between ArrayList and vector - 
	 * Arraylist / Vector
	 * non-synchronized / synchronized
	 * not thread safe / thread safe
	 * performance is high / performance is slow
	 * 1.2v / 1.0v (legacy class)
	 * 	
	 * 
	 * ---------------------------
	 * 
	 * how to get synchronized version of arraylist object ?
	 * ArrayList l = new ArrayList();
	 * List l1 = Collections.synchronizedList(l1);
	 * 	public static List synchronizedList(List l) // return type is of list
	 * 
	 *  public static Set synchronizedSet(Set s)
	 *  public static Map synchronizedSet(Map s)
	 *  
	 *  ---------------------------
	 *  
	 *  LinkedList - 
	 *   - underlying data structure is doubly linked list
	 *   - insertion order is preserved
	 *   - duplicates are allowed
	 *   - heterogeneous elements are allowed
	 *   - null insertion is possible
	 *   - best for insertion or deletion in middle
	 *   - serializable and clonable
	 *   - no random access
	 *   we can use linkedlist class to implement stacks & queues & array to implement stack and queues
	 *   - addFirst(Object o)
	 *   - addLast(Object o)
	 *   - Object getFirst()
	 *   - Object getLast()
	 *   - Object removeFirst()
	 *   - Object removeLast()
	 *   
	 *   various constructors in linked list - 
	 *   1. LinkedList l = new LinikedList();
	 *   2. LinkedList l = new LinikedList(Collection c);
	 *   
	 *   ----------------------------------
	 *   
	 *   difference between arrayList and linkedList -
	 *   ArrayList / LinkedList
	 *   best retrieval / 
	 *   
	 *   ----------------------------------
	 *   
	 *   Vector implementation class -
	 *    
	 *   1. underlying data structure is Resizable array
	 *   2. duplicates are allowed
	 *   3. insertion order is preserved
	 *   4. null is allowed
	 *   5. heterogeneous
	 *   6. serializable and clonable
	 *   7. RandomAccess
	 *   8. synchronized
	 *   9. best for retrieval
	 *   
	 *   vector methods - 
	 *   add(Object o) - from Collection - List(I)
	 *   add(int index, Object o) - from List
	 *   addElement(Object o) - from Vector
	 *   
	 *   remove(Object o) - from collection
	 *   removeElement(Object o) 
	 *   remove(int index) - from list
	 *   removeElementAt(int index)
	 *   clear() - from collection
	 *   removeAllElements()
	 *   
	 *   Object get(int index) - 
	 *   Object elementAt(int index)
	 *   Object firstElement()
	 *   Object lastElement()
	 *   
	 *   int size() / int capacity() / Enumeration elements()
	 *   
	 *   vector class constructores - 
	 *   1. Vector v = new Vector(); //by default capacity = 10, new capacity = (2 * current_capacity)
	 *   2. Vector v = new Vector(int initialCapacity);
	 *   3. Vector v = new Vector(int initialCapacity, int incrementalCapcity); // instead of double new capacity, we can specify the incremental capacity
	 *   4. Vector v = new Vector(Collection c);
	 *   
	 *   -------------------------------------------------
	 *   
	 *   Stack class - 
	 *   it is child class of vector
	 *   especially designed for LIFO
	 *   constructor - Stack s = new Stack();
	 *   
	 *   methods - 
	 *   1. push(Object o)
	 *   2. pop() // to return last element of the stack LIFO
	 *   3. peek() // to return top of the stack without removal
	 *   4. empty()
	 *   5. search() // returns offset
	 *   
	 *  --------------------------------------------------------
	 *  
	 *  Three cursors of Java - 
	 *  1. Enumeration
	 *  2. Iterator
	 *  3. ListIterator
	 *  
	 *  1. Enumeration(1.0v) - 
	 *  Enumeration e = v.elements(); // where v is vector
	 *  methods available - 
	 *   - hasMoreElements() // public boolean hasMoreElements();
	 *   - nextElements() // public Object nextElement();
	 *  
	 *  limitations of enumeration - 
	 *  Enumeration concept is applicable only for legacy classes and hence it is not a universal cursor.
	 *  By using Enumeration, we can get only read access and we can't perform remove operation
	 *  ---to overcome above limitations, go for Iterator
	 *  
	 *  ----------------------------------------------
	 *  
	 *  2. Iterator - 
	 *  
	 *  we can perform read and remove operations
	 *  public Iterator iterator()
	 *  
	 *  ex - 
	 *  Iterator itr = c.iterator(); // where c = any collection object
	 *  
	 *  methods - 
	 *  1. public boolean hasNext()
	 *  2. public Object next()
	 *  3. public void remove()
	 *   
	 *   limitations - 
	 *   
	 *    - moves towards a forward direction (single direction cursor are Enumeration and Iterator)
	 *    - either read / remove operation
	 *    - we cant replace(addition of new object)
	 *    
	 *    to overcome above limitations, we should go for ListIterator
	 *   
	 *   --------------------------------------------------
	 *   
	 *   3. ListIterator - 
	 *   
	 *   Iterator is one direction cursor.
	 *   ListIterator is a bidirectional cursor
	 *   operations - read / remove / replacement / addition of new objects
	 *   public ListIterator listIterator() - return type is of list and is found in list
	 *   ex - ListeIterator ltr = l.listIterator, where is 'l' is any list object(arraylist, linkedlist, vector, stack)
	 *   ListeIterator(I) is child interface of Iterator
	 *   
	 *   // forward operation
	 *   public boolean hasNext()
	 *   public Object next()
	 *   public int nextIndex()
	 *   
	 *   // backward operation
	 *   public boolean hasPrevious()
	 *   public Object previous()
	 *   public int previousIndex()
	 *   
	 *   public void remove()
	 *   public void set(Object new)
	 *   public void add(Object new)
	 *   
	 *   --------------------------------------------------
	 *   
	 *   comparisons between the 3 cursors of java
	 *   
	 *   properties - enumeration / iterator / listIterator
	 *   
	 *   applicable for - only legacy classes / any collection class / only for list classes
	 *   movement - only forward direction / only forward / bidirectional
	 *   accessability - read access / read & remove / read, remove, replace, addition of new objects
	 *   how to get it - elements of vector class / iterator() method of Collection(I) / listIterator
	 *   methods - hasMoreElement(), nextElement() / hasNext(), next(), remove() / 9 methods listed above
	 *   legacy - yes(1.0v) / no(1.2v) / no(1.2v)
	 *   
	 *   --------------------------------------------------
	 *   
	 *   HashSet (Collection Framework) - 
	 *   
	 *   Collection -> Set(I) -> ( HashSet(1.2v) -> LinkedHashSet(1.4v) / SortedSet(I 1.2v) -> NavigableSet(I 1.6v) -> TreeSet (1.2v) )
	 *      
	 *   Set doesnt have any inbuilt methods, so we have to use only Collection Interface methods
	 *   
	 *   1. HashSet - 
	 *   underlying DS is Hashtable,
	 *   for duplicates 'add' method returns false, we get neither run time error nor compile time error,
	 *   all objects will be inserted based on hashcode of objects,
	 *   heterogeneous objects allowed,
	 *   null is allowed,
	 *   can implement serializable and clonable interfaces but not random access,
	 *   for search operation, hashSet is the best choice if the frequent operation is search operation,
	 *   
	 *   constructors - 
	 *   i.) HashSet h = new HashSet(); // with default initial capacity is 16, default fill ratio(load factor) = 0.75 means after filling 75%, a new HashSet object will be created
	 *   ii.) HashSet h = new HashSet(itn initialCapacity); // load factor will be 0.75 only
	 *   iii.) HashSet h = new HashSet(int initialCapacity, float loadFactor)
	 *   iv.) HashSet h = new HashSet(Collection c); // for interconversion between collection objects.
	 *   
	 *   --------------------------------------------------
	 *   
	 *   LinkedHashSet - 
	 *   
	 *   child class of HashSet
	 *   came in 1.4v
	 *   underlying DS is HashTable + LinkedList
	 *   insertion order is preserved
	 *   
	 *   we can use it in cache based applications
	 *   in cache duplicates are not allowed, and order is preserved
	 *   
	 *   --------------------------------------------------
	 *   
	 *   SortedSet Details
	 *   
	 *   methods -
	 *   
	 *    1. first()
	 *    2. last()
	 *    3. headSet(104) -> o/p will be less than 104
	 *    4. tailSet(104) -> o/p will be greater than and equal to 104
	 *    5. subSet(103, 110) -> o/p will be greater than and equal to 103 and less than 110
	 *    6. comparator() -> returns comparator object which describes underlying sorting technique, defualt natural sorting order -
	 *    if its a number, then default sorting order is ascending order
	 *    for strings - alphabetical order
	 *    for default sorting orders, returns null
	 *    
	 *   ---------------------------------------------------
	 *   
	 *   TreeSet(c) -
	 *   
	 *    uderlying DS  is balanced tree
	 *    duplicates are not allowed
	 *    insertion order is not applicable
	 *    will be inserted according to some sorting order
	 *    heterogeneous objects not allowed - TreeSet and TreeMap // error is runtime exception: class cache exception
	 *    null acceptance allowed only once
	 *    
	 *   
	 *   constructors - 
	 *   
	 *   1. TreeSet t = new TreeSet(); //elements will be inserted according to default sorting order
	 *   2. TreeSet t = new TreeSet(Comparator c); // when we want customized sorting
	 *   3. TreeSet t = new TreeSet(Collection c);
	 *   4. TreeSet t = new TreeSet(SortedSet s);
	 *   
	 *   null acceptance - 
	 *   to insert null, but we can insert ar first place
	 *   to insert null, for non empty set, compulsory comparison is required, we will get null pointer exception
	 *   
	 *   ---------------------------------------------------------
	 *   
	 *   TreeSet Details part 3 -
	 *   
	 *   public int compareTo(Object obj)
	 *   obj1.compareTo(obj2) --->
	 *   	returns -ve iff obj1 has to come before obj2
	 *   	returns +ve iff obj1 has to come after obj2
	 *   	returns 0 iff obj1 & obj2 are equal
	 *   
	 *   ----------------------------------------------
	 *   
	 *   TreeSet Details part 4 - 
	 *   
	 *   comparator(I) - 
	 *   1. customized sorting order
	 *   2. present in java.util
	 *   3. compares 2 methods - compare() and equals()
	 *   4. public int compare(Object obj1, Object obj2)
	 *   	returns -ve iff obj1 has to come before obj2
	 *   	returns +ve iff obj1 has to come after obj2
	 *   	returns 0 iff obj1 & obj2 are equal
	 *   4. public boolean equals()
	 *   when we use interface, we need to implement all the methods of that interface, but here in this case second one is optional i.e. boolean equals method
	 *   
	 *   we can use comparator to define our own sorting(customized sorting)
	 *   comparator interface present in java.utill package
	 *   
	 *   ----------------------------------------------
	 *   
	 *   TreeSet Details 5 - 
	 *   ex 1 -Integer Objects into TreeSet, descending order
	 *   TreeSet t = new TreeSet() ; // default ascending order
	 *   TreeSet t = new TreeSet(new MyComparator()) ; // for descending order, if we dont want default sorting order
	 *   t.add(10);
	 *   t.add(0);  --> +ve compare(0, 10)
	 *   t.add(15); --> +ve compare(15, 10)
	 *   t.add(20); --> -ve compare(20, 10)
	 *   			--> -ve compare(20, 15)
	 *   t.add(20); --> -ve compare(20, 10)
	 *   			--> -ve compare(20, 15)
	 *   			--> -ve compare(20, 20)
	 *   sopln(t); // o/p = [0, 5, 10, 15, 20]
	 *   			// new o/p will be - [20, 15, 10, 0]
	 *   
	 *   class MyComparator implements Comparator {
	 *   	public int compare(Object obj1, Object obj2) {
	 *   		Integer I1 = (Integer)obj1;
	 *   		Integer I2 = (Integer)obj2;
	 *   		if(I1 < I2)
	 *   			return 1;
	 *   		else if(I1 > I2)
	 *   			return -1;
	 *   		else
	 *   			return 0;
	 *   
	 *   ----------------------------------------------
	 *   
	 *   TreeSetDetails part - 9
	 *   
	 *   comparable vs comparator
	 *   for predefined comparable classes - ex. string, default natural sorting order is already available, - 
	 *   i.e. alphabetical order and if we want our own sorting order, then we will Comparator concept.
	 *   
	 *   for pre defined non comparable classes - ex. stringbuffer, for any type of sorting, we should go for Comparator concept only
	 *   
	 *   ----------------------------------------------
	 *    
	 *   TreeSetDetails part - 10
	 *   
	 *	 when we are implementing Comprarable interface, we must define the compareTo class
	 *
	 *	 --------------------------------------------------
	 *
	 *	 difference between comparable and comparator
	 *	 comparable / comparator
	 *	1. default natrual sorting order / customized sorting order
	 *	2. present in java.lang / present in java.util
	 *	3. contains compareTo() method / contains 2 methods - compare() and equals()
	 *	4. all wrapper classes & string classes / only 2 classes implements - Collator / RuleBasedCollator
	 *
	 *		
	 *		--------------------------------------------------
	 *
	 *comparison table of set implemented classes
	 *property - hashset / linkedhashset / treeset
	 *
	 *underlying ds - hashtable / hashtable + linked list / balanced tree
	 *insertion order - not preserved / preserved / not applicable
	 *sorting order - not applicable / not applicable / applicable
	 *heterogeneous objects - allowed / allowed / not allowed(only allowed if we are defining our own sorting)
	 *duplicate objects - not allowed / not allowed / not allowed
	 *null acceptance - allowed(only once) / allowed(only once) / for empty treeset as first element, null is allwed and in all other cases, we will get null pointerexception
	 *     
	 * */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("theory of collections");

	}

}
