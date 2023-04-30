
/*
  Linked List
*/

// Node class
class Node {
  constructor(value) {
    this.value = value;
    this.next = null;
  }
}

// Linked list class
class LinkedList {
  constructor() {
    // new list, head is null
    this.head = null;
    this.size = 0;
  }
  
  isEmpty() {
    return this.size === 0;
  }
  
  getSize() {
    return this.size;
  }
  
  // prepend - adding @beginning, O(1)
  prepend(value) {
    const node = new Node(value);
    if (this.isEmpty()) { // if the list is empty
      this.head = node;
    } else {
      node.next = this.head;
      this.head = node;
    }
    this.size++;
  }
  
  // append - adding @end, O(n)
  append(value) {
    const node = new Node(value);
    if (this.isEmpty()) {
      this.head = node;
    } else {
      let previous = this.head;
      while(previous.next) {
        previous = previous.next;
      }
      previous.next = node;
    }
    this.size++;
  }
  
  // insert with 3 scenarios
  insert(value, index) {
    if (index < 0 || index > this.size) {
      return;  // do nothing
    }
    if (index === 0) {
      this.prepend(value); // not to increase the size here
    } else {
      const node = new Node(value);
      let previous = this.head;
      for (let i = 0; i < index - 1; i++) {
        previous = previous.next; // to advance the previous pointer, pointing at its own next pointer
      }
      node.next = previous.next;
      previous.next = node;
      this.size++;
    }
  }

  // remove by value
  removeValue(value) {
    if (this.isEmpty()) {
      return null;
    }
    if (this.head.value === value) {
      this.head = this.head.next;
      this.size--;
      return value;
    } else {
      let previous = this.head;
      while(previous.next && previous.next.value !== value) {
        previous = previous.next;
      }
      if (previous.next) {
        const removeNode = previous.next;
        previous.next = removeNode.next;
        this.size++;
        return value;
      }
      return null;
    }
  }
  
  // for searching the value
  search(value) {
    if (this.isEmpty()) {
      return -1;
    }
    let i = 0;
    let current = this.head;
    while(current) {
      if (current.value === value) {
        return i;
      }
      current = current.next;
      i++; 
    }
    return -1;
  }
  
  // reversing the linked list
  reverse() {
    let previous = null;
    let current = this.head;
    while(current) {
      let next = current.next;
      current.next = previous;
      previous = current;
      current = next;
    }
    this.head = previous;
  }
  
  
  
  // print the node
  print() {
    if (this.isEmpty()) {
      console.log('List is empty');
    } else {
      let current = this.head;
      let listValues = '';
      while (current) {
        listValues += `${current.value} `;
        current = current.next;
      }
      console.log(listValues);
    }
  }
  
  // remove from node given its index
  remove(index) {
    if (index < 0 || index >= this.size) {
      return null;
    }
    let removeNode;
    if (index === 0) {
      removeNode = this.head;
      this.head = this.head.next;
    } else {
      let previous = this.head;
      for (let i = 0; i < index - 1; i++) {
        previous = previous.next;
      }
      removeNode = previous.next;
      previous.next = removeNode.next;
    }
    this.size--;
    return removeNode.value;
  }
}

const list = new LinkedList();
console.log('List is empty? ', list.isEmpty());
console.log('List size ', list.getSize());

// list.append(10);
// list.print();

// list.append(20);
// list.append(30);
// list.print();

list.print();
// console.log(list.getSize());
list.insert(10, 0);
// console.log(list.getSize());
list.print();
list.insert(145, 0);
// console.log(list.getSize());
list.print();
list.insert(30, 1);
list.insert(55, 1);
// console.log(list.getSize());
list.print();
// console.log(list.removeValue(10));
// console.log(list.removeValue(50));
// list.search(1);
list.reverse();
list.print();