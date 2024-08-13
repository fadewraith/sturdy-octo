type Node<T> = {
  value: T,
  next?: Node<T>
}

class Queue<T> {
  public length: number;
  private head?: Node<T>;
  private tail?: Node<T>;
  
  constructor() {
    this.head = this.tail = undefined;
    this.length = 0;
  }
  
  enqueue(item: T): void {
    const node = { value: item } as Node<T>;
    this.length++;
    if(!this.tail) {
      this.tail = this.head = node;
      return;
    }
    
    this.tail.next = node;
    this.tail = node;
  }
  
  dequeue(): T | undefined {
    if(!this.head) {
      return undefined;
    }
    
    this.length--;
    
    const head = this.head;
    this.head = this.head.next;
    
    head.next = undefined;
    
    if(this.length === 0) {
      this.tail = undefined;
    }
    
    return head.value;
  }
  
  peek(): T | undefined {
    return this.head?.value;
  }
}

const queue1 = new Queue<number>();
queue1.enqueue(10);
queue1.enqueue(20);
queue1.enqueue(30);
console.log(queue1.dequeue()); // Expected output: 10
console.log(queue1.dequeue()); // Expected output: 20

const queue2 = new Queue<string>();
queue2.enqueue("apple");
queue2.enqueue("banana");
queue2.enqueue("cherry");
console.log(queue2.peek()); // Expected output: "apple"

const emptyQueue = new Queue<number>();
console.log(emptyQueue.dequeue()); // Expected output: undefined

const queue4 = new Queue<boolean>();
queue4.enqueue(true);
queue4.enqueue(false);
queue4.enqueue(42);
console.log(queue4.dequeue()); // Expected output: true
console.log(queue4.peek()); // Expected output: false


const largeQueue = new Queue<number>();
for (let i = 1; i <= 1000; i++) {
  largeQueue.enqueue(i);
}
for (let i = 0; i < 500; i++) {
  largeQueue.dequeue();
}
console.log(largeQueue.peek()); // Expected output: 501
