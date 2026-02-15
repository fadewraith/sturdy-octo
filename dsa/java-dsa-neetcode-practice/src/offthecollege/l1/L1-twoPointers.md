What is 2 pointers patterns?

The 2 pointers technique involves using 2 indices (pointers) to iterate over a DS(usually an array or a string) to solve problems efficiently by avoiding nested loops.

When to use 2 pointers?
- When you need to find pairs, triplets, or subarrays meeting certain conditions.
- When the data is sorted or can be stored.
- When you want to optimize brute force solutions that use nested loops (O(n*n)) to linear or near-linear time O(n).

How it Works?
You maintain 2 pointers that move through the ds acc to certain rules:
- One pointer starts at the beginning, other at the end.
- Or, both pointers start at the beginning, with one moving faster than the other (useful for sliding window problems).
- Move pointers towards each other or forward depending on the problem condition.
