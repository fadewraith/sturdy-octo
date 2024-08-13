type Node<T> = {
  value: T,
  prev?: Node<T>
}

class Stack<T> {
  
  public length: number;
  private head?: Node<T>;
  
  constructor() {
    this.head = undefined;
    this.length = 0;
  }
  
  
  push(item: T): void {
    const node = { value: item } as Node<T>;
    
    this.length++;
    if(!this.head) {
      this.head = node;
      return;
    }
    
    node.prev = this.head;
    this.head = node;
  }
  
  pop(): T | undefined {
    this.length = Math.max(0, this.length - 1);
    if(this.length === 0) {
      const head = this.head;
      this.head = undefined;
      return head?.value;
    }
    
    const head = this.head as Node<T>;
    this.head = head.prev;
    
    return head.value;
  }
  
  peek(): T | undefined {
    return this.head?.value;
  }
  
}

const stack1 = new Stack<number>();
stack1.push(10);
stack1.push(20);
stack1.push(30);
console.log(stack1.pop()); // Expected output: 30
console.log(stack1.pop()); // Expected output: 20


const stack2 = new Stack<string>();
stack2.push("apple");
stack2.push("banana");
stack2.push("cherry");
console.log(stack2.peek()); // Expected output: "cherry"


const emptyStack = new Stack<number>();
console.log(emptyStack.pop()); // Expected output: undefined


const stack4 = new Stack<boolean>();
stack4.push(true);
stack4.push(false);
stack4.push(true);
console.log(stack4.pop()); // Expected output: true
console.log(stack4.peek()); // Expected output: false

// Scenario 5: Large Stack
const largeStack = new Stack<number>();
for (let i = 1; i <= 1000; i++) {
  largeStack.push(i);
}
for (let i = 0; i < 500; i++) {
  largeStack.pop();
}
console.log("Top of the stack:", largeStack.peek()); // Expected output: 501


