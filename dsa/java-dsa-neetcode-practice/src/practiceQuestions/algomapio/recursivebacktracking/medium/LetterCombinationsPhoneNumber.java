package practiceQuestions.algomapio.recursivebacktracking.medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LetterCombinationsPhoneNumber {

    public List<String> letterCombinations(String digits) {
        List<String> ans = new ArrayList<>();
        if (digits.isEmpty()) {
            return ans;
        }

        Map<Character, String> letterMap = new HashMap<>();
        letterMap.put('2', "abc");
        letterMap.put('3', "def");
        letterMap.put('4', "ghi");
        letterMap.put('5', "jkl");
        letterMap.put('6', "mno");
        letterMap.put('7', "pqrs");
        letterMap.put('8', "tuv");
        letterMap.put('9', "wxyz");

        backtrack(digits, 0, new StringBuilder(), letterMap, ans);
        return ans;
    }

    private void backtrack(String digits, int index, StringBuilder path, Map<Character, String> letterMap, List<String> ans) {
        if (index == digits.length()) {
            ans.add(path.toString());
            return;
        }

        String letters = letterMap.get(digits.charAt(index));
        for (char letter : letters.toCharArray()) {
            path.append(letter);
            backtrack(digits, index + 1, path, letterMap, ans);
            path.deleteCharAt(path.length() - 1);
        }
    }

}



/**
 *Understanding the Problem
 * The "Letter Combinations of a Phone Number" problem involves generating all possible strings that can be formed by mapping a string of digits (from 2 to 9) to their corresponding letters on a traditional telephone keypad. For example, the digit 2 maps to the letters "abc", 3 maps to "def", and so on.
 *
 * Given an input string of digits, the goal is to return all valid letter combinations that can be formed by choosing one letter for each digit in the same order. This is a classic problem that emphasizes the importance of backtracking and recursion in generating combinations.
 * ================================
 *
 * Step-by-Step Execution
 * Let's break down the recursive process:
 *
 * First, we define a mapping from digits to characters using a dictionary: {"2": "abc", "3": "def", ..., "9": "wxyz"}.
 *
 * We initialize an empty list ans to store all the final combinations and a temporary list sol to build each string during the recursive process.
 *
 * The backtracking function accepts an index i that tracks which digit we are currently processing. If i reaches the length of the digit string, we know we’ve formed a complete combination and can add it to ans.
 *
 * Otherwise, we look up the letters for digits[i], append each one to the current solution, recurse to the next digit, and then backtrack by removing the letter we just added.
 *
 * ===================================
 *
 * The time complexity is O(3n * 4m) where n is the number of digits mapping to 3 letters and m is the number of digits mapping to 4 letters (like '7' or '9'). This represents the total number of combinations we may generate.
 *
 * The space complexity is also exponential, since we are storing all valid combinations in memory and maintaining a recursive call stack proportional to the number of digits.
 *
 * ==================================================
 *
 *  Step-by-Step Thought Process
 * Understand the problem: Given a string of digits, return all possible letter combinations based on a phone keypad mapping.
 * If the input digits string is empty, return an empty list.
 * Initialize an empty list ans to store the final combinations and a list sol to build the current combination.
 * Define a letter_map dictionary mapping each digit (2-9) to its corresponding letters (e.g., "2": "abc").
 * Get the length n of the digits string.
 * Define a backtrack function that takes an index i, starting from 0.
 * If i equals n, append the joined sol list to ans as a complete combination.
 * For each letter in letter_map[digits[i]], append the letter to sol, recursively call backtrack with i + 1, and pop the letter to backtrack.
 * Call backtrack with index 0 and return ans.
 * */