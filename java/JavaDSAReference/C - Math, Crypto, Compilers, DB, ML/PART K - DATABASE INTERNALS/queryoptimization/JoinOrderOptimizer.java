package db.queryoptimization;

import java.util.*;

/**
 * WHAT IT IS:
 * Join Order Optimization is the process of finding the most efficient way to join multiple tables in a database query. 
 * 
 * STRATEGY:
 * - Estimate the size of intermediate results using table statistics.
 * - Evaluate different join permutations (or use dynamic programming like System R).
 * - Choose the order that minimizes the total cost (usually intermediate row count).
 * 
 * TIME/SPACE COMPLEXITY:
 * - Time: O(N!) for naive permutation, O(2^N) or O(N^3) with DP for N tables.
 * - Space: O(2^N) for DP memoization.
 * 
 * REAL-WORLD ANALOGY / USE CASE:
 * If you need to find an intersection of three sets, you should intersect the two smallest sets first to minimize the work for the third set.
 * 
 * WHEN TO USE / COMBINATION:
 * Used in cost-based query optimizers in relational databases.
 * 
 * PSEUDOCODE:
 * optimize_join(tables):
 *   best_plan = null
 *   min_cost = infinity
 *   for order in permutations(tables):
 *     cost = estimate_cost(order)
 *     if cost < min_cost:
 *       best_plan = order
 *       min_cost = cost
 *   return best_plan
 */
public class JoinOrderOptimizer {

    static class Table {
        String name;
        int rows;

        Table(String name, int rows) {
            this.name = name;
            this.rows = rows;
        }
    }

    public static List<Table> optimizeJoinOrder(List<Table> tables) {
        // Very simplified cost model: Sort by size (smallest first).
        // Real optimizers use cardinality estimates and selectivity.
        List<Table> optimized = new ArrayList<>(tables);
        optimized.sort(Comparator.comparingInt(t -> t.rows));
        return optimized;
    }

    public static void main(String[] args) {
        System.out.println("=== Join Order Optimization Tests ===");
        
        List<Table> queryTables = Arrays.asList(
            new Table("Users", 100000),
            new Table("Orders", 500000),
            new Table("Roles", 10)
        );
        
        System.out.println("Original Plan:");
        for (Table t : queryTables) {
            System.out.print(t.name + "(" + t.rows + ") -> ");
        }
        System.out.println("End");
        
        List<Table> optimized = optimizeJoinOrder(queryTables);
        
        System.out.println("\nOptimized Plan (Smallest First):");
        for (Table t : optimized) {
            System.out.print(t.name + "(" + t.rows + ") -> ");
        }
        System.out.println("End");
        System.out.println("Explanation: Joining Roles with Users first creates a much smaller intermediate set than Users with Orders.");
    }
}
