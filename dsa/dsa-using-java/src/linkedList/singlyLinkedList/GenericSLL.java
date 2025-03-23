package linkedList.singlyLinkedList;

import utils.GenericNode;

public class GenericSLL<T> {
    public GenericNode<T> head;
    public GenericNode<T> tail;
    public int size;

    // Constructor
    public GenericSLL() {
        head = null;
        tail = null;
        size = 0;
    }

    // Create a Singly Linked List with one node
    public GenericNode<T> createSinglyLinkedList(T nodeValue) {
        GenericNode<T> node = new GenericNode<>();
        node.value = nodeValue;
        node.next = null;
        head = node;
        tail = node;
        size = 1;
        return head;
    }

    // Insert Method into the Singly Linked List
    public void insertInLinkedList(T nodeValue, int location) {
        GenericNode<T> node = new GenericNode<>();
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
            GenericNode<T> tempNode = head;
            int index = 0;
            while (index < location - 1) {
                tempNode = tempNode.next;
                index++;
            }
            GenericNode<T> nextNode = tempNode.next;
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
            GenericNode<T> tempNode = head;
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
    public boolean searchNode(T nodeValue) {
        if (head == null) {
            System.out.println("SLL does not exist!");
            return false;
        }

        GenericNode<T> tempNode = head;
        int index = 0;
        while (tempNode != null) {
            if (tempNode.value.equals(nodeValue)) {
                System.out.println("Found the node at location: " + index);
                return true;
            }
            tempNode = tempNode.next;
            index++;
        }
        System.out.println("GenericNode not found!");
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
            GenericNode<T> tempNode = head;
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
            GenericNode<T> tempNode = head;
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


}

