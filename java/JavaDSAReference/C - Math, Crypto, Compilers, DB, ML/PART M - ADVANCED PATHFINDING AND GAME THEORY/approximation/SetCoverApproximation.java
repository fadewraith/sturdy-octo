package algorithms.approximation;

/**
 * WHAT IT IS: A greedy approximation algorithm for the Minimum Set Cover problem.
 * STRATEGY: Greedily pick the set that covers the maximum number of remaining uncovered elements until the entire universe of elements is covered.
 * TIME/SPACE COMPLEXITY: O(N * M) time where N is elements and M is number of sets. O(N + M) space.
 * REAL-WORLD ANALOGY / USE CASE: Selecting the minimum number of employees to form a team covering all required skills for a project.
 * WHEN TO USE / COMBINATION: When finding the exact minimum set cover is intractable (NP-hard). This greedy approach gives an O(log N) approximation ratio.
 * 
 * PSEUDOCODE:
 * covered = {}
 * cover_sets = []
 * while covered != universe:
 *   pick set S that maximizes |S - covered|
 *   add S to cover_sets
 *   covered = covered UNION S
 * return cover_sets
 */
// These don't guarantee the optimal solution, only a solution within a proven bound of optimal, in exchange for tractable runtime on NP-hard problems.

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetCoverApproximation {

    public static List<Set<Integer>> greedySetCover(Set<Integer> universe, List<Set<Integer>> sets) {
        List<Set<Integer>> cover = new ArrayList<>();
        Set<Integer> coveredElements = new HashSet<>();
        
        // Prevent infinite loops if universe is un-coverable
        Set<Integer> reachableUniverse = new HashSet<>();
        for (Set<Integer> s : sets) reachableUniverse.addAll(s);
        if (!reachableUniverse.containsAll(universe)) {
            throw new IllegalArgumentException("Universe cannot be covered by the given sets.");
        }

        List<Set<Integer>> remainingSets = new ArrayList<>(sets);

        while (!coveredElements.containsAll(universe)) {
            Set<Integer> bestSet = null;
            int maxUncovered = 0;
            
            for (Set<Integer> s : remainingSets) {
                int uncoveredCount = 0;
                for (Integer elem : s) {
                    if (universe.contains(elem) && !coveredElements.contains(elem)) {
                        uncoveredCount++;
                    }
                }
                
                if (uncoveredCount > maxUncovered) {
                    maxUncovered = uncoveredCount;
                    bestSet = s;
                }
            }
            
            if (bestSet != null) {
                cover.add(bestSet);
                coveredElements.addAll(bestSet);
                remainingSets.remove(bestSet); // Optional optimization
            } else {
                break; // Should not happen given the reachable check
            }
        }
        
        return cover;
    }

    public static void main(String[] args) {
        System.out.println("--- Set Cover Greedy Approximation ---");
        
        // Edge Case 1: Empty Universe
        System.out.println("Edge Case 1: Empty Universe");
        System.out.println("Cover: " + greedySetCover(new HashSet<>(), new ArrayList<>()));
        
        // Normal Case: Skills mapping
        System.out.println("\nNormal Case: Covering universe {1, 2, 3, 4, 5}");
        Set<Integer> universe = new HashSet<>(Set.of(1, 2, 3, 4, 5));
        
        Set<Integer> s1 = new HashSet<>(Set.of(1, 2, 3));
        Set<Integer> s2 = new HashSet<>(Set.of(2, 4));
        Set<Integer> s3 = new HashSet<>(Set.of(3, 4));
        Set<Integer> s4 = new HashSet<>(Set.of(4, 5));
        
        List<Set<Integer>> sets = new ArrayList<>();
        sets.add(s1); sets.add(s2); sets.add(s3); sets.add(s4);
        
        List<Set<Integer>> result = greedySetCover(universe, sets);
        System.out.println("Cover sets: " + result);
    }
}
