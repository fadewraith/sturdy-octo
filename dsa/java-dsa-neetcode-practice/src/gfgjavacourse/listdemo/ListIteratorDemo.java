package gfgjavacourse.listdemo;

public class ListIteratorDemo {

    /**
     * works only for lists - LinkedList, ArrayList, etc
     * inherits from Iterator and provides below additional functionalities (in addition to next(), hasNext() and remove())
     * hasPrevious(), previous(), add(), set(), nextIndex(), previousIndex()
     * methods of iterator -
     * next(), hasNext(), remove()
     * listIterator(), listIterator(int index)
     * */

    /**
     * ArrayList in Java -
     * how they work internally -
     * internally use array only
     * if become full, do the following -
     * a. create a new array of double size
     * b. copy elements from old space to new
     * c. free old space
     * ArrayList.ensureCapacity(size)
     * */

    /**
     * ArrayList methods -
     * - add(object)
     * - add(index, object)
     * - contains(object)
     * - remove(index)
     * - remove(object)
     * - get(index)
     * - set(index, object)
     * - indexOf(object)
     * - lastIndexOf(object)
     * - clear()
     * - isEmpty()
     *
     * loops using get(), forEach loop, iterator(items can be removed while traversal), forEach() method
     * */

    /**
     * LinkedList -> Dequeue -> Queue -> Collection
     * Queue -> add(), remove(), element(), offer(), poll(), peek()
     * Deque -> addFirst(x), removeFirst(), getFirst(), addLast(x), removeLast(x), getLast(), offerFirst(x),
     * pollFirst(), peekFirst(), offerLast(x), pollLast(), peekLast()
     * */
}
