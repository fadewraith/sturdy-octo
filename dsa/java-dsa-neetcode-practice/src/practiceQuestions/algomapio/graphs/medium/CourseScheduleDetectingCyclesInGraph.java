package practiceQuestions.algomapio.graphs.medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseScheduleDetectingCyclesInGraph {

    /**
     * Step-by-Step Thought Process
     * Understand the problem: Determine if it’s possible to finish all courses given prerequisites, by detecting if there’s a cycle in the graph.
     * Create an adjacency list using a defaultdict to store the graph, where each course maps to its prerequisites.
     * Initialize a states array with UNVISITED (0) for each course to track visit status.
     * Define a DFS function that takes a node (course) and returns False if a cycle is detected, True otherwise.
     * In DFS, if the node is VISITING (1), a cycle is detected, return False.
     * If the node is VISITED (2), return True as it’s already processed.
     * Mark the node as VISITING, then recursively check all its neighbors (prerequisites).
     * If any neighbor returns False, return False.
     * Mark the node as VISITED and return True.
     * Run DFS for each course; if any returns False, return False.
     * Return True if no cycles are detected.
     * */

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int[] pair : prerequisites) {
            graph.computeIfAbsent(pair[0], k -> new ArrayList<>()).add(pair[1]);
        }

        final int UNVISITED = 0;
        final int VISITING = 1;
        final int VISITED = 2;
        int[] states = new int[numCourses];

        for (int i = 0; i < numCourses; i++) {
            if (!dfs(i, graph, states)) {
                return false;
            }
        }

        return true;
    }

    private boolean dfs(int node, Map<Integer, List<Integer>> graph, int[] states) {
        if (states[node] == 1) return false; // Cycle detected
        if (states[node] == 2) return true;  // Already visited

        states[node] = 1; // Mark as visiting

        if (graph.containsKey(node)) {
            for (int neighbor : graph.get(node)) {
                if (!dfs(neighbor, graph, states)) {
                    return false;
                }
            }
        }

        states[node] = 2; // Mark as visited
        return true;
    }
}

/**
 * Detailed Explanation
 * Understanding the Problem
 * You're given a set of courses, labeled from 0 to numCourses - 1, and a list of prerequisite pairs. Each pair [a, b] means course a requires course b to be completed first. Your task is to determine if it's possible to finish all the courses. This boils down to detecting whether the prerequisite structure contains any cycles — if it does, completing all courses is impossible.
 *
 * Modeling the Problem
 * We can model this as a directed graph:
 *
 * Each course is a node.
 * Each prerequisite pair [a, b] becomes a directed edge from b to a.
 * If this graph has a cycle, then there's a circular dependency, and we cannot complete all the courses.
 * Approach: DFS with Cycle Detection
 * The goal is to perform a depth-first traversal (DFS) on each node and detect cycles. To do this, we use a state tracking array for all nodes:
 *
 * 0 = UNVISITED: the course has not been checked yet.
 * 1 = VISITING: we're currently exploring this course’s prerequisites.
 * 2 = VISITED: the course and all its descendants have been verified and no cycle was found.
 * During DFS, if we encounter a course marked as VISITING, we’ve found a cycle.
 * Step-by-Step Execution
 * 1. Build the graph: Use an adjacency list to represent which courses depend on which prerequisites.
 * 2. Initialize visit state: Create a state array of size numCourses initialized to 0 (UNVISITED).
 * 3. Define DFS: For each course, recursively traverse its prerequisites.
 * 4. Run DFS: Loop through all courses. For each UNVISITED course, call DFS. If any DFS call detects a cycle, return False.
 * 5. If no cycles are found: Return True after checking all courses.
 *
 * Time and Space Complexity
 * Time Complexity: O(V + E), where V is the number of courses and E is the number of prerequisites. Each node and edge is visited once.
 * Space Complexity: O(V + E), to store the graph and the visit state.
 *
 * Why This Works
 * The key idea is that a valid course schedule corresponds to a Directed Acyclic Graph (DAG). If you can show there is no cycle in the graph formed by prerequisites, then there's some order in which all courses can be taken.
 *
 * Conclusion
 * This is a classic graph cycle detection problem disguised in the context of course scheduling. By translating prerequisites into edges and using DFS with a visited state tracker, we can efficiently determine whether a valid schedule exists.
 * */