package linkedList;

import utils.Node;

public class SinglyLinkedList {
    public Node head;
    public Node tail;
    public int size;

    // Constructor
    public SinglyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    // Create a Singly Linked List with one node
    public Node createSinglyLinkedList(int nodeValue) {
        Node node = new Node();
        node.value = nodeValue;
        node.next = null;
        head = node;
        tail = node;
        size = 1;
        return head;
    }

    // Insert Method into the Singly Linked List
    public void insertInLinkedList(int nodeValue, int location) {
        Node node = new Node();
        node.value = nodeValue;

        // Edge case: If the list is empty
        if (head == null) {
            createSinglyLinkedList(nodeValue);
            return;
        }

        // Inserting at the beginning (location 0)
        if (location == 0) {
            node.next = head;
            head = node;
        }
        // Inserting at the end (location >= size)
        else if (location >= size) {
            node.next = null;
            tail.next = node;
            tail = node;
        }
        // Inserting in the middle
        else {
            if (location < 0 || location > size) {
                System.out.println("Invalid location");
                return;
            }
            Node tempNode = head;
            int index = 0;
            while (index < location - 1) {
                tempNode = tempNode.next;
                index++;
            }
            Node nextNode = tempNode.next;
            tempNode.next = node;
            node.next = nextNode;
        }
        size++;
    }

    // Traverse the Singly Linked List
    public void traverseSinglyLinkedList() {
        if (head == null) {
            System.out.println("SLL does not exist!");
        } else {
            Node tempNode = head;
            while (tempNode != null) {
                System.out.print(tempNode.value);
                if (tempNode.next != null) {
                    System.out.print(" -> ");
                }
                tempNode = tempNode.next;
            }
        }
        System.out.println("\n");
    }

    // Search for a node in the Singly Linked List
    public boolean searchNode(int nodeValue) {
        if (head == null) {
            System.out.println("SLL does not exist!");
            return false;
        }

        Node tempNode = head;
        int index = 0;
        while (tempNode != null) {
            if (tempNode.value == nodeValue) {
                System.out.println("Found the node at location: " + index);
                return true;
            }
            tempNode = tempNode.next;
            index++;
        }
        System.out.println("Node not found!");
        return false;
    }

    // Deleting a node from Singly Linked List
    public void deletionOfNode(int location) {
        if (head == null) {
            System.out.println("The SLL does not exist");
            return;
        }

        // Deleting the first node (location 0)
        if (location == 0) {
            head = head.next;
            size--;
            if (size == 0) {
                tail = null; // The list is now empty
            }
        }
        // Deleting the last node (location >= size)
        else if (location >= size - 1) {
            Node tempNode = head;
            while (tempNode.next != null && tempNode.next != tail) {
                tempNode = tempNode.next;
            }
            if (tempNode == head) {
                head = tail = null;
            } else {
                tempNode.next = null;
                tail = tempNode;
            }
            size--;
        }
        // Deleting a node from the middle
        else {
            if (location < 0 || location >= size) {
                System.out.println("Invalid location");
                return;
            }
            Node tempNode = head;
            for (int i = 0; i < location - 1; i++) {
                tempNode = tempNode.next;
            }
            tempNode.next = tempNode.next.next;
            size--;
        }
    }

    // Delete Entire Singly Linked List
    public void deleteSLL() {
        head = null;
        tail = null;
        size = 0;
        System.out.println("The SLL has been deleted successfully.");
    }

    public static void main(String[] args) {
        SinglyLinkedList sLL = new SinglyLinkedList();
        sLL.createSinglyLinkedList(5);
        // System.out.println(sLL.head.value);
        sLL.insertInLinkedList(6, 1);
        sLL.insertInLinkedList(7, 3);
        sLL.insertInLinkedList(8, 4);
        sLL.insertInLinkedList(9, 0);
        sLL.traverseSinglyLinkedList();
        sLL.deleteSLL();
        sLL.traverseSinglyLinkedList();

    }

}
