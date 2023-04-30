/*
  Linked list queue
*/

class Node {
  constructor(value) {
    this.value = value;
    this.next = null;
  }
}

class LinkedList {
  constructor() {
    this.head = null;
    this.tail = null;
    this.size = 0;
  }
  
  isEmpty() {
    return this.size === 0;
  }
  
  getSize() {
    return this.size;
  }
  
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
  
  prepend(value) {
    const node = new Node(value);
    if (this.isEmpty()) {
      this.head = node;
      this.tail = node;
    } else {
      node.next = this.head;
      this.head = node;
    }
    this.size++;
  }
  
  append(value) {
    const node = new Node(value);
    if (this.isEmpty()) {
      this.head = node;
      this.tail = node;
    } else {
      this.tail.next = node;
      this.tail = node;
    }
    this.size++;
  }
  
  removeFromFront() {
    if (this.isEmpty()) {
      return null;
    }
    const value = this.head.value;
    this.head = this.head.next;
    this.size--;
    return value;
  }
  
  removeFromEnd() {
    if (this.isEmpty()) {
      return null;
    }
    const value = this.tail.value;
    if (this.size === 1) {
      this.head = null;
      this.tail = null;
    } else {
      let previous = this.head;
      while (previous.next !== this.tail) {
        previous = previous.next;
      }
      previous.next = null;
      this.tail = previous;
    }
    this.size--;
    return value;
  }
}

class LinkedListStack {
  constructor() {
    this.list = new LinkedList();
  }
  
  enqueue(value) {
    // insert the value at the rare end
    this.list.append(value);
  }
  
  dequeue() {
    // removes from front of the queue
    return this.list.removeFromFront();
  }
  
  peek() {
    return this.list.head.value;
  }
  
  isEmpty() {
    return this.list.isEmpty();
  }
  
  getSize() {
    return this.list.getSize();
  }
  
  print() {
    return this.list.print();
  }
  
}

const queue = new LinkedListStack();
console.log(queue.isEmpty());

queue.enqueue(10);
queue.enqueue(20);
queue.enqueue(30);
console.log(queue.getSize());
queue.print();

console.log(queue.dequeue());
queue.print();

console.log(queue.peek());