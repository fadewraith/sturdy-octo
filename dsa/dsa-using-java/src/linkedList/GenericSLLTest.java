package linkedList;

public class GenericSLLTest {

    public static void main(String[] args) {

        // Test Case 1: Create Singly Linked List with Integer
        GenericSLL<Integer> integerList = new GenericSLL<>();
        integerList.createSinglyLinkedList(10);
        System.out.println("Test Case 1 - Create Singly Linked List with Integer:");
        integerList.traverseSinglyLinkedList();  // Expected: 10

        // Test Case 2: Insert at the beginning (location 0) for Integer
        integerList.insertInLinkedList(5, 0);
        System.out.println("\nTest Case 2 - Insert at the Beginning with Integer:");
        integerList.traverseSinglyLinkedList();  // Expected: 5 -> 10

        // Test Case 3: Insert at the end (location >= size) for Integer
        integerList.insertInLinkedList(20, 2);
        System.out.println("\nTest Case 3 - Insert at the End with Integer:");
        integerList.traverseSinglyLinkedList();  // Expected: 5 -> 10 -> 20

        // Test Case 4: Insert in the middle for Integer
        integerList.insertInLinkedList(15, 1);
        System.out.println("\nTest Case 4 - Insert in the Middle with Integer:");
        integerList.traverseSinglyLinkedList();  // Expected: 5 -> 15 -> 10 -> 20

        // Test Case 5: Search for an existing node (Integer)
        System.out.println("\nTest Case 5 - Search for an Existing Node with Integer:");
        integerList.searchNode(15);  // Expected: Found the node at location: 1

        // Test Case 6: Search for a non-existing node (Integer)
        System.out.println("\nTest Case 6 - Search for a Non-Existing Node with Integer:");
        integerList.searchNode(30);  // Expected: Node not found!

        // Test Case 7: Delete node at the beginning (location 0) for Integer
        integerList.deletionOfNode(0);
        System.out.println("\nTest Case 7 - Delete Node at Beginning with Integer:");
        integerList.traverseSinglyLinkedList();  // Expected: 15 -> 10 -> 20

        // Test Case 8: Delete node in the middle (Integer)
        integerList.deletionOfNode(1);
        System.out.println("\nTest Case 8 - Delete Node in the Middle with Integer:");
        integerList.traverseSinglyLinkedList();  // Expected: 15 -> 20

        // Test Case 9: Delete node at the end (location >= size) for Integer
        integerList.deletionOfNode(1);
        System.out.println("\nTest Case 9 - Delete Node at End with Integer:");
        integerList.traverseSinglyLinkedList();  // Expected: 15

        // Test Case 10: Delete the entire singly linked list (Integer)
        integerList.deleteSLL();
        System.out.println("\nTest Case 10 - Delete Entire Singly Linked List with Integer:");
        integerList.traverseSinglyLinkedList();  // Expected: (No output, list is empty)

        // Test Case 11: Create Singly Linked List with String
        GenericSLL<String> stringList = new GenericSLL<>();
        stringList.createSinglyLinkedList("Hello");
        System.out.println("\nTest Case 11 - Create Singly Linked List with String:");
        stringList.traverseSinglyLinkedList();  // Expected: Hello

        // Test Case 12: Insert at the beginning (location 0) for String
        stringList.insertInLinkedList("World", 0);
        System.out.println("\nTest Case 12 - Insert at the Beginning with String:");
        stringList.traverseSinglyLinkedList();  // Expected: World -> Hello

        // Test Case 13: Insert at the end (location >= size) for String
        stringList.insertInLinkedList("Java", 2);
        System.out.println("\nTest Case 13 - Insert at the End with String:");
        stringList.traverseSinglyLinkedList();  // Expected: World -> Hello -> Java

        // Test Case 14: Insert in the middle for String
        stringList.insertInLinkedList("Programming", 1);
        System.out.println("\nTest Case 14 - Insert in the Middle with String:");
        stringList.traverseSinglyLinkedList();  // Expected: World -> Programming -> Hello -> Java

        // Test Case 15: Search for an existing node (String)
        System.out.println("\nTest Case 15 - Search for an Existing Node with String:");
        stringList.searchNode("Programming");  // Expected: Found the node at location: 1

        // Test Case 16: Search for a non-existing node (String)
        System.out.println("\nTest Case 16 - Search for a Non-Existing Node with String:");
        stringList.searchNode("Python");  // Expected: Node not found!

        // Test Case 17: Delete node at the beginning (location 0) for String
        stringList.deletionOfNode(0);
        System.out.println("\nTest Case 17 - Delete Node at Beginning with String:");
        stringList.traverseSinglyLinkedList();  // Expected: Programming -> Hello -> Java

        // Test Case 18: Delete node in the middle (String)
        stringList.deletionOfNode(1);
        System.out.println("\nTest Case 18 - Delete Node in the Middle with String:");
        stringList.traverseSinglyLinkedList();  // Expected: Programming -> Java

        // Test Case 19: Delete node at the end (location >= size) for String
        stringList.deletionOfNode(1);
        System.out.println("\nTest Case 19 - Delete Node at End with String:");
        stringList.traverseSinglyLinkedList();  // Expected: Programming

        // Test Case 20: Delete the entire singly linked list (String)
        stringList.deleteSLL();
        System.out.println("\nTest Case 20 - Delete Entire Singly Linked List with String:");
        stringList.traverseSinglyLinkedList();  // Expected: (No output, list is empty)
    }
}
