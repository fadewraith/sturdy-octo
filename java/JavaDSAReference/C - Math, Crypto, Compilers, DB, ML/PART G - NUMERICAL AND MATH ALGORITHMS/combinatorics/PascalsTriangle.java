package math.combinatorics;

import java.util.ArrayList;
import java.util.List;

/**
 * WHAT IT IS: A triangular array of the binomial coefficients.
 * STRATEGY: Dynamic programming. Each number is the sum of the two numbers directly above it.
 * TIME/SPACE COMPLEXITY: O(n^2) time, O(n^2) space to store the triangle.
 * REAL-WORLD ANALOGY / USE CASE: Expanding binomials, computing probabilities.
 * WHEN TO USE / COMBINATION: Quick computation of multiple nCr queries up to a small n.
 * 
 * PSEUDOCODE:
 * triangle = []
 * for i from 0 to n:
 *     row = [1]
 *     for j from 1 to i-1:
 *         row.add(triangle[i-1][j-1] + triangle[i-1][j])
 *     if i > 0: row.add(1)
 *     triangle.add(row)
 * return triangle
 */
public class PascalsTriangle {
    public static List<List<Integer>> generate(int numRows) {
        List<List<Integer>> triangle = new ArrayList<>();
        if (numRows == 0) return triangle;
        
        triangle.add(new ArrayList<>());
        triangle.get(0).add(1);
        
        for (int i = 1; i < numRows; i++) {
            List<Integer> row = new ArrayList<>();
            List<Integer> prevRow = triangle.get(i - 1);
            
            row.add(1);
            for (int j = 1; j < i; j++) {
                row.add(prevRow.get(j - 1) + prevRow.get(j));
            }
            row.add(1);
            triangle.add(row);
        }
        return triangle;
    }

    public static void main(String[] args) {
        List<List<Integer>> pt = generate(5);
        System.out.println("Pascal's Triangle for n=5:");
        for (List<Integer> row : pt) {
            System.out.println(row);
        }
    }
}
