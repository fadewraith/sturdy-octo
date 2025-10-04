package practiceQuestions.algomapio.arraysandstrings.medium;

import java.util.Arrays;

public class HIndex {

//    Step-by-Step Thought Process
//    Understand the problem: Find the largest h such that h papers have at least h citations each, given an array of citations.
//    Create an array paper_counts of size n+1 to store the count of papers with at least i citations.
//    For each citation, increment paper_counts at index min(n, citation).
//    Start with h = n and papers = paper_counts[n].
//    While papers < h, decrement h and add paper_counts[h] to papers.
//    Return h as the H-Index.

    private static int[] solution(int[] citations) {
        int n = citations.length;
        // First, initialize an array paper_counts of size n + 1 with all zeros. Then, for each citation c in the input array:
        int[] paperCounts = new int[n + 1];

        for(int c: citations) {
            // If c >= n, increment paper_counts[n].
            // Otherwise, increment paper_counts[c].
            paperCounts[Math.min(n, c)]++;
        }

        int h = n;
        // Once the counting array is populated, we work backwards from h = n down to 0, cumulatively adding up how many papers have at least h citations. The first h where the number of such papers is at least h is our answer.
        int papers = paperCounts[n];

        while(papers < h) {
            h--;
            papers += paperCounts[h];
        }

        // return h;

        int[] res = new int[h];
        int index = 0;
        for(int c: citations) {
            if(c >= h) {
                res[index++] = c;
            }
        }

        return res;
    }

    public static void main(String[] args) {
        int[] citations = {3, 0, 6, 1, 5, 10, 50};
//        System.out.println(solution(citations));
        System.out.println(Arrays.toString(solution(citations)));
    }
}


/**
 * Detailed Explanation
 * What is the H-Index Problem?
 * The H-Index is a metric that attempts to measure both the productivity and citation impact of a researcher’s publications. Given a list of citations (where each element represents how many times a paper has been cited), the goal is to determine the highest number h such that the researcher has published h papers each cited at least h times.
 *
 * This problem is a common interview question that requires understanding of counting and sorting concepts, and it has both intuitive and optimized solutions.
 *
 * Strategy and Key Insight
 * A naive approach might sort the array and check each position to determine how many papers meet the citation threshold. However, this isn't efficient enough for large datasets.
 *
 * A better strategy uses counting. Since the H-Index can never be greater than the total number of papers n, we can create a counting array of size n + 1 where each index i represents how many papers have exactly i citations. Any citation count higher than n is treated as n to simplify handling.
 *
 * Step-by-Step Breakdown
 * First, initialize an array paper_counts of size n + 1 with all zeros. Then, for each citation c in the input array:
 *
 * If c >= n, increment paper_counts[n].
 * Otherwise, increment paper_counts[c].
 * Once the counting array is populated, we work backwards from h = n down to 0, cumulatively adding up how many papers have at least h citations. The first h where the number of such papers is at least h is our answer.
 *
 * Time and Space Complexity
 * Time Complexity: O(n), since we only pass through the citations list once and then iterate through an array of size n + 1.
 * Space Complexity: O(n), due to the counting array.
 * Why This Approach is Efficient
 * This technique avoids sorting and leverages frequency counting, which allows us to quickly determine how many papers meet each citation threshold without scanning the original array multiple times. It's especially useful for large datasets and maintains linear time performance.
 *
 * Conclusion
 * The H-Index problem demonstrates how counting and bounding techniques can be used to solve problems involving thresholds and rankings efficiently. Understanding this approach provides a strong foundation for similar optimization problems involving frequency analysis and numeric constraints.
 * */