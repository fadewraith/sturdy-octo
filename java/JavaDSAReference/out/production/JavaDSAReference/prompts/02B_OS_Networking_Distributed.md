# PROMPT 2B — OS, Networking, Distributed Systems & Probabilistic Structures (Java, using built-in functions)

Run this as its own Claude Code session, separate from 2A. Paste the whole prompt below.

---

You are a senior engineer building an EXHAUSTIVE REFERENCE CODEBASE simulating Operating
System, Networking, Distributed Systems, and Probabilistic Data Structure algorithms in
Java, from scratch (educational simulations of protocols/OS behavior, not actual OS/network
calls). Do NOT skip any item, including "bonus"/"advanced"/"conceptual" ones.

## GLOBAL RULES (different from the DSA/Algorithms prompt — read carefully)
1. USE JAVA'S BUILT-IN DATA STRUCTURES FREELY in this file (ArrayList, HashMap,
   LinkedList, Queue, PriorityQueue, etc.). This file is about OS/networking/
   distributed-systems ALGORITHM LOGIC, not data-structure-building practice — do not
   reimplement custom structures here. Use whatever built-in collection fits naturally,
   the way a real engineer simulating these systems would.
2. Inbuilt Function Exception Rule (for genuinely low-level primitives only, not
   collections): where something truly cannot be simulated meaningfully without a
   built-in (e.g., real thread blocking requires `wait()`/`notify()`, real atomicity
   requires `java.util.concurrent.atomic.*`), implement the algorithmic logic around it
   as clearly as possible, flag with `// NOTE: relies on Java's built-in <X> because
   <reason>`, and note the alternative approach where relevant.
3. Package per topic, `common` subpackage for shared pieces.
4. Every file: top comment block containing:
   - What this algorithm/concept is, the approach/strategy, time/space complexity,
     a real-world analogy or example use case
   - WHEN TO USE THIS / WHAT SYSTEM COMPONENT IT APPLIES TO / VARIATIONS /
     COMBINATION WITH OTHER ALGORITHMS (same as the DSA/Algorithms prompt)
   - **PSEUDOCODE**: a short language-agnostic pseudocode block for the core
     algorithm (e.g., Dijkstra's, Prim's, Banker's Algorithm, Bakery Algorithm,
     whichever applies to that file) BEFORE the Java implementation, so the logic
     is understandable independent of Java syntax
   Then: fully commented Java implementation, and a `main`/`Runner` method exercising
   all methods/scenarios including edge cases with labeled printed output.

---

## PART C — OPERATING SYSTEMS ALGORITHMS

1. **CPU SCHEDULING** (package: `os.scheduling`)
   - FCFS (First Come First Serve)
   - SJF (Shortest Job First) — preemptive (SRTF) AND non-preemptive
   - Priority Scheduling — preemptive AND non-preemptive
   - Round Robin (configurable time quantum)
   - Multilevel Queue Scheduling
   - Multilevel Feedback Queue Scheduling
   - Each: simulate processes (arrival time, burst time, priority), compute/print
     waiting time, turnaround time, completion time, averages
   - When to use: comment on tradeoffs (SJF minimizes avg wait but can starve long
     processes; RR is fair but has context-switch overhead; priority needs aging)

2. **ADVANCED CPU SCHEDULING** (package: `os.scheduling.advanced`)
   - Lottery Scheduling (probabilistic, ticket-based)
   - Completely Fair Scheduler (CFS) concept — Linux's actual scheduler; note it uses
     a Red-Black Tree internally ordered by "virtual runtime" (cross-reference your
     `tree.redblack` from the DSA prompt if available)
   - Real-Time Scheduling: Rate Monotonic Scheduling (RMS), Earliest Deadline First (EDF)

3. **PAGE REPLACEMENT** (package: `os.pagereplacement`)
   - FIFO
   - Optimal (Belady's Algorithm) — theoretical best-case, requires future knowledge
   - LRU — cross-reference `cache.lru` concept, same idea applied to memory pages
   - LFU — cross-reference `cache.lfu`
   - Clock / Second-Chance Algorithm — practical LRU approximation used in real OSes
   - Demonstrate Belady's Anomaly using FIFO (more frames causing MORE page faults —
     explain why, and why LRU/Optimal don't suffer from it)

4. **MEMORY MANAGEMENT — ADDRESS TRANSLATION** (package: `os.memorymanagement`)
   - Paging: multi-level page table simulation, virtual-to-physical address translation
   - TLB (Translation Lookaside Buffer) simulation — cache of recent translations,
     demonstrate TLB hit vs miss
   - Segmentation (segment table, base+limit translation)
   - Demand Paging (page fault simulation, lazy loading)

5. **MEMORY ALLOCATION** (package: `os.memoryallocation`)
   - First-Fit, Best-Fit, Worst-Fit (contiguous memory allocation strategies)
   - Buddy System Allocation (splitting/merging power-of-2 memory blocks)
   - Comment on internal vs external fragmentation, and which strategy minimizes which

6. **DISK SCHEDULING** (package: `os.diskscheduling`)
   - FCFS, SSTF (Shortest Seek Time First)
   - SCAN (elevator algorithm), C-SCAN (circular SCAN)
   - LOOK, C-LOOK
   - Compute total seek distance given a request queue + starting head position;
     comment on minimizing seek time vs fairness (avoiding starvation at disk edges)

7. **FILE ALLOCATION METHODS** (package: `os.filesystem`)
   - Contiguous Allocation, Linked Allocation, Indexed Allocation
   - Free Space Management: Bitmap-based, Linked-list-based
   - Comment comparing fragmentation/access-speed tradeoffs

8. **RAID ALGORITHMS** (package: `os.raid`)
   - RAID 0 (striping), RAID 1 (mirroring)
   - RAID 5 (striping with distributed parity — implement parity via XOR)
   - RAID 6 (double distributed parity)
   - Comment on fault tolerance vs storage efficiency tradeoffs

9. **DEADLOCK HANDLING** (package: `os.deadlock`)
   - Banker's Algorithm (deadlock AVOIDANCE) — safety algorithm + resource request
     algorithm using allocation/max/available matrices
   - Deadlock DETECTION using Resource Allocation Graph (cycle detection)
   - Comment on the 4 necessary conditions for deadlock and how each algorithm
     prevents/avoids/detects it

10. **PROCESS SYNCHRONIZATION** (package: `os.synchronization`)
    - Custom Semaphore (counting AND binary/mutex) using `wait()`/`notify()` as the
      unavoidable built-in primitive (flag per Inbuilt Function Exception Rule)
    - Producer-Consumer Problem (fully implemented using the custom Semaphore)
    - Dining Philosophers Problem (deadlock-prone version, then deadlock-free fix using
      resource ordering)
    - Readers-Writers Problem (readers-preference AND writers-preference variants)
    - Peterson's Algorithm (2-process mutual exclusion using only shared variables)

11. **SYNCHRONIZATION PRIMITIVES** (package: `os.synchronization.primitives`)
    - Bakery Algorithm (Lamport's) — mutual exclusion for N processes, extends
      Peterson's from 2 processes to N
    - Test-and-Set, Compare-and-Swap (CAS) — flag under Inbuilt Function Exception Rule;
      mention `AtomicInteger`/`AtomicReference` as the acceptable built-in
    - Spinlock implementation (busy-wait lock using CAS)
    - Monitor concept — show how `synchronized` + `wait()`/`notify()` implements one

12. **LOCK-FREE / CONCURRENT ALGORITHMS** (package: `os.synchronization.lockfree`)
    - Michael-Scott Lock-Free Queue (CAS-based — the actual algorithm behind
      `ConcurrentLinkedQueue`)
    - Treiber Stack (CAS-based lock-free stack)
    - Read-Copy-Update (RCU) concept — readers never block, writers create new copies
    - Double-Checked Locking pattern — with the "why it was broken pre-Java-5 without
      volatile" explained in comments
    - Work-Stealing Algorithm — the technique behind `ForkJoinPool`; idle threads
      "steal" tasks from busy threads' queues
    - Fork-Join / Divide-and-Conquer Parallelism — implement a parallel sum/merge sort
      using a custom fork-join style split
    - Barrier Synchronization — all threads wait at a point before any proceed
      (concept behind `CyclicBarrier`)
    - Future/Promise pattern — minimal from-scratch implementation, then compare to
      `CompletableFuture` as the acceptable built-in
    - Comment: "// True lock-free algorithms rely on hardware CAS instructions, which
      Java exposes via AtomicInteger/AtomicReference/sun.misc.Unsafe — these are the
      unavoidable built-in per the Inbuilt Function Exception Rule. The value here is
      understanding the ALGORITHM (how CAS retry loops work), not avoiding the
      primitive entirely."

13. **LOAD BALANCING ALGORITHMS** (package: `os.loadbalancing`)
    - Round Robin, Weighted Round Robin
    - Least Connections
    - IP Hash-based
    - Consistent Hashing (hash-ring concept; explain why it minimizes redistribution
      when nodes are added/removed, unlike simple modulo hashing — full implementation
      with virtual nodes lives in Part E #6, cross-reference it)

---

## PART D — NETWORKING / INTERNET PROTOCOL ALGORITHMS

1. **ROUTING ALGORITHMS** (package: `net.routing`)
   - Distance Vector Routing (Bellman-Ford applied to routing tables — cross-reference
     your Bellman-Ford from the algorithms prompt; explain this is used by RIP)
   - Link State Routing (Dijkstra's applied per-router — used by OSPF)
   - Comment comparing: Distance Vector = "routing by rumor" vs Link State = "routing
     by map"

2. **DISTANCE VECTOR ROUTING — LOOP PREVENTION** (package: `net.routing.loopprevention`)
   - Count-to-Infinity problem demonstration
   - Split Horizon technique
   - Poison Reverse technique

3. **FLOW CONTROL / RELIABLE DELIVERY** (package: `net.flowcontrol`)
   - Stop-and-Wait ARQ (Automatic Repeat reQuest)
   - Go-Back-N ARQ
   - Selective Repeat ARQ
   - Sliding Window Protocol (generalized version underlying Go-Back-N/Selective Repeat)
   - Simulate sender/receiver with packet loss injected, show retransmission behavior

4. **CONGESTION CONTROL** (package: `net.congestioncontrol`)
   - TCP Slow Start
   - AIMD (Additive Increase Multiplicative Decrease)
   - TCP Tahoe vs TCP Reno (behavior on packet loss — timeout vs triple duplicate ACK)
   - Simulate congestion window growth/shrink over simulated ACKs/losses, print the
     cwnd progression as text output

5. **TRAFFIC SHAPING / RATE LIMITING** (package: `net.trafficshaping`)
   - Leaky Bucket Algorithm
   - Token Bucket Algorithm
   - Comment comparing: leaky bucket enforces a strict constant output rate (smooths
     bursts completely), token bucket allows bursts up to a limit while maintaining an
     average rate

6. **MEDIUM ACCESS CONTROL** (package: `net.mac`)
   - CSMA/CD (Collision Detection — traditional Ethernet)
   - CSMA/CA (Collision Avoidance — WiFi)
   - Simulate multiple nodes attempting transmission, show collision detection/backoff
     (exponential backoff algorithm)

7. **ERROR DETECTION** (package: `net.errordetection`)
   - Checksum (simple sum-based)
   - CRC (Cyclic Redundancy Check) — implement polynomial division over bits
   - Comment on what each catches vs misses

8. **SPANNING TREE PROTOCOL** (package: `net.stp`)
   - Spanning Tree Algorithm (prevents broadcast loops in Ethernet switches) —
     cross-reference Prim's/Kruskal's MST algorithms; explain STP is MST adapted for
     loop prevention rather than cost minimization

9. **NETWORK FLOW** (package: `net.flow`)
   - Ford-Fulkerson Method (Max Flow), Edmonds-Karp variant (BFS-based)
   - Applies to: capacity-constrained network graphs; bandwidth allocation,
     bipartite matching

10. **ARP SIMULATION** (package: `net.arp`)
    - Simulate IP-to-MAC address resolution with a local cache table

11. **TCP CONNECTION STATE MACHINE** (package: `net.tcp`)
    - 3-way handshake simulation (SYN, SYN-ACK, ACK)
    - Connection teardown (FIN, ACK, FIN, ACK)
    - State machine: CLOSED -> LISTEN -> SYN_RECEIVED -> ESTABLISHED -> FIN_WAIT -> CLOSED

12. **IP FRAGMENTATION & REASSEMBLY** (package: `net.ipfragmentation`)
    - Simulate splitting a large packet into MTU-sized fragments, then reassembling
      using fragment offset + more-fragments flag

13. **MULTICAST ROUTING** (package: `net.multicast`)
    - Flooding-based multicast
    - Spanning-Tree-based multicast (cross-reference `net.stp`)

14. **CLOCK SYNCHRONIZATION** (package: `net.clocksync`)
    - Cristian's Algorithm
    - Berkeley Algorithm
    - Comment on why distributed systems need clock sync at all

15. **DNS RESOLUTION SIMULATION** (package: `net.dns`) — bonus/conceptual
    - Simulate recursive vs iterative DNS lookup using a mock hierarchy
      (root -> TLD -> authoritative)

---

## PART E — DISTRIBUTED SYSTEMS ALGORITHMS

1. **CONSENSUS** (package: `distributed.consensus`)
   - Paxos (basic single-decree version — implement the core propose/promise/accept
     flow with heavy comments)
   - Raft (leader election + log replication — more implementable/teachable than
     Paxos; used by etcd, Consul)

2. **LEADER ELECTION** (package: `distributed.leaderelection`)
   - Bully Algorithm
   - Ring Algorithm
   - Comment on when each is used and message-complexity tradeoffs

3. **LOGICAL CLOCKS** (package: `distributed.clocks`)
   - Lamport Timestamps (logical event ordering without physical clock sync)
   - Vector Clocks (detecting causality vs concurrency between events)

4. **DISTRIBUTED TRANSACTIONS** (package: `distributed.transactions`)
   - Two-Phase Commit (2PC)
   - Three-Phase Commit (3PC) — explain what it fixes over 2PC (blocking on
     coordinator failure)

5. **GOSSIP PROTOCOL** (package: `distributed.gossip`)
   - Simulate epidemic-style information propagation across simulated nodes

6. **CONSISTENT HASHING** (package: `distributed.consistenthashing`)
   - Full implementation with virtual nodes, hash ring, node add/remove with minimal
     redistribution demonstrated

---

## PART F — PROBABILISTIC DATA STRUCTURES

1. **BLOOM FILTER** (package: `probabilistic.bloomfilter`)
   - add(element), mightContain(element) — multiple hash functions + a bit array
   - Applies to: fast "definitely not present" checks before hitting a slow store;
     comment on false-positive-only guarantee (never false negatives)

2. **COUNT-MIN SKETCH** (package: `probabilistic.countminsketch`)
   - Approximate frequency counting with bounded memory
   - Applies to: streaming data, "top-k" frequency problems at scale

3. **HYPERLOGLOG** (package: `probabilistic.hyperloglog`) — bonus/advanced
   - Approximate cardinality (distinct count) estimation with minimal memory
   - Applies to: counting unique visitors/IPs at massive scale

---

## EXECUTION APPROACH
Go part-by-part, topic-by-topic, in the order listed (C, then D, then E, then F). After
each file, wait for me to say "next" before continuing. If a later item cross-references
a structure from the separate DSA/Algorithms prompt (e.g., Red-Black Tree, Prim's/
Kruskal's, min-heap), assume it exists and just reference/reimplement a minimal version
inline if needed — note in a comment which external file it would normally reuse.
