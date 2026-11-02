# PROMPT 02A - CUSTOM DATA STRUCTURES AND ALGORITHMS

This workspace contains the foundational 'from-scratch' DSA implementations.

## PART A - CUSTOM DATA STRUCTURES
### array
- [x] MyArrayList.java
- [x] StaticArray.java

### bitset
- [x] MyBitSet.java

### cache
- [x] LFUCache.java
- [x] LRUCache.java

### deque
- [x] MyArrayDeque.java
- [x] MyLinkedListDeque.java

### distributed
- [x] ConsistentHashing.java (mixed hash, virtual nodes, removeServer)

### graph
- [x] Graph.java
- [x] State.java
- [x] WeightedGraph.java (weighted edges: Dijkstra + path, Bellman-Ford, Prim MST)

### hashmap
- [x] MyHashMap.java
- [x] MyLinkedHashMap.java (insertion/access order, LRU eviction)
- [x] OpenAddressingHashMap.java (linear / quadratic / double hashing, tombstones)

### heap
- [x] HeapSort.java
- [x] MaxHeap.java (natural order or Comparator)
- [x] MinHeap.java (natural order or Comparator)

### linkedlist
- [x] CircularDoublyLinkedList.java
- [x] CircularLinkedList.java
- [x] DoublyLinkedList.java
- [x] DoublyNode.java
- [x] Node.java
- [x] SinglyLinkedList.java

### matrix
- [x] SparseMatrix.java

### multimap
- [x] MyListMultimap.java

### multiset
- [x] MyHashMultiset.java

### priorityqueue
- [x] IndexedMinPQ.java (decrease-key)
- [x] MyPriorityQueue.java

### probabilistic
- [x] BloomFilter.java

### queue
- [x] ArrayQueue.java
- [x] CircularQueue.java
- [x] LinkedListQueue.java
- [x] MonotonicQueue.java
- [x] TwoStacksQueue.java

### set
- [x] MyHashSet.java
- [x] MyTreeSet.java

### skiplist
- [x] GenericSkipList.java (any Comparable type, floor/ceiling, iterable)
- [x] SkipList.java

### stack
- [x] ArrayStack.java
- [x] LinkedListStack.java
- [x] MinStack.java (O(1) getMin)
- [x] TwoQueuesStack.java

### treap
- [x] Treap.java

### tree
- [x] AVLTree.java
- [x] IntervalTree.java (augmented AVL: overlap / stabbing queries)
- [x] OrderStatisticTree.java (select k-th, rank, countBetween)
- [x] BinarySearchTree.java
- [x] BPlusTree.java (insert, get, delete with borrow/merge, range queries)
- [x] BTree.java (insert + CLRS delete)
- [x] FenwickTree.java
- [x] RedBlackTree.java
- [x] GenericSegmentTree.java (any type + combine function: sum/min/max/gcd/concat)
- [x] SegmentTree.java
- [x] SegmentTreeLazy.java
- [x] SparseTable.java
- [x] SplayTree.java (top-down, amortized O(log N))
- [x] SqrtDecomposition.java

### treemap
- [x] MyTreeMap.java

### trie
- [x] Trie.java (prefix counts, autocomplete)

### unionfind
- [x] DisjointSet.java (generic, union by size + path compression)

## PART B - CUSTOM ALGORITHMS
### algorithms
- [x] ActivitySelection.java
- [x] ArrayRotation.java
- [x] ArticulationPoints.java
- [x] AStar.java
- [x] BellmanFord.java
- [x] BFSShortestPath.java
- [x] BFSVariants.java (multi-source BFS, 0-1 BFS)
- [x] BinarySearch.java (bounds, first/last/count, rotated incl. duplicates, peak, 2D matrix, integer sqrt)
- [x] BinarySearchOnAnswer.java
- [x] BipartiteCheck.java
- [x] BitTricks.java
- [x] BoyerMoore.java
- [x] BoyerMooreMajority.java
- [x] BuildTreeFromTraversals.java
- [x] BurstBalloons.java
- [x] ClosestPairOfPoints.java
- [x] CoinChange.java (min coins, count combinations / ordered ways)
- [x] CombinationSum.java
- [x] ConvexHull.java
- [x] CountInversions.java
- [x] DecodeWays.java
- [x] DiameterOfTree.java
- [x] DigitDP.java
- [x] Dijkstra.java
- [x] Dinic.java (max flow + min cut)
- [x] DutchNationalFlag.java
- [x] EditDistance.java
- [x] EggDrop.java
- [x] EulerianPath.java (Hierholzer, undirected + directed, existence checks)
- [x] ExponentialSearch.java
- [x] FastExponentiation.java
- [x] FibonacciFactorial.java
- [x] FisherYatesShuffle.java
- [x] FixedWindow.java
- [x] FloydWarshall.java
- [x] FordFulkerson.java
- [x] FractionalKnapsack.java
- [x] GCDAndLCM.java
- [x] GenerateParentheses.java
- [x] GraphColoring.java (greedy, backtracking m-coloring, chromatic number)
- [x] GridPaths.java
- [x] GridTraversal.java (islands, flood fill, binary-matrix path)
- [x] HammingDistance.java
- [x] HeapPatterns.java (merge k lists, top-k, top-k frequent, median stream)
- [x] HeapSortAlgo.java
- [x] HeldKarpTSP.java
- [x] HopcroftKarp.java
- [x] HouseRobber.java
- [x] HuffmanCoding.java
- [x] InterpolationSearch.java
- [x] IntervalProblems.java (merge, insert, non-overlapping, meeting rooms I/II, intersection, min arrows)
- [x] JobSequencing.java
- [x] JumpGame.java
- [x] KadanesAlgorithm.java
- [x] KMPAlgorithm.java
- [x] KosarajusSCC.java
- [x] Kruskals.java
- [x] LargestRectangleHistogram.java
- [x] LCABinaryLifting.java
- [x] LinearSorts.java
- [x] LongestCommonSubsequence.java
- [x] LongestCommonSubstring.java
- [x] LongestIncreasingSubsequence.java
- [x] LongestPalindromicSubsequence.java
- [x] LowestCommonAncestor.java
- [x] ManachersAlgorithm.java
- [x] MatrixChainMultiplication.java
- [x] MatrixTraversal.java (spiral, rotate, set zeroes)
- [x] MedianOfTwoSortedArrays.java
- [x] MeetInTheMiddleAlgo.java
- [x] MergeSort.java
- [x] Minimax.java
- [x] MinimumWindowSubstring.java
- [x] MonotonicStack.java
- [x] MoreBitTricks.java (bit ops, counting bits, reverse, single number II/III, Gray code, Gosper, submasks)
- [x] MorrisTraversal.java
- [x] NaivePatternMatching.java
- [x] NextPermutation.java
- [x] NimGame.java (Nim, Sprague-Grundy)
- [x] NQueens.java
- [x] OppositeDirection.java
- [x] PalindromePartitioning.java
- [x] Permutations.java
- [x] PermutationsWithDuplicates.java
- [x] PrefixSumAndDifference.java
- [x] Prims.java
- [x] ProductExceptSelf.java
- [x] QuadraticSorts.java
- [x] QuickSelect.java
- [x] QuickSort.java
- [x] RabinKarp.java
- [x] ReservoirSampling.java
- [x] RodCutting.java
- [x] SameDirection.java
- [x] SerializeDeserialize.java
- [x] ShuntingYard.java (multi-digit numbers, right-assoc ^)
- [x] SieveOfEratosthenes.java
- [x] SubsetGeneration.java
- [x] SubsetSumPartition.java
- [x] SudokuSolver.java
- [x] TarjansBridges.java
- [x] TarjansSCC.java
- [x] ThreeSum.java
- [x] TopologicalSort.java (DFS + cycle detection, Kahn, findCycle, canFinish)
- [x] TrappingRainWater.java
- [x] UnboundedKnapsack.java
- [x] UnionFindAlgo.java
- [x] VariableWindow.java
- [x] WeightedRandomPick.java (prefix sums, alias method)
- [x] WordBreak.java
- [x] WordSearch.java
- [x] ZAlgorithm.java
- [x] ZeroOneKnapsack.java


