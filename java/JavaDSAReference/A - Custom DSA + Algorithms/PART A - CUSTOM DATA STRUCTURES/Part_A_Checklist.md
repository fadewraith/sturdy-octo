# PART A: CUSTOM DATA STRUCTURES - CHECKLIST

This checklist tracks the completion of all fundamental data structures required by the master prompt. Every structure here was built from scratch without `java.util` collections. The only `java.util` helpers used are `Arrays.copyOf` (array resizing in the heaps and priority queue), `Comparator` (ordering interface for the priority queue and `MyArrayList.sort`), `Random` (Treap priorities), and the `Iterator` interface plus its standard exceptions (`NoSuchElementException`, `ConcurrentModificationException`) so every collection works in a for-each loop.

- [x] **1. Array** (`array`)
  - [x] Static Array Wrapper
  - [x] Dynamic Array (`MyArrayList`)

- [x] **2. Linked List** (`linkedlist`)
  - [x] Node / DoublyNode
  - [x] Singly Linked List
  - [x] Doubly Linked List
  - [x] Circular Linked List

- [x] **3. Stack** (`stack`)
  - [x] Array-based Stack
  - [x] Linked List-based Stack
  - [x] Stack using 2 Queues

- [x] **4. Queue** (`queue`)
  - [x] Array-based Queue
  - [x] Linked List-based Queue
  - [x] Circular Queue
  - [x] Queue using 2 Stacks
  - [x] Monotonic Queue

- [x] **5. Deque** (`deque`)
  - [x] Array-based Deque
  - [x] Linked List-based Deque

- [x] **6. Hash Map** (`hashmap`)
  - [x] Hash Map with Chaining (`MyHashMap`)

- [x] **7. Set** (`set`)
  - [x] HashSet (backed by `MyHashMap`)
  - [x] TreeSet (backed by `RedBlackTree`: O(log N) worst case; first/last/floor/ceiling/lower/higher)

- [x] **8. Tree Map** (`treemap`)
  - [x] TreeMap (backed by `RedBlackTree`: O(log N) worst case; firstKey/lastKey/floorKey/ceilingKey/lowerKey/higherKey)

- [x] **8b. Iteration** (all collections)
  - [x] Every list, set, map, stack, queue and deque implements `Iterable` (works in for-each)
  - [x] Fail-fast iterators (`ConcurrentModificationException`) for `MyArrayList` and `MyHashMap`; `MyArrayList` iterator supports `remove()`

- [x] **9. Binary Search Tree** (`tree.bst`)
  - [x] Insert, Delete, Search, Traversals

- [x] **10. AVL Tree** (`tree.avl`)
  - [x] Self-balancing (LL, RR, LR, RL Rotations)

- [x] **11. Red-Black Tree** (`tree.redblack`)
  - [x] Color tracking, Uncle-based fixups, Sibling-based fixups

- [x] **12. Segment Tree** (`tree.segment`)
  - [x] Point Updates
  - [x] Range Queries
  - [x] Range Updates (Lazy Propagation)

- [x] **13. Fenwick Tree / BIT** (`tree.fenwick`)
  - [x] Fast Build O(N)
  - [x] Update & Prefix/Range Sums

- [x] **13b. Sparse Table** (`tree.sparsetable`)
  - [x] Range Minimum Query (RMQ) in strict O(1)

- [x] **13c. Sqrt Decomposition** (`tree.sqrtdecomposition`)
  - [x] Block building and Range queries

- [x] **14. B-Tree** (`tree.btree`)
  - [x] Generic Node Splitting on insert
  - [x] Delete (CLRS: borrow from sibling or merge on the way down)

- [x] **15. Binary Heap** (`heap`)
  - [x] Min-Heap (natural order or custom Comparator; O(N) build-heap)
  - [x] Max-Heap (natural order or custom Comparator; O(N) build-heap)
  - [x] Heap Sort (In-place array sort)

- [x] **16. Priority Queue** (`priorityqueue`)
  - [x] Generic PQ with custom `Comparator` support

- [x] **17. Trie** (`trie`)
  - [x] Insert, Search, StartsWith
  - [x] Optimized `prefixCount` logic (duplicate inserts ignored so counts stay exact)

- [x] **18. Graph** (`graph`)
  - [x] Adjacency List (reusing custom structures)
  - [x] BFS, DFS (Iterative & Recursive)
  - [x] Cycle Detection (Directed 3-color & Undirected)
  - [x] Topological Sort (Kahn's Algorithm)
  - [x] WeightedGraph: Dijkstra with decrease-key + path, Bellman-Ford (negative cycles), Prim MST

- [x] **19. LRU Cache** (`cache.lru`)
  - [x] O(1) custom HashMap + custom internal DLL

- [x] **20. LFU Cache** (`cache.lfu`)
  - [x] O(1) Multi-map (Key->Node and Freq->DLL) tie-breaking architecture

- [x] **21. Sparse Matrix** (`matrix.sparse`)
  - [x] Array of Linked Lists (Coordinate logic)

- [x] **22. Skip List** (`skiplist`)
  - [x] Randomized coin-flip levels (Express lanes)

- [x] **23. Treap** (`treap`)
  - [x] Randomized Tree + Heap self-balancing

---
*All implementations confirmed fully generic, heavily documented, and functioning under extreme constraints without standard library cheats.*

- [x] **24. Bloom Filter** (`probabilistic.bloomfilter`)
  - [x] Multiple custom hash functions over bit array

- [x] **25. Consistent Hashing** (`distributed.consistenthashing`)
  - [x] Virtual nodes logic using custom sorted array and Binary Search ceiling
  - [x] Mixed hash (FNV-1a + Murmur3 finalizer) for even load, `removeServer`

- [x] **26. B+ Tree** (`tree.bplustree`)
  - [x] Insert (upsert) with leaf/internal splits, get, delete with borrow/merge
  - [x] Leaf-node Linked List for fast Range Scans (`rangeQuery`, `rangeScan`)

- [x] **27. Min Stack** (`stack.minstack`)
  - [x] push/pop/peek/getMin all O(1) (each node stores the minimum below it)

- [x] **28. Circular Doubly Linked List** (`linkedlist.cdll`)
  - [x] O(1) insert/delete at both ends (tail = head.prev), index access from the closer end
  - [x] rotate (pointer move only), reverse, forward and descending iterators

- [x] **29. Open Addressing Hash Map** (`hashmap.openaddressing`)
  - [x] Linear, quadratic (triangular steps) and double-hashing probe sequences
  - [x] Tombstone deletes, load factor <= 0.5, rebuild to sweep tombstones

- [x] **30. Linked Hash Map** (`hashmap.linked`)
  - [x] Insertion order or access order; optional maxEntries eviction (LRU cache)

- [x] **31. Generic Disjoint Set / Union-Find** (`unionfind`)
  - [x] Any element type; union by size + iterative path compression; setCount/setSize/groups

- [x] **32. Indexed Min Priority Queue** (`priorityqueue.indexed`)
  - [x] decreaseKey / increaseKey / changeKey / delete(id) in O(log N) (Dijkstra/Prim with decrease-key)

- [x] **33. Generic Skip List** (`skiplist.GenericSkipList`)
  - [x] Any Comparable type; add/remove/contains, first/last/floor/ceiling/lower/higher, sorted iteration

- [x] **34. Generic Segment Tree** (`tree.segment.GenericSegmentTree`)
  - [x] Any type + associative combine function (sum, min, max, gcd, concat); iterative 2N array; order-preserving for non-commutative functions

- [x] **35. Interval Tree** (`tree.interval`)
  - [x] Augmented AVL (subtree max endpoint); overlapsAny, findAllOverlapping, stab(point), insert/delete

- [x] **36. Order Statistic Tree** (`tree.orderstatistic`)
  - [x] Augmented AVL (subtree size); select(k), rank(x), countBetween, median - all O(log N)

- [x] **37. Splay Tree** (`tree.splay`)
  - [x] Top-down splay (no recursion); accessed key moves to the root; amortized O(log N)

- [x] **38. Bit Set** (`bitset`)
  - [x] long[] words; set/clear/flip/get, cardinality, nextSetBit/nextClearBit, and/or/xor/andNot, auto-grow

- [x] **39. Multiset / Multimap** (`multiset`, `multimap`)
  - [x] MyHashMultiset: counts per element, add/remove n copies, setCount, distinctCount
  - [x] MyListMultimap: key -> list of values, remove one value, removeAll(key), containsEntry
