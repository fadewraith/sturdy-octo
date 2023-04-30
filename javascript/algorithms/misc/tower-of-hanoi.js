/*
  Tower of Hanoi
  read master theorem for calculating time complexity
  time complexity - O(2^n-1)
  where n = 3, number of moves = 7
  
  some popular design techniques - 
  brute force - simple and exhaustive technique that evaluates every possible outcome to find the best soln, ex - linear search
  greedy - choose the best option at the current time, without any consideration for the future. ex - Djikstra's algorithm, Prim's algorithm, Kruskal's algorithm
  divide and conquer - divide the problem into smaller sub-problems. each sub-problem is then solved and the partial solutions are recombined to determine the overall solution. ex - binary search, quick sort, merge sort, tower of hanoi
  Dynamic programming - divide the problem into smaller sub-problems. Break it down into smaller but overlapping sub problems. Store the result and reuse it for the same sub-problems. This is called memoization and is a optimization technique that improves the time complexity of our algorithm. ex. - fibinacci numbers and climbing staircase
  backtracking - generate all possible solutions. check if the solution satisfies all the given constrains and only then, we proceed with generating subsequent solutions. If the constraints are not satisfied, backtracka nd go on a different path to find the solution. ex - n queens problem
*/

function towerOfHanoi(n, fromRod, toRod, usingRod) {
  if(n === 1) {
    console.log(`Move disk 1 from ${fromRod} to ${toRod}`);
    return;
  }
  towerOfHanoi(n-1, fromRod, usingRod, toRod)
  console.log(`Move disk 1 from ${fromRod} to ${toRod}`);
  towerOfHanoi(n-1, fromRod, usingRod, toRod);    
}

towerOfHanoi(3, 'A', 'C', 'B');