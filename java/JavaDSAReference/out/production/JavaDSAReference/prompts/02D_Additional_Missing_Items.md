# PROMPT 2D — Additional Missing Items (Java, using built-in functions)

Run this as its own Claude Code session, separate from 2A, 2B, and 2C. Paste the whole
prompt below as your first message.

This file collects items that were identified as gaps AFTER the main DSA/Algorithms
reference (02A) was already built — advanced/applied string-indexing techniques, an
alternate hashing strategy, and a classic graphics algorithm. They're kept here, separate
from 02A, and use Java's built-in data structures/functions rather than custom-from-scratch
ones, following the same split used in 02B and 02C (02A = custom-built DSA only;
everything else = built-ins allowed).

---

You are a senior Java engineer building a REFERENCE CODEBASE covering a set of advanced/
applied algorithms that were missed in earlier passes of a larger DSA+Algorithms reference.
Do NOT skip any item listed below.

## GLOBAL RULES

1. USE JAVA'S BUILT-IN DATA STRUCTURES FREELY (ArrayList, HashMap, arrays, etc.). These
   are applied/advanced techniques, not custom-DSA-building exercises.
2. Package per topic, `common` subpackage for shared pieces where relevant.
3. Every file: top comment block containing:
   - What this algorithm is, the approach/strategy, time/space complexity, a real-world
     analogy or example use case
   - WHEN TO USE THIS / APPLIES TO / VARIATIONS / COMBINATION WITH OTHER ALGORITHMS
   - PSEUDOCODE: a short language-agnostic pseudocode block for the core algorithm,
     BEFORE the Java implementation
   Then: fully commented Java implementation, and a `main`/`Runner` method exercising all
   methods/scenarios including edge cases with labeled printed output.

---

## PART — ADDITIONAL MISSING ITEMS

1. **AHO-CORASICK ALGORITHM** (package: `algorithms.strings.ahocorasick`)
   - Applies to: multi-pattern string search — searching for MANY patterns
     simultaneously in a single pass over the text
   - Combination: builds on a Trie structure (use `java.util.HashMap`-backed trie
     nodes) + KMP's failure-function idea, constructing "failure links" between trie
     nodes so mismatches jump to the next-best matching state instead of restarting
   - When to use: instead of running KMP/Rabin-Karp once per pattern (slow for many
     patterns), Aho-Corasick finds all pattern occurrences in one linear pass —
     e.g., scanning a document for hundreds of keywords at once (spam filters,
     intrusion detection, dictionary matching)

2. **SUFFIX ARRAY** (package: `algorithms.strings.suffixarray`)
   - A sorted array of all suffixes of a string (represented as starting indices,
     not full substrings, to save memory)
   - Applies to: fast substring existence/count queries, pattern matching
   - Combination: typically built together with an LCP (Longest Common Prefix)
     array, which records the shared-prefix length between adjacent suffixes in
     the sorted order — enables even faster queries (e.g., longest repeated
     substring) on top of the base suffix array
   - When to use: repeated substring queries on a fixed string where preprocessing
     cost is worth it (e.g., genome search, full-text search indexes)
   - Include both a naive O(n^2 log n) construction (sort suffixes directly using
     built-in `Arrays.sort` with a comparator) and a note on the existence of
     faster O(n log n) construction algorithms (e.g., prefix doubling), without
     requiring the faster version to be fully implemented — flag it as optional/
     bonus if time allows

3. **SUFFIX TREE** (package: `algorithms.strings.suffixtree`)
   - A compressed trie containing all suffixes of a string as paths from root to leaf
   - Applies to: same use cases as a suffix array (substring search, longest
     repeated substring, longest common substring across multiple strings) but
     with different tradeoffs
   - Comment explicitly comparing the two: "// Suffix Array = simpler to build and
     more memory-efficient, but needs the LCP array for some queries a suffix tree
     answers directly. Suffix Tree = faster queries (O(m) for a pattern of length
     m) and more powerful (multi-string generalized suffix trees exist), but is
     significantly harder to construct efficiently (e.g., Ukkonen's algorithm) and
     uses more memory."
   - A basic (not necessarily Ukkonen's linear-time) construction is acceptable —
     note in a comment that Ukkonen's algorithm is the real linear-time approach
     used in production, described conceptually but not required to implement in
     full if too complex

4. **CUCKOO HASHING** (package: `hashmap.cuckoo`)
   - An alternate collision-resolution strategy to chaining: two hash functions,
     two tables; on collision, the existing entry is "kicked out" (displaced) to
     its alternate location in the other table, recursively, until a cycle is
     detected and the whole structure is rehashed with new hash functions
   - put, get, remove, containsKey
   - Comment comparing worst-case O(1) lookup (cuckoo hashing guarantees this,
     since an element is always in one of exactly two locations) vs chaining's
     O(1) average but O(n) worst case
   - When to use: lookup-heavy workloads where worst-case guarantees matter more
     than insertion speed (insertion can trigger cascading displacement or a full
     rehash)

5. **BRESENHAM'S LINE ALGORITHM** (package: `geometry.bresenham`)
   - Basic computer graphics: draw a line between two points on a discrete pixel
     grid using only integer arithmetic (no floating point/no trigonometry)
   - Applies to: rasterization — converting a mathematical line into the nearest
     set of pixels on a screen/grid
   - Pseudocode should show the core decision-variable/error-accumulator technique
     that avoids division and floating-point rounding entirely

6. **KD-TREE** (package: `spatial.kdtree`)
   - k-dimensional spatial partitioning: insert, nearest-neighbor search, range
     search
   - Applies to: multi-dimensional point data (e.g., 2D/3D coordinates)
   - When to use: nearest-neighbor or range queries over points in low-to-moderate
     dimensional space (performance degrades in very high dimensions — the
     "curse of dimensionality")

7. **QUAD-TREE** (package: `spatial.quadtree`)
   - 2D spatial partitioning, recursively divides space into 4 quadrants
   - Applies to: collision detection in games, image compression, spatial indexing
   - When to use: 2D spatial data with uneven density (finer subdivision where
     points are dense, coarser where sparse)

8. **R-TREE** (package: `spatial.rtree`)
   - Bounding-box based spatial indexing; groups nearby objects and represents
     them with a minimum bounding rectangle at each tree level
   - Applies to: real spatial databases/GIS systems, rectangle/region queries
   - When to use: indexing extended objects (rectangles/regions), not just points
     — this is the actual structure PostGIS and similar spatial DB extensions use

9. **BK-TREE** (package: `bktree`)
   - Tree structure for fast approximate string matching using edit distance and
     the triangle inequality property; insert, search within a given edit-distance
     threshold
   - Comment explaining why this beats brute-force comparison against every string
     in a dataset — this is the actual production approach behind fast fuzzy search

10. **STRING SIMILARITY / DISTANCE / FUZZY MATCHING** (package: `algorithms.strings.similarity`)
    - **Hamming Distance** — count of differing characters at the same position
      between two EQUAL-LENGTH strings; applies to: fixed-length strings, binary
      data, error detection; when to use: only valid when both strings are the
      same length
    - **Levenshtein Distance (Edit Distance)** — cross-reference: full DP
      implementation lives in file 02A, Part B, Dynamic Programming section;
      comment here: "// See algorithms.dp.EditDistance in the DSA prompt — the
      general-purpose string distance metric allowing insert/delete/replace"
    - **Damerau-Levenshtein Distance** — like Levenshtein, but ALSO allows
      transposition of two adjacent characters as a single operation; applies to:
      spell-checkers, typo correction; combination: extends the Levenshtein DP
      table with one extra transposition check per cell
    - **Jaro Distance + Jaro-Winkler Distance** — similarity score (0 to 1) based
      on matching characters within a proximity window and transpositions, with
      Winkler adding a bonus for common prefixes; applies to: short strings,
      classically name matching; when to use: fuzzy matching of names/short
      identifiers, NOT long text
    - **Longest Common Substring** — NOT the same as Longest Common Subsequence
      (note explicitly: substring must be CONTIGUOUS, subsequence need not be);
      DP-based, resets to 0 on mismatch instead of carrying forward; applies to:
      plagiarism detection, DNA sequence matching, diff tools
    - **Sørensen-Dice Coefficient** — 2×|intersection| / (|A|+|B|) on token/n-gram
      sets; applies to: set/string similarity; comment comparing against Jaccard
      Similarity (different weighting of intersection vs union)
    - **Jaccard Similarity** — |intersection| / |union| on token/n-gram sets
    - **Cosine Similarity** (on character/word n-grams or token vectors) — treats
      each string as a vector and measures the cosine of the angle between two
      vectors; applies to: document similarity, search relevance ranking; NOT
      ideal for short strings/typos; combination: Cosine Similarity = Vector
      representation (n-grams or bag-of-words) + dot product / magnitude formula;
      cross-reference: pairs with TF-IDF (file 02C, Part L) for real document
      comparison
    - **N-gram Similarity** (character-level n-grams, e.g., bigrams/trigrams) —
      break both strings into overlapping n-character chunks, compare set overlap
      via Jaccard/Dice; applies to: fuzzy search/autocomplete/"did you mean"
      features
    - **Soundex Algorithm** — phonetic algorithm, encodes a string based on how it
      SOUNDS, not how it's spelled; applies to: name matching where spelling
      varies but pronunciation is similar (genealogy databases, old-school search
      systems)
    - **Metaphone Algorithm** (bonus/advanced) — improved phonetic algorithm over
      Soundex, more accurate for modern English
    - **Bitap Algorithm (Shift-Or / Shift-And)** — bit-parallel approximate string
      matching (used by tools like `agrep`); distinct technique from Levenshtein
      DP; applies to: fast approximate matching with a small edit-distance
      tolerance
    - **Levenshtein Automaton** (advanced) — builds a finite automaton accepting
      all strings within edit distance k of a target; used internally by
      production fuzzy-search engines (Lucene/Elasticsearch) for speed
    - **Wildcard Pattern Matching** (`*` and `?` support) — DP-based, different
      technique from KMP/Rabin-Karp which need exact patterns; applies to:
      glob-style pattern matching (file search, simple query languages)

    **FUZZY SEARCH** — explicit comment block explaining the term as a whole:
    `// Fuzzy search is not a single algorithm — it's the general problem of
    finding approximate matches (not exact matches) between a query and a
    dataset. It's typically implemented using ONE or A COMBINATION of the
    algorithms above: Levenshtein/Damerau-Levenshtein (typo tolerance), N-gram +
    Jaccard/Dice (partial/substring tolerance), Soundex/Metaphone (phonetic
    tolerance), or Jaro-Winkler (short-string/name tolerance). A real fuzzy
    search engine typically combines edit-distance thresholding with n-gram
    indexing OR a BK-Tree (item 9 above) for speed, since running edit distance
    against every record in a large dataset is too slow.`

    - `FuzzySearch` demo class (package: `algorithms.strings.similarity.fuzzysearch`)
      — given a query string and a list of candidates, return matches ranked by
      similarity, using a combination of: BK-Tree pre-filtering (item 9) OR
      n-gram pre-filtering, THEN exact Levenshtein distance only on the filtered
      subset. Top-of-file comment must explicitly demonstrate this COMBINATION
      approach. Use `java.util.PriorityQueue`/`ArrayList` for ranking results —
      no custom structures needed here.

11. **REAL-WORLD APPLIED ALGORITHMS** (package: `algorithms.realworld`)
    - PageRank — iterative; applies to: graph ranking; combination: Graph
      (adjacency list, `java.util.HashMap`) + iterative power iteration
    - Collaborative Filtering (basic user-based/item-based recommendation) —
      combination: builds on Cosine Similarity (item 10 above), applied to
      rating vectors
    - MapReduce paradigm (conceptual simulation) — Map + Shuffle + Reduce phase
      on a simple word-count example, single-machine simulation of the
      distributed concept
    - Reservoir Sampling — randomly sample k items from a stream of unknown
      length in one pass; applies to: streaming data
    - (NOTE: Boyer-Moore Voting Algorithm stays in file 02A — it's a classic
      array/DSA interview technique, not a domain-specific applied algorithm.)

---

## EXECUTION APPROACH
Go item-by-item, in the order listed. After each file, wait for me to say "next" before
continuing.
