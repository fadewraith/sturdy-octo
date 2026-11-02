# PROMPT 02C - MATH, GEOMETRY, CRYPTO, COMPILERS, DB, ML

This workspace contains all the advanced domain-specific logic and computational algorithms requested in 02C.

> **Note:** Any algorithms from the 02C syllabus that are traditionally built as "from-scratch" Data Structures (like `B+ Tree`, `Shunting Yard`, `Minimax`, `Convex Hull`) were explicitly built inside the strict `02A` workspace instead. We rebuilt some of them here anyway for format consistency!

---

## PART G — NUMERICAL / MATH ALGORITHMS

### 1. Number Theory (`math.numbertheory`)
- [x] EuclideanAlgorithm.java
- [x] ExtendedEuclideanAlgorithm.java
- [x] SieveOfEratosthenes.java
- [x] SegmentedSieve.java
- [x] PrimalityTesting.java
- [x] ModularExponentiation.java
- [x] ModularMultiplicativeInverse.java
- [x] ChineseRemainderTheorem.java
- [x] PrimeFactorization.java
- [x] EulersTotientFunction.java
- [x] KaratsubaAlgorithm.java

### 2. Matrix Algorithms (`math.matrix`)
- [x] MatrixMultiplication.java
- [x] StrassenMatrixMultiplication.java
- [x] MatrixExponentiation.java
- [x] MatrixTranspose.java
- [x] MatrixDeterminant.java
- [x] GaussianElimination.java
- [x] LUDecomposition.java (P A = L U with partial pivoting, solve, determinant, inverse)

### 3. Root Finding & Optimization (`math.rootfinding`)
- [x] BisectionMethod.java
- [x] NewtonRaphsonMethod.java
- [x] FastInverseSquareRoot.java
- [x] NumericalIntegration.java (`math.integration`: trapezoid, Simpson, adaptive Simpson, Romberg, Gauss-Legendre)

### 4. Combinatorics (`math.combinatorics`)
- [x] CombinationsPermutations.java
- [x] PascalsTriangle.java
- [x] CatalanNumbers.java
- [x] LucasTheorem.java (C(n, k) mod p: factorial tables + Lucas theorem)

### 5. Fast Fourier Transform (`math.fft`)
- [x] FastFourierTransform.java

---

## PART H — COMPUTATIONAL GEOMETRY

### 1. Convex Hull (`geometry.convexhull`)
- [x] GrahamScan.java
- [x] JarvisMarch.java
- [x] MonotoneChain.java (Andrew's monotone chain, exact integer arithmetic)

### 2. Lines & Segments (`geometry.lines`)
- [x] LineIntersection.java
- [x] PointInPolygon.java
- [x] ClosestPairOfPoints.java

### 3. Sweep Line (`geometry.sweepline`)
- [x] SweepLineAlgorithm.java

### 4. Polygons (`geometry.polygon`)
- [x] PolygonGeometry.java (shoelace area, orientation, centroid, Pick's theorem, point-segment distance)

### 5. Triangulation (`geometry.delaunay`)
- [x] DelaunayTriangulation.java (Bowyer-Watson with a vertex at infinity)

---

## PART I — CRYPTOGRAPHY (Educational Only)

### 1. Hashing (`crypto.hashing`)
- [x] EducationalSHA256.java (full FIPS 180-4, matches MessageDigest; digest(byte[]) for raw bytes)
- [x] EducationalMD5.java (full RFC 1321, matches MessageDigest)

### 2. Symmetric Encryption (`crypto.symmetric`)
- [x] CaesarCipher.java
- [x] VigenereCipher.java
- [x] XORCipher.java
- [x] EducationalAES.java (full AES-128 encrypt + decrypt, matches javax.crypto)
- [x] AESModes.java (ECB, CBC + PKCS#7, CTR; matches javax.crypto)

### 3. Asymmetric & Signatures (`crypto.asymmetric` / `crypto.signatures`)
- [x] RSA.java
- [x] DiffieHellman.java
- [x] RSASignatureConcept.java
- [x] ECDH.java (P-256 point arithmetic, Montgomery ladder, matches the JDK)

### 4. MACs, Key Derivation & Secret Sharing (`crypto.mac` / `crypto.kdf` / `crypto.secretsharing`)
- [x] HMAC.java (HMAC-SHA256, constant-time verify)
- [x] PBKDF2.java (PBKDF2-HMAC-SHA256)
- [x] ShamirSecretSharing.java (k-of-n threshold scheme)

---

## PART J — COMPILER / PARSING ALGORITHMS

### 1. Expression Parsing (`compilers.expressionparsing`)
- [x] ShuntingYard.java
- [x] PostfixEvaluator.java

### 2. Lexer & Parser (`compilers.lexer` / `compilers.parser`)
- [x] BasicTokenizer.java
- [x] RecursiveDescentParser.java

### 3. Interpreters, Regex & Parser Generators (`compilers.interpreter` / `compilers.regex` / `compilers.pratt` / `compilers.ll1`)
- [x] ASTInterpreter.java (lexer, parser, scoped tree-walking evaluator)
- [x] RegexEngine.java (Thompson NFA, NFA simulation, lazy DFA)
- [x] PrattParser.java (binding powers: prefix, infix, postfix, right-assoc)
- [x] LL1Parser.java (FIRST/FOLLOW, parse table, conflict detection)
- [x] SLRParser.java (`compilers.lr`: LR(0) items, ACTION/GOTO tables, shift/reduce conflicts)

---

## PART K — DATABASE INTERNALS

### 1. Core Engine Concepts (`db.*`)
- [x] BPlusTree.java (leaf-linked range scans, upsert on duplicate keys)
- [x] WriteAheadLog.java
- [x] MVCC.java
- [x] JoinOrderOptimizer.java
- [x] LSMTree.java (memtable, SSTables, tombstones, compaction)
- [x] JoinAlgorithms.java (nested loop, block NL, sort-merge, hash, Grace hash)
- [x] BufferPool.java (pin counts, dirty pages, clock eviction)
- [x] TwoPhaseLocking.java (strict 2PL, S/X locks, waits-for deadlock detection)
- [x] ARIESRecovery.java (`db.recovery`: analysis / redo / undo with CLRs, steal + no-force)

---

## PART L — MACHINE LEARNING FUNDAMENTALS

### 1. ML Algorithms (`ml.*`)
- [x] KMeansClustering.java
- [x] KNearestNeighbors.java
- [x] LinearRegression.java
- [x] LogisticRegression.java
- [x] TfIdf.java
- [x] DecisionTree.java (CART with Gini)
- [x] NaiveBayes.java (multinomial, Laplace smoothing, log space)
- [x] NeuralNetwork.java (MLP with backpropagation, learns XOR)
- [x] PCA.java (covariance + Jacobi eigenvalue method)
- [x] DBSCAN.java (density clustering with noise)
- [x] Metrics.java (confusion matrix, precision/recall/F1, ROC AUC, log loss, regression metrics)

---

## PART N — COMPRESSION ALGORITHMS

### 1. Compression (`compression.*`)
- [x] HuffmanCoding.java
- [x] RunLengthEncoding.java
- [x] LZ77.java
- [x] LZ78.java
- [x] LZW.java
- [x] BurrowsWheeler.java (BWT, inverse via LF mapping, move-to-front)
- [x] ArithmeticCoding.java (integer coder, near-entropy output)
- [x] Base64AndVarint.java (Base64 std/URL-safe, LEB128 varint, ZigZag)

---

## PART M — ADVANCED PATHFINDING, GAME THEORY & APPROXIMATION ALGORITHMS

### 1. Advanced Pathfinding (`algorithms.graph.advanced`)
- [x] BidirectionalSearch.java
- [x] BidirectionalAStar.java
- [x] IDAStar.java
- [x] JumpPointSearch.java (8-directional JPS, same cost as A*)
- [x] DStarLite.java (optimized D* Lite with incremental replanning)
- [x] ContractionHierarchies.java (edge-difference ordering, witness search, bidirectional query)
- [x] JohnsonsAlgorithm.java (all-pairs shortest paths with negative edges: Bellman-Ford potentials + Dijkstra)
- [x] TwoSAT.java (implication graph + iterative Tarjan SCC)
- [x] HeavyLightDecomposition.java (path sum/max, LCA, subtree queries, point updates)

### 2. Game Theory (`algorithms.gametheory.advanced`)
- [x] Minimax.java
- [x] AlphaBetaPruning.java
- [x] MonteCarloTreeSearch.java

### 3. Approximation Algorithms (`algorithms.approximation`)
- [x] TSPApproximation.java
- [x] VertexCoverApproximation.java
- [x] SetCoverApproximation.java
- [x] ChristofidesTSP.java (1.5-approximation: MST + min-weight matching + Euler tour)
- [x] BinPacking.java (Next/First/Best Fit, First Fit Decreasing)
- [x] Metaheuristics.java (2-opt, simulated annealing, genetic algorithm on TSP)
