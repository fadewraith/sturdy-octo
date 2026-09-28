# PROMPT 2C — Math, Geometry, Cryptography, Compilers, DB Internals, ML, Compression (Java, using built-in functions)

Run this as its own Claude Code session, separate from 2A and 2B. Paste the whole prompt below.

---

You are a senior engineer building an EXHAUSTIVE REFERENCE CODEBASE covering Numerical/Math
Algorithms, Computational Geometry, Cryptography, Compiler/Parsing Algorithms, Database
Internals, Machine Learning Fundamentals, and Compression Algorithms — all implemented FROM
SCRATCH in Java for educational/interview-prep purposes. Do NOT skip any item, including
"bonus"/"advanced"/"conceptual" ones.

## GLOBAL RULES (different from the DSA/Algorithms prompt — read carefully)
1. USE JAVA'S BUILT-IN DATA STRUCTURES FREELY in this file (ArrayList, HashMap,
   arrays, BigInteger where relevant for math/crypto, etc.). This file is about
   ALGORITHM LOGIC in math, geometry, crypto, compilers, DB internals, ML, and
   compression — not data-structure-building practice. Do not reimplement custom
   structures here; use whatever built-in fits naturally.
2. Inbuilt Function Exception Rule (for genuinely unavoidable low-level operations,
   not collections): where a real production concern applies (e.g., real
   cryptographic primitives must use `java.security`/`javax.crypto` for actual
   security), implement the educational version of the algorithm's LOGIC, and add a
   comment `// NOTE: production-grade version should use Java's built-in <X> because
   <reason>`. This is ESPECIALLY important in the Cryptography section — every crypto
   file must say so explicitly.
3. Package per topic, `common` subpackage for shared pieces.
4. Every file: top comment block containing:
   - What this algorithm/concept is, the approach/strategy, time/space complexity,
     a real-world analogy or example use case
   - WHEN TO USE THIS / APPLIES TO / VARIATIONS / COMBINATION WITH OTHER ALGORITHMS
   - **PSEUDOCODE**: a short language-agnostic pseudocode block for the core
     algorithm (e.g., RSA key generation, Shunting Yard, K-Means, Huffman Coding,
     whichever applies to that file) BEFORE the Java implementation
   Then: fully commented Java implementation, and a `main`/`Runner` method exercising
   all methods/scenarios including edge cases with labeled printed output.

---

## PART G — NUMERICAL / MATH ALGORITHMS

1. **NUMBER THEORY** (package: `math.numbertheory`)
   - GCD/LCM — Euclidean Algorithm (iterative AND recursive), Extended Euclidean
     Algorithm (finds Bezout coefficients)
   - Sieve of Eratosthenes (prime generation up to N)
   - Segmented Sieve (primes in a range, for large N)
   - Primality Testing: trial division, Fermat's Little Theorem test, Miller-Rabin
     primality test (probabilistic)
   - Modular Exponentiation (Fast/Binary Exponentiation) — applies to: cryptography,
     large power computations under a modulus
   - Modular Multiplicative Inverse (Extended Euclidean OR Fermat's Little Theorem
     when modulus is prime)
   - Chinese Remainder Theorem
   - Prime Factorization (trial division AND Pollard's Rho for large numbers)
   - Euler's Totient Function
   - Karatsuba's Algorithm (fast integer multiplication) — applies to: large
     integers; divide-and-conquer approach beating naive O(n^2) grade-school
     multiplication, achieving ~O(n^1.585); combination: same divide-and-conquer
     spirit as Strassen's Matrix Multiplication below, but applied to digit-wise
     integer multiplication instead of matrices; when to use: multiplying very
     large numbers (arbitrary-precision arithmetic, cryptographic computations)

2. **MATRIX ALGORITHMS** (package: `math.matrix`)
   - Matrix Multiplication (naive O(n^3))
   - Strassen's Matrix Multiplication (divide and conquer, sub-cubic)
   - Matrix Exponentiation (fast power applied to matrices — applies to: computing
     nth Fibonacci in O(log n), solving linear recurrences)
   - Matrix Transpose, Determinant (cofactor expansion AND Gaussian elimination)
   - Gaussian Elimination (solving systems of linear equations)

3. **ROOT FINDING / OPTIMIZATION** (package: `math.rootfinding`)
   - Bisection Method
   - Newton-Raphson Method
   - Fast Inverse Square Root (the famous bit-hack version, with the "0x5f3759df"
     magic number explained historically)

4. **COMBINATORICS** (package: `math.combinatorics`)
   - Factorial-based nCr/nPr computation
   - Pascal's Triangle generation
   - Catalan Numbers (recurrence + applications: valid parentheses count, BST count)

5. **FFT — FAST FOURIER TRANSFORM** (package: `math.fft`) — advanced
   - Applies to: fast polynomial multiplication, signal processing; when to use:
     multiplying two large polynomials/numbers faster than O(n^2)
   - Implement the recursive Cooley-Tukey version, heavily commented

---

## PART H — COMPUTATIONAL GEOMETRY

1. **CONVEX HULL** (package: `geometry.convexhull`)
   - Graham Scan
   - Jarvis March (Gift Wrapping)
   - Comment comparing time complexity (Graham Scan O(n log n) vs Jarvis March
     O(nh) where h = hull points)

2. **LINE / SEGMENT ALGORITHMS** (package: `geometry.lines`)
   - Line Intersection detection (using cross products/orientation)
   - Point-in-Polygon test (ray casting method)
   - Closest Pair of Points (divide and conquer — O(n log n) vs naive O(n^2))

3. **SWEEP LINE ALGORITHM** (package: `geometry.sweepline`)
   - Applies to: detecting all intersections among a set of line segments efficiently;
     combination: Sweep Line + a sorted structure (BST/TreeMap) to track active segments

   (NOTE: Bresenham's Line Algorithm moved to file 02D, grouped with the other
   "additional missing items" identified later in this reference.)

---

## PART I — CRYPTOGRAPHY

**Top-level comment required in every file in this section:**
`// EDUCATIONAL IMPLEMENTATION ONLY. Do not use this code for real security-critical
work. Production code must use java.security / javax.crypto (MessageDigest, Cipher,
KeyPairGenerator, etc.), which are the unavoidable built-ins here per the Inbuilt
Function Exception Rule — real cryptography requires audited, constant-time
implementations that are extremely difficult to get right from scratch.`

1. **HASHING** (package: `crypto.hashing`)
   - Simplified SHA-256-style hash from scratch (educational, heavily commented on
     the compression function steps)
   - MD5 (same treatment — note it's cryptographically broken for real security use)

2. **SYMMETRIC ENCRYPTION** (package: `crypto.symmetric`)
   - Caesar Cipher, Vigenere Cipher (classical, for teaching the concept)
   - XOR Cipher
   - Simplified AES round structure (educational only)

3. **ASYMMETRIC ENCRYPTION** (package: `crypto.asymmetric`)
   - RSA — key generation (using `math.numbertheory`'s prime generation + modular
     exponentiation), encryption, decryption; combination: RSA = Number Theory
     (primes, modular exponentiation, modular inverse) + Euler's Totient
   - Diffie-Hellman Key Exchange — using modular exponentiation

4. **DIGITAL SIGNATURES** (package: `crypto.signatures`)
   - Basic RSA-based signing/verification concept (sign with private key, verify
     with public key)

---

## PART J — COMPILER / PARSING ALGORITHMS

1. **EXPRESSION PARSING** (package: `compilers.expressionparsing`)
   - Shunting Yard Algorithm (infix to postfix/prefix conversion, using a custom
     Stack)
   - Postfix/Prefix Expression Evaluation (using a custom Stack)
   - Applies to: calculator implementations, compiler expression evaluation

2. **LEXICAL ANALYSIS** (package: `compilers.lexer`)
   - Basic Tokenizer — split source code/text into tokens (numbers, operators,
     identifiers) using a simple state machine

3. **PARSING** (package: `compilers.parser`)
   - Recursive Descent Parser (basic arithmetic grammar: parse and build an
     expression tree, then evaluate it)
   - Comment connecting this to tree structures (expression trees are just trees)

---

## PART K — DATABASE INTERNALS

1. **B+ TREE** (package: `db.bplustree`)
   - Distinct from a plain B-Tree — explain explicitly: B+ Tree keeps ALL data in
     leaf nodes (internal nodes only store keys for navigation), and leaf nodes are
     linked together for fast range scans — this is what real databases (MySQL
     InnoDB, PostgreSQL indexes) actually use
   - insert, search, range query (using the leaf-node linked-list traversal)

2. **WRITE-AHEAD LOGGING** (package: `db.wal`) — conceptual/simplified
   - Simulate logging changes before applying them, replaying the log on "crash
     recovery" to demonstrate durability

3. **MVCC — MULTI-VERSION CONCURRENCY CONTROL** (package: `db.mvcc`) — conceptual/simplified
   - Simulate multiple versions of a row with transaction IDs; demonstrate how
     readers see a consistent snapshot while writers create new versions

4. **QUERY OPTIMIZATION BASICS** (package: `db.queryoptimization`)
   - Join Order optimization concept — demonstrate why join order matters using a
     simple cost-estimation example (smaller intermediate result sets first)

---

## PART L — MACHINE LEARNING FUNDAMENTALS (from-scratch, math-only)

**Top-level comment required for this whole section:**
`// These are simplified, from-scratch educational implementations to understand the
underlying math. Real-world ML work uses libraries (scikit-learn, TensorFlow, etc.) —
these exist here purely for conceptual completeness, not as production advice.`

1. **K-MEANS CLUSTERING** (package: `ml.kmeans`)
   - Centroid initialization, assignment step, update step, convergence check

2. **K-NEAREST NEIGHBORS** (package: `ml.knn`)
   - Distance calculation (Euclidean), find K nearest points, majority-vote
     classification

3. **LINEAR REGRESSION** (package: `ml.linearregression`)
   - Gradient Descent from scratch (cost function, gradient computation, weight
     updates) — no external ML libraries

4. **LOGISTIC REGRESSION** (package: `ml.logisticregression`)
   - Sigmoid function, gradient descent for classification

5. **TF-IDF** (package: `ml.tfidf`)
   - Term Frequency-Inverse Document Frequency weighting scheme
   - Applies to: making raw word-count vectors meaningful for document comparison;
     combination: TF-IDF vectors + Cosine Similarity (from the DSA/Algorithms prompt's
     string-similarity section) = the standard document-similarity pipeline

---

## PART M — ADVANCED PATHFINDING, GAME THEORY & APPROXIMATION ALGORITHMS
(moved here from the DSA/Algorithms prompt — these are applied/advanced techniques,
built using Java's built-in collections, not custom-from-scratch structures)

1. **ADVANCED PATHFINDING** (package: `algorithms.graph.advanced`)
   - Bidirectional Search (BFS/Dijkstra run simultaneously from start AND goal,
     meeting in the middle — roughly halves the search space explored)
   - Bidirectional A* — same idea combined with A*'s heuristic; when to use: large
     graphs where single-direction A* explores too much (e.g., long-distance routing)
   - IDA* (Iterative Deepening A*) — combines A*'s heuristic with iterative
     deepening's low memory usage; when to use: memory-constrained pathfinding
   - Jump Point Search (JPS) — optimization specific to uniform-cost GRID maps
     (e.g., game pathfinding); skips over "uninteresting" nodes in straight lines
   - D* Lite — dynamic replanning pathfinding; when to use: the graph/map changes
     DURING navigation (robotics, real-time obstacle avoidance)
   - Contraction Hierarchies (conceptual/simplified) — technique behind real GPS
     routing systems; precomputes a hierarchy of "shortcut" edges so queries skip
     most of the graph
   - Comment comparing all of these against plain Dijkstra's/A*: "// These aren't
     separate algorithms so much as OPTIMIZATIONS/ADAPTATIONS of Dijkstra's and A*
     for specific real-world constraints (memory, dynamic changes, precomputation
     budget, grid structure)."
   - Use `java.util.PriorityQueue`, `java.util.HashMap`, etc. for the underlying
     open/closed sets and priority ordering — no custom structures needed here.

2. **GAME THEORY / SEARCH** (package: `algorithms.gametheory`)
   - Minimax Algorithm (game AI decision-making) — applies to: two-player
     zero-sum games with perfect information (e.g., Tic-Tac-Toe, Chess);
     when to use: need the optimal move assuming an optimal opponent
   - Alpha-Beta Pruning (optimization over minimax) — combination: Minimax +
     branch-pruning to skip subtrees that can't affect the final decision
   - Monte Carlo Tree Search (conceptual, simplified) — applies to: large game
     trees where exhaustive minimax is infeasible (e.g., Go); when to use:
     huge branching factor, use random simulations to estimate move quality

3. **APPROXIMATION ALGORITHMS** (package: `algorithms.approximation`) — for
   NP-hard problems, bonus/advanced
   - TSP approximation — Nearest Neighbor heuristic, 2-opt improvement;
     applies to: Traveling Salesman Problem where exact solution is infeasible
     at scale
   - Vertex Cover approximation — 2-approximation via maximal matching
   - Set Cover greedy approximation — repeatedly pick the set covering the most
     uncovered elements
   - Comment on all three: "// These don't guarantee the optimal solution, only
     a solution within a proven bound of optimal, in exchange for tractable
     runtime on NP-hard problems."

---

## PART N — COMPRESSION ALGORITHMS

1. **HUFFMAN CODING** (package: `compression.huffman`)
   - Build frequency table, build Huffman tree (uses a min-heap), generate codes,
     encode/decode

2. **RUN-LENGTH ENCODING** (package: `compression.rle`)
   - Basic RLE encode/decode

3. **LZ77 / LZ78** (package: `compression.lz`)
   - Dictionary-based compression, sliding window (LZ77) and explicit dictionary
     (LZ78)

4. **LZW** (package: `compression.lzw`)
   - Lempel-Ziv-Welch — used in GIF/older ZIP formats

---

## EXECUTION APPROACH
Go part-by-part, topic-by-topic, in the order listed (G, H, I, J, K, L, M, N). After each
file, wait for me to say "next" before continuing. If a later item cross-references a
structure/algorithm from the other two prompts (e.g., min-heap for Huffman, Cosine
Similarity for TF-IDF), assume it exists and reference it, or reimplement a minimal
version inline if needed — note in a comment which external file it would normally reuse.
