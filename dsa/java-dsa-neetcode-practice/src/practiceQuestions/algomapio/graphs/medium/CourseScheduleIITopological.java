package practiceQuestions.algomapio.graphs.medium;

import java.util.*;

public class CourseScheduleIITopological {

    /**
     * Step-by-Step Thought Process
     * Understand the problem: Find a valid order to take all courses given prerequisites, returning an empty list if impossible.
     * Create an adjacency list using a defaultdict to store the graph, where each course maps to its prerequisites.
     * Initialize an empty list order to store the course order.
     * Initialize a states array with UNVISITED (0) for each course to track visit status.
     * Define a DFS function that takes a node (course) and returns False if a cycle is detected, True otherwise.
     * In DFS, if the node is VISITING (1), a cycle is detected, return False.
     * If the node is VISITED (2), return True as it’s already processed.
     * Mark the node as VISITING, then recursively check all its neighbors (prerequisites).
     * If any neighbor returns False, return False.
     * Mark the node as VISITED, append it to order, and return True.
     * Run DFS for each course; if any returns False, return an empty list.
     * Return the order list.
     * */

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] order = new int[numCourses];
        int[] inDegree = new int[numCourses];
        Map<Integer, List<Integer>> map = new HashMap<>();
        Queue<Integer> queue = new LinkedList<>();
        // Initialize the graph
        for (int[] pre : prerequisites) {
            int target = pre[0], preCourse = pre[1];
            map.computeIfAbsent(preCourse, k -> new ArrayList<>()).add(target);
            inDegree[target] += 1;
        }
        // Find all courses with no prerequisites
        for (int i = 0; i < numCourses; ++i) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }
        int index = 0;
        while (!queue.isEmpty()) {
            int node = queue.poll();
            order[index++] = node;

            if (map.containsKey(node)) {
                for (int target : map.get(node)) {
                    inDegree[target] -= 1;
                    if (inDegree[target] == 0) {
                        queue.offer(target);
                    }
                }
            }
        }
        return index == numCourses ? order : new int[0];
    }
}

/**
 * Detailed Explanation
 * What Is the Course Schedule II Problem?
 * The Course Schedule II problem asks you to return a valid order in which to complete all courses given a list of prerequisites. If it’s impossible to complete all courses due to a cycle in the course dependencies, return an empty list.
 *
 * Topological Sort Approach for Course Scheduling
 * This problem is a classic application of topological sorting in a directed graph. Each course is a node, and each prerequisite is a directed edge. To solve this, we use depth-first search (DFS) to build a valid course order and detect cycles.
 *
 * How to Solve Course Schedule II Using DFS
 * Here is a step-by-step breakdown of how to solve the problem using DFS and a cycle detection strategy:
 *
 * Use a hash map to build an adjacency list from the prerequisites.
 * Track each course’s visit state with an array: UNVISITED (0), VISITING (1), and VISITED (2).
 * Perform DFS traversal for each course:
 * If the course is VISITING again, a cycle exists → return False.
 * If the course is VISITED, return True to avoid redundant work.
 * Mark the course as VISITING and recursively visit all prerequisites.
 * Once all neighbors are explored, mark the course as VISITED and append it to the course order.
 * Reverse the result list to get the correct topological sort order.
 * Why Topological Sorting Works for Course Prerequisites
 * In a valid course order, all prerequisites must appear before the dependent course. Using post-order traversal during DFS naturally builds this order. Reversing the result ensures each course comes after its dependencies. If a cycle is found, we can’t finish all courses, and we return an empty list.
 *
 * Time and Space Complexity of the Solution
 * Time Complexity: O(V + E), where V is the number of courses and E is the number of prerequisites. Each node and edge is visited once.
 * Space Complexity: O(V + E), due to the adjacency list and recursive call stack.
 *
 * Conclusion: Solving Course Schedule II Efficiently
 * The DFS-based topological sort is an efficient way to solve the Course Schedule II problem. It detects cycles in course prerequisites and builds a valid course completion order. This approach is scalable, fast, and leverages key graph theory principles used in many scheduling and dependency resolution problems.
 * */