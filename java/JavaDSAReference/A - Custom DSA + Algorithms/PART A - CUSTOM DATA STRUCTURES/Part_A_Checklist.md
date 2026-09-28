# PART A: CUSTOM DATA STRUCTURES - CHECKLIST

This checklist tracks the completion of all fundamental data structures required by the master prompt. Every structure here was built strictly from scratch, without utilizing any `java.util.*` built-in classes.

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
  - [x] TreeSet (backed by `BinarySearchTree`)

- [x] **8. Tree Map** (`treemap`)
  - [x] TreeMap (backed by `BinarySearchTree`)

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

- [x] **15. Binary Heap** (`heap`)
  - [x] Min-Heap
  - [x] Max-Heap
  - [x] Heap Sort (In-place array sort)

- [x] **16. Priority Queue** (`priorityqueue`)
  - [x] Generic PQ with custom `Comparator` support

- [x] **17. Trie** (`trie`)
  - [x] Insert, Search, StartsWith
  - [x] Optimized `prefixCount` logic

- [x] **18. Graph** (`graph`)
  - [x] Adjacency List (reusing custom structures)
  - [x] BFS, DFS (Iterative & Recursive)
  - [x] Cycle Detection (Directed 3-color & Undirected)
  - [x] Topological Sort (Kahn's Algorithm)

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

- [x] **26. B+ Tree** (`tree.bplustree`)
  - [x] Simplified structure demonstrating leaf-node Linked List for fast Range Scans
