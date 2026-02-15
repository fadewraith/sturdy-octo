Characteristics -
- Overlapping subproblems: The subproblems of a given problem are not independent. In other words, two subproblems share a problem.
- Optimal substructure property: The overall optimal solution of the problem can be constructed from the optimal solutions of its subproblems.

Dynamic programming patterns
Memoization (top down)
The memoized version of a problem is similar to the regular recursive version, except that it looks for the answer of a subproblem in a lookup table before computing its solution.

Tabulation (bottom up)
Tabulation is the opposite of the top-down approach and avoids recursion. In this approach, we solve the problem “bottom-up”. This is typically done by filling up a lookup table, and computing the solution to the top/original problem based on the results in the table.

