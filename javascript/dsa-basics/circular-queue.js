/*
  Circular Queue implementation - 
  methods to implement
  enqueue/dequeue/isFull/isEmpty/peek/size/print
*/
'use strict';
class CircularQueue {
  constructor(capacity) {
    this.items = new Array(capacity)
    this.capacity = capacity;
    this.currentLength = 0;
    // 2 pointers to keep the track 
    this.rear = -1;
    this.front = -1;
  }
  
  isFull() {
    return this.currentLength === this.capacity;
  }
  
  isEmpty() {
    return this.currentLength === 0;
  }
  
  enqueue(element) {
    if(!this.isFull()) {
      // this is done for edge case, so it has 5 places and  from index 0 an element is deleted, then that time rear will point o 5, but we wanted it to point to index 0
      this.rear = (this.rear + 1) % this.capacity;
      this.items[this.rear] = element; 
      this.currentLength++;
      if (this.front === -1) {
        this.front = this.rear;
      }
    }
  }
  
  dequeue() {
    if(this.isEmpty()) {
      return null;
    }
    const item = this.items[this.front];
    this.items[this.front] = null;
    this.front = (this.front + 1) % this.capacity;
    this.currentLength--;
    if(this.isEmpty()) {
      this.front = -1;
      this.rear = -1;
    }
    return item;
  }
  
  peek() {
    if(!this.isEmpty()) {
      return this.items[this.front]
    }
    return null;
  }
  
  print() {
    if(this.isEmpty()) {
      console.log('Queue is empty');
    } else {
      let i;
      let str = '';
      for(i = this.front; i !== this.rear; i = (i+1) % this.capacity) {
        str += this.items[i] + " ";
      }
      str += this.items[i];
      console.log(str);
    }
  }
}

const queue = new CircularQueue(5);
console.log(queue.isEmpty());

queue.enqueue(10);
queue.enqueue(20);
queue.enqueue(30);
queue.enqueue(40);
queue.enqueue(50);

console.log(queue.isFull());
queue.print();

console.log(queue.dequeue());
console.log(queue.peek());
queue.print();
queue.enqueue(60);
queue.print();