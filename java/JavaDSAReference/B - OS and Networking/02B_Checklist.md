# PROMPT 02B - OS, NETWORKING, AND DISTRIBUTED SYSTEMS

This workspace contains the high-level simulations of OS mechanics, Networking protocols, and Distributed Systems algorithms.

> **Note:** Any algorithms from the 02B syllabus that are traditionally built as "from-scratch" Data Structures (like `BloomFilter`, `ConsistentHashing`, `LRU Cache`, `LFU Cache`) were explicitly built inside the strict `02A` workspace instead.

---

## PART C — OPERATING SYSTEMS ALGORITHMS

### 1. CPU Scheduling (`os.scheduling`)
- [x] BasicCPUScheduling.java (FCFS, Non-Preemptive SJF, Round Robin)
- [x] PreemptiveScheduling.java (SRTF, Preemptive Priority)
- [x] MultilevelQueues.java (MLQ, MLFQ)
- [x] AdvancedCPUScheduling.java (Lottery Scheduling, CFS)
- [x] RealTimeScheduling.java (EDF)

### 2. Page Replacement (`os.pagereplacement`)
- [x] FIFO.java
- [x] Optimal.java
- [x] LRU.java (Simulated via LinkedHashMap)
- [x] LFU.java (Simulated via PriorityQueue)
- [x] ClockSecondChance.java
- [x] BeladysAnomaly.java

### 3. Memory Management (`os.memorymanagement` / `os.memoryallocation`)
- [x] PagingTranslation.java
- [x] TLBSimulation.java
- [x] Segmentation.java
- [x] DemandPaging.java
- [x] ContiguousAllocation.java (First, Best, Worst Fit)
- [x] BuddySystem.java

### 4. Disk Scheduling & File Systems (`os.diskscheduling` / `os.filesystem` / `os.raid`)
- [x] DiskScheduling.java (FCFS, SSTF, SCAN, C-SCAN, LOOK, C-LOOK)
- [x] FileAllocation.java (Contiguous, Linked, Indexed)
- [x] FreeSpaceManagement.java (Bitmap, Linked List)
- [x] RaidSimulations.java (RAID 0, 1, 5, 6)

### 5. Deadlock & Synchronization (`os.deadlock` / `os.synchronization`)
- [x] BankersAlgorithm.java (Avoidance)
- [x] DeadlockDetection.java (RAG Cycle Detection)
- [x] CustomSemaphore.java
- [x] ProducerConsumer.java
- [x] DiningPhilosophers.java
- [x] ReadersWriters.java
- [x] PetersonsAlgorithm.java
- [x] BakeryAlgorithm.java
- [x] CASPrimitives.java (Compare-And-Swap)
- [x] TestAndSet.java
- [x] Spinlock.java
- [x] MonitorConcept.java

### 6. Concurrent Algos & Load Balancing (`os.synchronization.lockfree` / `os.loadbalancing`)
- [x] MichaelScottQueue.java
- [x] TreiberStack.java
- [x] DoubleCheckedLocking.java
- [x] RCUConcept.java
- [x] BarrierSync.java
- [x] ForkJoinParallelism.java
- [x] FuturePromise.java
- [x] WorkStealing.java (Explicit Simulation)
- [x] RoundRobinLoadBalancer.java
- [x] WeightedRoundRobin.java
- [x] LeastConnections.java
- [x] IPHashLoadBalancer.java

---

## PART D — NETWORKING / INTERNET PROTOCOL

### 1. Routing & Flow Control (`net.routing` / `net.flowcontrol`)
- [x] DistanceVectorRouting.java
- [x] LinkStateRouting.java
- [x] CountToInfinityProblem.java
- [x] SplitHorizon.java
- [x] PoisonReverse.java
- [x] StopAndWaitARQ.java
- [x] GoBackNARQ.java
- [x] SelectiveRepeatARQ.java
- [x] SlidingWindowProtocol.java

### 2. Congestion, Traffic, MAC (`net.congestioncontrol` / `net.trafficshaping` / `net.mac`)
- [x] TCPSlowStart.java
- [x] AIMD.java
- [x] TCPTahoe.java
- [x] TCPReno.java
- [x] LeakyBucket.java
- [x] TokenBucket.java
- [x] CSMACD.java
- [x] CSMACA.java

### 3. Layers & Protocols (`net.*`)
- [x] Checksum.java & CRC.java
- [x] SpanningTreeProtocol.java
- [x] FordFulkerson.java & EdmondsKarp.java
- [x] ARPSimulation.java
- [x] TCPConnectionStateMachine.java
- [x] IPFragmentation.java
- [x] FloodingMulticast.java & SpanningTreeMulticast.java
- [x] CristiansAlgorithm.java & BerkeleyAlgorithm.java
- [x] RecursiveDNSLookup.java & IterativeDNSLookup.java

---

## PART E & F — DISTRIBUTED SYSTEMS & PROBABILISTIC

### 1. Distributed Systems (`distributed.*`)
- [x] Paxos.java
- [x] Raft.java
- [x] BullyAlgorithm.java
- [x] RingAlgorithm.java
- [x] LamportTimestamps.java
- [x] VectorClocks.java
- [x] TwoPhaseCommit.java
- [x] ThreePhaseCommit.java
- [x] GossipProtocol.java

### 2. Probabilistic Data Structures (`probabilistic.*`)
- [x] CountMinSketch.java
- [x] HyperLogLog.java
