# PROMPT 2A — Custom DSA + Algorithms (Java, from scratch)

Run this in Claude Code as its own session. Paste the whole prompt below as your first message.

---

You are a senior Java engineer building an EXHAUSTIVE REFERENCE CODEBASE of custom data
structures and algorithms implemented FROM SCRATCH. This is meant to cover the subject
completely — not scoped to any particular experience level or interview likelihood. Do NOT
skip any item listed below, including items marked "bonus" or "advanced."

## GLOBAL RULES — APPLY TO EVERY FILE YOU GENERATE

1. NO BUILT-IN DATA STRUCTURES: Do not use java.util.LinkedList, HashMap, ArrayList, Stack,
   Queue, TreeMap, TreeSet, PriorityQueue, Deque, etc. You may use plain arrays, Object[],
   or your own previously-built classes.

2. INBUILT FUNCTION EXCEPTION RULE: If a specific method genuinely cannot be implemented
   without relying on a Java built-in, then:
   - Still implement it as fully from scratch as possible
   - Add a comment: `// NOTE: This part cannot reasonably be done without using Java's
     built-in <X> because <reason>.`
   - Also provide an alternate version, clearly labeled `// Alternate version using
     built-in (acceptable if interviewer allows):`
   - Do not use this as a shortcut — only when truly unavoidable.

3. PACKAGE STRUCTURE:
   - One top-level package per data structure/algorithm family.
   - Shared internals go in a `common` subpackage (e.g., `linkedlist.common` holding a
     generic `Node<T>`) so sibling packages reuse it instead of redefining it.
   - Where a structure/algorithm builds on another (LRU Cache uses HashMap + DLL;
     Dijkstra uses the min-heap; Kruskal's uses Union-Find), import/reuse the earlier
     class instead of reimplementing it.

4. GENERICS: Every custom data structure MUST use generics, with bounds where required
   (e.g., `<T extends Comparable<T>>` for BST/AVL/Red-Black/TreeSet/TreeMap).

5. FILE STRUCTURE (every file):
   a) TOP-OF-FILE COMMENT BLOCK: what it is, the approach/strategy, time/space complexity
      of each operation (small table), a real-world analogy or example use case.
   b) For ALGORITHMS specifically, the top comment block must ALSO include:
      - WHEN TO USE THIS: the specific problem signals/patterns that suggest this
        algorithm (e.g., "sorted array + need pair/triplet -> two pointers")
      - WHAT DATA STRUCTURE(S) IT TYPICALLY OPERATES ON (array, linked list, graph,
        tree, etc.) and WHY that structure fits
      - VARIATIONS of the algorithm, if any (e.g., two pointers: fast/slow same-direction
        vs opposite-direction)
      - COMBINATION USAGE: if this algorithm is commonly combined with another
        algorithm or data structure to solve harder problems, explain the combination
        explicitly (e.g., "Dijkstra's = Greedy + Min-Heap + Graph adjacency list")
      - PSEUDOCODE: a short language-agnostic pseudocode block for the core algorithm
        (e.g., Prim's, Kruskal's, KMP, Dijkstra's, whichever applies) BEFORE the Java
        implementation, so the logic is understandable independent of Java syntax
   c) FULLY COMMENTED implementation: every non-trivial line explains WHAT and WHY,
      with small inline examples where helpful.
   d) A `main`/`Runner` method exercising EVERY public method/variation, covering edge
      cases explicitly (empty, single element, duplicates, largest/smallest input,
      invalid input), with clear labeled printed output for visual verification.

6. Do not skip ANY item below. Items marked "bonus"/"advanced"/"rare" are still required.

---

## PART A — CUSTOM DATA STRUCTURES

1. **ARRAY / ARRAYLIST** (package: `array`)
   a) `array.staticarray` — insert, delete (shifting), search, update
   b) `array.dynamicarray` — `MyArrayList<T>`: add, add(index), remove, remove(index),
      remove(value), get, set, size, isEmpty, clear, contains, indexOf, toArray,
      resize/grow (explain growth factor), trimToSize

2. **LINKED LIST** (package: `linkedlist`, common: `linkedlist.common` with shared `Node<T>`)
   a) `linkedlist.sll`
      - insertAtHead, insertAtTail, insertAtPosition
      - deleteAtHead, deleteAtTail, deleteByValue, deleteAllOccurrences(value),
        deleteByPosition
      - search/find, get(index)
      - reverse (iterative AND recursive)
      - detect cycle using Floyd's Tortoise-and-Hare algorithm + find cycle start node
        (explain the math: why slow/fast pointers must meet if a cycle exists, and why
        resetting one pointer to head finds the cycle start)
      - find middle element (slow/fast pointer, same tortoise-hare family)
      - merge two sorted SLLs
      - isPalindrome
      - toString/print/traverse
   b) `linkedlist.dll`
      - insertAtHead, insertAtTail, insertAtPosition
      - deleteAtHead, deleteAtTail, deleteByPosition
      - traverse forward, traverse backward
      - reverse
   c) `linkedlist.cll`
      - insert, delete
      - traverse (cycle-aware stopping condition)
      - detect if a given list is circular

3. **STACK** (package: `stack`)
   a) `stack.arraybased` — push, pop, peek, isEmpty, isFull, dynamic resizing,
      search(element)
   b) `stack.linkedlistbased` — push, pop, peek, isEmpty
   c) `stack.twoqueues` — Stack using two Queues

4. **QUEUE** (package: `queue`)
   a) `queue.arraybased` — enqueue, dequeue, front, isEmpty, isFull
   b) `queue.linkedlistbased` — enqueue, dequeue, front, isEmpty
   c) `queue.circular` — circular queue (array-based)
   d) `queue.twostacks` — Queue using two Stacks
   e) `queue.monotonic` — monotonic increasing AND decreasing queue (backs sliding window
      maximum/minimum in Part B)

5. **DEQUE** (package: `deque`)
   - `MyDeque<T>` — array-based AND linked-list-based
   - addFirst, addLast, removeFirst, removeLast, peekFirst, peekLast

6. **HASHMAP** (package: `hashmap`)
   - `hashmap.chaining` — array of buckets + chaining for collisions:
      put, get, remove, containsKey, containsValue, size, isEmpty, keySet, values,
      entrySet, basic iterator support, resize/rehash when load factor exceeded,
      custom hash function (explain index = hash(key) % capacity)
   (NOTE: Cuckoo Hashing variant moved to file 02D — it's an alternate/advanced
   collision strategy, built there using Java's built-in functions.)

7. **SET** (package: `set`)
   a) `set.hashset` — `MyHashSet<T>` on custom HashMap
   b) `set.treeset` — `MyTreeSet<T>` on BST, sorted traversal

8. **TREEMAP** (package: `treemap`)
   - `MyTreeMap<K,V>` on BST logic, sorted keySet

9. **BINARY SEARCH TREE** (package: `tree.bst`)
   - insert, delete (all 3 cases), search
   - inorder, preorder, postorder (recursive AND iterative), level order (BFS)
   - findMin, findMax, height, checkIfBalanced, checkIfValidBST

10. **AVL TREE** (package: `tree.avl`)
    - insert with rebalancing (LL, RR, LR, RL rotations)
    - delete with rebalancing
    - getBalanceFactor, getHeight, search, inorder traversal

11. **RED-BLACK TREE** (package: `tree.redblack`)
    - insert: fix-up based on uncle's color —
      - Case 1: uncle is red -> recolor parent, uncle, grandparent; move fix-up pointer
        up to grandparent and repeat
      - Case 2: uncle is black, new node forms a "triangle" -> rotate at parent first
        to convert into a "line" case
      - Case 3: uncle is black, new node forms a "line" -> rotate at grandparent and
        recolor
      - Comment: "line" maps loosely to single rotations (like AVL's LL/RR), "triangle"
        to double rotations (like AVL's LR/RL) — but the deciding factor is uncle's color.
    - delete: fix-up based on sibling's color and sibling's children's colors —
      - Case 1: sibling is red
      - Case 2: sibling is black, both children black
      - Case 3: sibling is black, near nephew red, far nephew black
      - Case 4: sibling is black, far nephew red
    - search, inorder traversal
    - comment: why Red-Black trees exist (looser balancing than AVL, fewer rotations on
      average; backs Java's TreeMap/TreeSet internally)

12. **SEGMENT TREE** (package: `tree.segment`)
    - build(array), rangeQuery(l, r), update(index, value)
    - rangeUpdate + lazy propagation (advanced)

13. **FENWICK TREE / BIT** (package: `tree.fenwick`)
    - build(array), update(index, delta), prefixSum(index), rangeSum(l, r)

13b. **SPARSE TABLE** (package: `tree.sparsetable`)
    - build(array) using binary lifting (precompute answers for ranges of length
      2^k), rangeQuery(l, r) (idempotent operations like min/max/gcd — O(1) per
      query after O(n log n) preprocessing)
    - Applies to: static (immutable) arrays with repeated range-min/max/gcd
      queries; when to use: contrast with Segment Tree — Sparse Table is faster
      per query (O(1) vs O(log n)) but only works when the array never changes
      (no updates), and only for idempotent operations (min/max/gcd work; sum
      does not, since overlapping ranges would double-count)

13c. **SQRT DECOMPOSITION** (package: `tree.sqrtdecomposition`)
    - build(array) — partition into blocks of size ~sqrt(n), precompute a
      per-block aggregate (sum/min/max)
    - update(index, value), rangeQuery(l, r) — O(sqrt(n)) per operation
    - Applies to: range queries with updates, on arrays; when to use: comment
      comparing against Segment Tree/Fenwick Tree — Sqrt Decomposition has
      simpler code and is easier to adapt to unusual query types, but
      O(sqrt(n)) is slower than Segment Tree's O(log n); often used when the
      query type is too complex to fit cleanly into a Segment Tree's merge
      logic

14. **B-TREE** (package: `tree.btree`) — advanced; insert + search; note it's the
    generalized structure that databases use; contrast with B+ Tree in Part K

15. **BINARY HEAP** (package: `heap`)
    a) `heap.minheap`, b) `heap.maxheap`, c) `heap.heapsort`

16. **PRIORITY QUEUE** (package: `priorityqueue`)
    - `MyPriorityQueue<T>` on heap, with comparator support

17. **TRIE** (package: `trie`)
    - insert, search, startsWith, delete(word), countWordsWithPrefix

18. **GRAPH** (package: `graph`, adjacency list, subpackage `graph.common`)
    - addVertex, addEdge, removeEdge, BFS, DFS (recursive AND iterative),
      detect cycle (directed AND undirected), topological sort (Kahn's/BFS-based)

19. **LRU CACHE** (package: `cache.lru`) — HashMap + DLL

20. **LFU CACHE** (package: `cache.lfu`) — frequency map + DLL per frequency

21. **SPARSE MATRIX** (package: `matrix.sparse`)

22. **SKIP LIST** (package: `skiplist`) — advanced/rare

23. **TREAP** (package: `treap`) — advanced/rare

   (NOTE: BK-Tree, and the entire String Similarity/Fuzzy Matching algorithm set that
   references it, have been moved to file 02D — they're applied search-engine
   techniques, not core DSA teaching material. Built there using Java's built-in
   functions.)

   (NOTE: Spatial data structures — KD-Tree, Quad-Tree, R-Tree — moved to file 02D.
   They're applied/advanced structures used in specific domains like GIS/games, not
   core DSA teaching material, and are built there using Java's built-in functions.)

For EVERY structure: comment answering "Why implement this yourself when Java's built-in
exists? What's the tradeoff?"

---

## PART B — CUSTOM ALGORITHMS
(each file must include the WHEN TO USE / DATA STRUCTURE / VARIATIONS / COMBINATION
comment block described in Global Rule 5b)

1. **SORTING** (package: `algorithms.sorting`)
   - Bubble, Selection, Insertion, Merge, Quick (pivot strategy explained), Heap
     (combination: Heap Sort = Binary Heap + Sorting), Shell, Counting, Radix, Bucket
   - comment table: best/avg/worst time complexity + stability, per file
   - When to use: note which sorts are stable/in-place, and when counting/radix/bucket
     beat comparison sorts (bounded integer ranges, etc.)
   - Comment (no implementation required): Tim Sort is the real hybrid
     merge+insertion algorithm behind Java's `Arrays.sort()`/`Collections.sort()`
     for objects (and Python's `sorted()`); it runs Insertion Sort on small
     chunks, then merges them like Merge Sort — worth knowing the name and the
     idea even though implementing it fully is rarely asked

2. **SEARCHING** (package: `algorithms.searching`)
   - Binary Search (iterative AND recursive) — applies to: sorted array
   - Binary Search on Answer — applies to: monotonic search space; example: minimum
     in rotated sorted array
   - Quickselect (Hoare's Selection Algorithm) — applies to: unsorted array;
     find the Kth smallest/largest element in expected O(n), worst-case O(n^2);
     combination: uses QuickSort's partition step (Part B, item 1) but recurses
     into only the one side containing the target index, instead of both sides;
     when to use: "find the Kth element" without needing the whole array fully
     sorted — faster on average than sorting the whole array first

3. **TWO POINTERS** (package: `algorithms.twopointers`)
   a) Opposite-direction (one pointer at start, one at end, moving toward each other) —
      used for: sorted array pair-sum problems, reversing an array in place,
      container-with-most-water style problems
   b) Same-direction / fast-slow (both start from index 0, move at different speeds or
      one only advances conditionally) — used for: removing duplicates in place,
      partitioning arrays, cycle detection (same family as Floyd's Tortoise-and-Hare
      from linked lists — cross-reference `linkedlist.sll`'s cycle detection)
   - Applies to: arrays (mostly sorted) and linked lists
   - Combination: often combined with Sorting first (sort the array, then apply two
     pointers) — e.g., 3Sum = Sorting + Two Pointers

4. **SLIDING WINDOW** (package: `algorithms.slidingwindow`)
   - Fixed-size window AND variable-size (expand/shrink) window
   - Applies to: arrays/strings (contiguous subarray/substring problems)
   - Combination: Sliding Window Maximum/Minimum = Sliding Window + Monotonic Deque
     (`queue.monotonic` from Part A)

5. **STRING ALGORITHMS** (package: `algorithms.strings`)
   - Naive pattern matching — applies to: strings; when to use: small inputs, no
     preprocessing needed
   - KMP (LPS/failure array) — applies to: strings; when to use: repeated pattern
     searches, avoids re-scanning
   - Rabin-Karp (rolling hash) — applies to: strings; when to use: multiple pattern
     search, plagiarism-detection-style problems
   - Boyer-Moore String Search (bad-character heuristic AND good-suffix heuristic) —
     applies to: strings; when to use: large alphabets/long patterns, since it can
     skip sections of the text instead of checking every position; comment noting
     the naming collision with the unrelated Boyer-Moore Voting Algorithm
     (Part B, item 15) — same author, two completely different algorithms
   - Z-algorithm — applies to: strings; when to use: pattern matching + finding all
     occurrences in linear time
   - Manacher's Algorithm — applies to: strings; when to use: longest palindromic
     substring in linear time (vs O(n^2) expand-around-center)
   (NOTE: Aho-Corasick, Suffix Array, and Suffix Tree moved to file 02D — they're
   advanced/applied string-indexing techniques, built there using Java's built-in
   functions.)

   (NOTE: The entire String Similarity/Distance/Fuzzy Matching algorithm set —
   Hamming, Damerau-Levenshtein, Jaro-Winkler, Sørensen-Dice, Jaccard, Cosine
   Similarity, N-gram Similarity, Soundex, Metaphone, Bitap, Levenshtein Automaton,
   Wildcard Matching, and the FuzzySearch demo — has also moved to file 02D, along
   with BK-Tree which it depends on. These are applied search-engine techniques,
   not core DSA teaching material. Levenshtein Distance itself stays covered in
   Part B section 9, Dynamic Programming, since edit distance is standard DP
   material.)

6. **GRAPH ALGORITHMS** (package: `algorithms.graph`, on `graph.common`)
   - BFS shortest path — applies to: unweighted graph
   - Dijkstra's — applies to: weighted graph, non-negative weights only;
     combination: Greedy + Min-Heap (priority queue) + Graph adjacency list
   - Bellman-Ford — applies to: weighted graph, handles negative weights, detects
     negative cycles; when to use: Dijkstra's precondition (non-negative weights) fails
   - Floyd-Warshall — applies to: weighted graph, all-pairs shortest path;
     combination: Dynamic Programming applied on a graph's adjacency matrix
   - Prim's — applies to: weighted, undirected, connected graph, for MST;
     combination: Greedy + Min-Heap + Graph
   - Kruskal's — applies to: weighted, undirected graph, for MST;
     combination: Greedy + Sorting + Union-Find (explain why Kruskal's needs Union-Find
     to detect cycles while Prim's doesn't)
   - Topological Sort (DFS-based) — applies to: Directed Acyclic Graph (DAG) only
   - A* search — applies to: weighted graph with a heuristic function;
     combination: Dijkstra's + Heuristic (Greedy Best-First component) + Min-Heap
   - Ford-Fulkerson Method (Max Flow), Edmonds-Karp variant (BFS-based augmenting
     path) — applies to: capacity-constrained network graphs; when to use: bandwidth
     allocation, bipartite matching; combination: Graph + BFS augmenting path search
   - Tarjan's Algorithm for Strongly Connected Components (SCC) — applies to:
     directed graph; when to use: finding maximal groups of mutually-reachable
     vertices; single-pass DFS using discovery time + low-link values
   - Kosaraju's Algorithm — applies to: directed graph, same SCC problem as
     Tarjan's; combination: two-pass DFS (DFS on original graph for finish order,
     transpose the graph, DFS again in reverse finish order); comment comparing:
     Kosaraju's is simpler to understand (two clear passes) but needs the graph
     transpose; Tarjan's does it in one pass but the low-link logic is trickier
   - Tarjan's Algorithm for Bridges and Articulation Points — applies to:
     undirected graph; when to use: finding edges/vertices whose removal
     disconnects the graph (network reliability analysis); same discovery-time/
     low-link technique as Tarjan's SCC, adapted for undirected graphs
   - Bipartite Graph Check (2-coloring via BFS or DFS) — applies to: any graph;
     when to use: determining if vertices can be split into two groups with no
     edge inside a group (e.g., matching problems, scheduling conflicts)
   - Eulerian Path / Eulerian Circuit (Hierholzer's Algorithm) — applies to:
     graph where you need to traverse every EDGE exactly once (contrast with
     Hamiltonian path, which visits every VERTEX exactly once — note this
     distinction explicitly); when to use: route problems where every connection
     must be used exactly once (e.g., mail delivery, DNA fragment assembly)
   - Graph Coloring — applies to: any graph; assign colors to vertices such that
     no two adjacent vertices share a color, using the minimum number of colors
     (chromatic number); combination: Greedy coloring (fast, not always optimal)
     AND Backtracking (exact, tries increasing color counts until one works);
     when to use: scheduling/conflict problems (e.g., exam timetabling, register
     allocation in compilers)

   (NOTE: Advanced pathfinding optimizations — Bidirectional Search/A*, IDA*, Jump
   Point Search, D* Lite, Contraction Hierarchies — have been moved to file 02C,
   since they're applied/advanced techniques built on top of this basic Dijkstra's/
   A*, not core DSA teaching material. They use Java's built-in collections there.)

6b. **TREE ALGORITHMS** (package: `algorithms.tree`, on `tree.bst` from Part A)
   - Lowest Common Ancestor (LCA) — applies to: binary tree/BST; variations:
     naive path-to-root comparison, recursive single-pass approach, and (for
     repeated queries) binary lifting/sparse-table preprocessing; when to use
     each: naive for one-off queries, binary lifting when there are many LCA
     queries on the same static tree
   - Diameter of a Binary Tree — applies to: binary tree; the longest path
     between any two nodes, which may or may not pass through the root
   - Serialize/Deserialize a Binary Tree — applies to: binary tree; convert a
     tree to a string representation and back; when to use: persisting a tree
     structure or sending it over a network
   - Morris Traversal — applies to: binary tree; inorder traversal in O(1)
     extra space without recursion or an explicit stack, by temporarily
     threading the tree using null right-pointers (Threaded Binary Tree
     technique); when to use: memory-constrained inorder traversal; comment
     explaining why this is usually a "do you know the advanced trick" follow-up
     rather than the first thing asked

6c. **ARRAY PATTERNS — ADDITIONAL** (package: `algorithms.patterns.array`)
   - Dutch National Flag Algorithm — applies to: arrays with a bounded/small set
     of distinct values (classically 3 values); 3-way partitioning in a single
     pass; when to use: "sort 0s, 1s, 2s" style problems, or as the partition
     step in a 3-way quicksort variant
   - Trapping Rain Water — applies to: arrays representing elevation/heights;
     variations: two-pointer approach (O(1) space) vs prefix-max/suffix-max
     arrays (O(n) space); combination: builds on the Two Pointers pattern
     (Part B, item 3)
   - Array Rotation (cyclic, in-place) — applies to: arrays; variations: using
     extra array (O(n) space), juggling algorithm (in-place, uses GCD), and the
     reversal algorithm (reverse whole array, then reverse each segment —
     simplest in-place approach)

7. **UNION-FIND / DISJOINT SET** (package: `algorithms.unionfind`)
   - path compression AND union by rank
   - Applies to: disjoint set membership problems, cycle detection in undirected
     graphs, Kruskal's MST

8. **RECURSION & BACKTRACKING** (package: `algorithms.recursion`)
   - Factorial, Fibonacci (plain recursive, memoized, iterative — complexity compared)
   - Permutations of a string/array — applies to: arrays/strings, backtracking pattern
   - Subset Generation (power set) — applies to: arrays; generate all 2^n subsets;
     variations: iterative bitmask approach vs recursive include/exclude approach
   - Combination Sum — applies to: arrays; find all combinations summing to a
     target, with/without reuse of elements; combination: backtracking + pruning
     (skip branches once the running sum exceeds the target)
   - N-Queens — applies to: 2D grid/board, backtracking with constraint pruning
   - Sudoku Solver — applies to: 2D grid/board; combination: backtracking +
     constraint checking (row/column/3x3-box validity) at each placement; comment
     on this being one of the more time-consuming backtracking problems to code
     live, so understanding the pruning strategy matters more than raw speed
   - When to use backtracking generally: problems requiring exploring all
     combinations/paths with the ability to "undo" a choice

8b. **MEET IN THE MIDDLE** (package: `algorithms.meetinthemiddle`)
   - Split the input into two halves, solve/enumerate each half independently,
     then combine the two partial results (often via sorting + two pointers, or
     a HashMap lookup) to find the overall answer
   - Applies to: problems with exponential (2^n) brute-force complexity where n
     is too large for full brute force but small enough that 2^(n/2) is
     tractable (roughly n up to ~40, vs brute force capping out around n ~20-25)
   - When to use: subset-sum-style problems, or any "try all combinations"
     problem where splitting in half and combining is valid
   - Combination: Recursion/Backtracking (to enumerate each half) + Sorting or
     HashMap (to combine efficiently) — comment explicitly framing this as a
     technique built ON TOP of backtracking, not a replacement for it

9. **DYNAMIC PROGRAMMING** (package: `algorithms.dp`)
   - 0/1 Knapsack, Longest Common Subsequence, Longest Increasing Subsequence,
     Edit Distance (Levenshtein), Coin Change (min coins + count ways),
     Matrix Chain Multiplication, Subset Sum/Partition, House Robber
   - Rod Cutting Problem — applies to: 1D DP; given a rod of length n and prices
     for each cut length, maximize revenue; comment noting this is structurally
     almost identical to Unbounded Knapsack
   - Word Break — applies to: strings + a dictionary/word set; determine if a
     string can be segmented into dictionary words; combination: DP + Set/HashMap
     lookup at each position
   - Held-Karp Algorithm (Traveling Salesman Problem via DP + Bitmask) — applies
     to: small-to-moderate n (roughly n <= 20, since state space is O(2^n * n));
     exact optimal solution to TSP, O(2^n * n^2) time, contrasted explicitly with
     the TSP approximation heuristics in file 02C (which trade optimality for
     speed on larger inputs); classic teaching example for "Bitmask DP" — state
     is (set of visited cities, current city)
   - When to use DP generally: overlapping subproblems + optimal substructure;
     explain top-down (memoization) vs bottom-up (tabulation) for each problem

10. **GREEDY ALGORITHMS** (package: `algorithms.greedy`)
    - Activity Selection, Fractional Knapsack, Job Sequencing with Deadlines
    - When to use: problem has the "greedy choice property"; explicitly contrast
      Fractional Knapsack (greedy works) vs 0/1 Knapsack (greedy fails, needs DP)

11. **MONOTONIC STACK PATTERNS** (package: `algorithms.patterns.monotonicstack`)
    - Next Greater Element, Next Smaller Element, Previous Greater/Smaller Element
    - Applies to: arrays; when to use: "find next/previous element satisfying a
      comparison" in O(n) instead of O(n^2)

12. **PREFIX SUM / KADANE'S** (package: `algorithms.patterns.prefixsum`)
    - Prefix Sum / Difference Array — when to use: repeated range-sum queries or
      repeated range-update operations (contrast with Segment Tree/Fenwick Tree from
      Part A — static array vs frequently updated array)
    - Kadane's Algorithm (max subarray sum)

13. **INTERVAL PROBLEMS** (package: `algorithms.intervals`)
    - Merge Intervals, Insert Interval
    - Combination: usually Sorting (by start time) + linear scan/merge

14. **BIT MANIPULATION** (package: `algorithms.bitmanipulation`)
    - isPowerOfTwo, countSetBits (naive loop AND Brian Kernighan's),
      swapWithoutTemp (XOR)
    - Single Number — applies to: an array where every element appears twice
      except one; find the lone element in O(n) time, O(1) space using XOR
      (all paired elements cancel to 0, leaving only the single one); classic
      "do you know the XOR trick" interview question

15. **BOYER-MOORE VOTING ALGORITHM** (package: `algorithms.realworld`)
    - Find majority element in O(1) space; note the naming collision with Boyer-Moore
      string search (different algorithm, same name)

   (NOTE: PageRank, Collaborative Filtering, MapReduce, and Reservoir Sampling moved
   to file 02D — they're tied to specific real-world domains (search ranking,
   recommendation systems, distributed computing), not core DSA teaching material.
   Boyer-Moore Voting stays here since it's a classic array/DSA interview technique.)

   (NOTE: Game Theory/Search algorithms — Minimax, Alpha-Beta Pruning, Monte Carlo
   Tree Search — and Approximation Algorithms — TSP approximation, Vertex Cover,
   Set Cover — have been moved to file 02C, since they're applied/advanced
   algorithm domains, not core DSA teaching material. They use Java's built-in
   collections there.)

---

## EXECUTION APPROACH
Go structure-by-structure / algorithm-by-algorithm, in the order listed (Part A fully,
then Part B). After each file, wait for me to say "next" before continuing. If a later
item depends on an earlier one, confirm that dependency is already built before starting,
or build it first if I've skipped ahead.
