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

### 3. Root Finding & Optimization (`math.rootfinding`)
- [x] BisectionMethod.java
- [x] NewtonRaphsonMethod.java
- [x] FastInverseSquareRoot.java

### 4. Combinatorics (`math.combinatorics`)
- [x] CombinationsPermutations.java
- [x] PascalsTriangle.java
- [x] CatalanNumbers.java

### 5. Fast Fourier Transform (`math.fft`)
- [x] FastFourierTransform.java

---

## PART H — COMPUTATIONAL GEOMETRY

### 1. Convex Hull (`geometry.convexhull`)
- [x] GrahamScan.java
- [x] JarvisMarch.java

### 2. Lines & Segments (`geometry.lines`)
- [x] LineIntersection.java
- [x] PointInPolygon.java
- [x] ClosestPairOfPoints.java

### 3. Sweep Line (`geometry.sweepline`)
- [x] SweepLineAlgorithm.java

---

## PART I — CRYPTOGRAPHY (Educational Only)

### 1. Hashing (`crypto.hashing`)
- [x] EducationalSHA256.java
- [x] EducationalMD5.java

### 2. Symmetric Encryption (`crypto.symmetric`)
- [x] CaesarCipher.java
- [x] VigenereCipher.java
- [x] XORCipher.java
- [x] EducationalAES.java

### 3. Asymmetric & Signatures (`crypto.asymmetric` / `crypto.signatures`)
- [x] RSA.java
- [x] DiffieHellman.java
- [x] RSASignatureConcept.java

---

## PART J — COMPILER / PARSING ALGORITHMS

### 1. Expression Parsing (`compilers.expressionparsing`)
- [x] ShuntingYard.java
- [x] PostfixEvaluator.java

### 2. Lexer & Parser (`compilers.lexer` / `compilers.parser`)
- [x] BasicTokenizer.java
- [x] RecursiveDescentParser.java

---

## PART K — DATABASE INTERNALS

### 1. Core Engine Concepts (`db.*`)
- [x] BPlusTree.java (Leaf-linked range scans)
- [x] WriteAheadLog.java
- [x] MVCC.java
- [x] JoinOrderOptimizer.java

---

## PART L — MACHINE LEARNING FUNDAMENTALS

### 1. ML Algorithms (`ml.*`)
- [x] KMeansClustering.java
- [x] KNearestNeighbors.java
- [x] LinearRegression.java
- [x] LogisticRegression.java
- [x] TfIdf.java

---

## PART M/N — COMPRESSION ALGORITHMS

### 1. Compression (`compression.*`)
- [x] HuffmanCoding.java
- [x] RunLengthEncoding.java
- [x] LZ77.java
- [x] LZ78.java
- [x] LZW.java

---

## PART M � ADVANCED PATHFINDING, GAME THEORY & APPROXIMATION ALGORITHMS

### 1. Advanced Pathfinding (lgorithms.graph.advanced)
- [x] BidirectionalSearch.java
- [x] BidirectionalAStar.java
- [x] IDAStar.java
- [x] JumpPointSearch.java
- [x] DStarLite.java
- [x] ContractionHierarchies.java

### 2. Game Theory (lgorithms.gametheory)
- [x] Minimax.java
- [x] AlphaBetaPruning.java
- [x] MonteCarloTreeSearch.java

### 3. Approximation Algorithms (lgorithms.approximation)
- [x] TSPApproximation.java
- [x] VertexCoverApproximation.java
- [x] SetCoverApproximation.java
