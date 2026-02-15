Traditional vs Condition-Based Binary Search

Feature	- Traditional Binary Search,	Condition-Based Binary Search

Use Case->	Find a specific target value,	Find boundary index satisfying a condition
Loop Condition->	L <= R,	L < R
Midpoint Adjustment->	R = M - 1 or L = M + 1,	R = M or L = M + 1
Return Value->	True/False (found or not),	Index of boundary
Examples->	Search in [1, 3, 5, 7],	Search in [False, False, True, True]