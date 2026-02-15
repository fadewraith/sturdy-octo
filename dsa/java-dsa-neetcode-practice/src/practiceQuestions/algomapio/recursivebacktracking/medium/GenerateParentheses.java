package practiceQuestions.algomapio.recursivebacktracking.medium;

import java.util.ArrayList;
import java.util.List;

public class GenerateParentheses {

    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();

        dfs(0, 0, "", n, res);

        return res;
    }

    private void dfs(int openP, int closeP, String s, int n, List<String> res) {
        if (openP == closeP && openP + closeP == n * 2) {
            res.add(s);
            return;
        }

        if (openP < n) {
            dfs(openP + 1, closeP, s + "(", n, res);
        }

        if (closeP < openP) {
            dfs(openP, closeP + 1, s + ")", n, res);
        }
    }

}

/**
 *
 * Understanding the Problem
 * The "Generate Parentheses" problem challenges us to produce all valid combinations of n pairs of parentheses. A combination is considered valid if every opening parenthesis has a matching closing one and they are properly nested. For example, when n = 3, a valid result includes combinations like "((()))", "(()())", and "()()()".
 *
 * Why Brute Force is Not Enough
 * A naive approach would be to generate every possible string of 2n characters composed of '(' and ')', then filter out the invalid ones. However, that approach results in 22n combinations to check, which is extremely inefficient for larger n. Instead, we leverage backtracking to generate only valid sequences directly.
 *
 * Backtracking Approach
 * Backtracking is a recursive strategy where we build up a partial solution, and as soon as we detect that it cannot lead to a valid result, we backtrack. In this problem, the key is to keep track of how many open and close parentheses have been added so far.
 *
 * We begin with an empty list sol that will be used to build each combination character by character. At each recursive call, we make one of two decisions:
 *
 * If we still have unused opening parentheses (open < n), we can safely add a '('.
 * If the number of opening parentheses used is greater than the number of closing ones (close < open), we can add a ')' to close a group.
 * We continue this process recursively until the length of the built string is 2n. At that point, we’ve formed a valid combination and add it to the result list.
 *
 * Complexity Analysis
 * The total number of valid combinations corresponds to the nth Catalan number, which grows rapidly: C(n) = (2n)! / ((n + 1)! * n!). Thus, the time complexity is O(C(n) * n), since we spend O(n) time to construct each string of length 2n. The space complexity is also O(C(n) * n) to store the output and support the recursive call stack.
 * */


/**
 *  Step-by-Step Thought Process
 * Understand the problem: Generate all valid combinations of n pairs of parentheses.
 * Initialize two empty lists: ans to store all valid combinations and sol to build the current combination.
 * Define a backtracking function that takes the count of open and close parentheses used.
 * If the current combination length equals 2*n, append it to ans as a valid combination.
 * If the number of open parentheses is less than n, append an open parenthesis and recurse with open+1.
 * Pop the open parenthesis to backtrack.
 * If the number of open parentheses is greater than close, append a close parenthesis and recurse with close+1.
 * Pop the close parenthesis to backtrack.
 * Start backtracking with open=0 and close=0, then return ans.
 * */