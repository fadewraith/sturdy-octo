FUNCTION fastAndSlow(dataStructure):
#### initialize pointers (or indices)
fastPointer = dataStructure.start   # or 0 if the data structure is an array
slowPointer = dataStructure.start   # or 0 if the data structure is an array

WHILE fastPointer != null AND fastPointer.next != null:
#### For arrays: WHILE fastPointer < dataStructure.length AND (fastPointer + 1) < dataStructure.length:

    slowPointer = slowPointer.next            
    # For arrays: slowPointer = slowPointer + 1
    
    fastPointer = fastPointer.next.next       
    # For arrays: fastPointer = fastPointer + 2
    
    IF fastPointer != null AND someCondition(fastPointer, slowPointer):
      # For arrays: use someCondition(dataStructure[fastPointer], dataStructure[slowPointer]) if needed
      handleCondition(fastPointer, slowPointer)
      BREAK

### process the result
processResult(slowPointer)
#### For arrays: processResult(slowPointer) might process dataStructure[slowPointer]


Does your problem match this pattern?
-------------------------------------

Yes, if the following condition is fulfilled:

*   **Linear data structure:** The input data can be traversed in a linear fashion, such as an array, linked list, or string.


In addition, if either of these conditions is fulfilled:

*   **Cycle or intersection detection:** The problem involves detecting a loop within a linked list or an array or involves finding an intersection between two linked lists or arrays.

*   **Find the starting element of the second quantile:** This means identifying the element where the second part of a divided dataset begins—like the second half, second third (tertile), or second quarter (quartile). For example, the task might ask you to find the middle element of an array or a linked list, which marks the start of the second half.