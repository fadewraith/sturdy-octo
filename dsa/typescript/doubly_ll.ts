interface LinkedList<T> {
  get length(): number;
  insertAt(item: T, index: number): void;
  remove(item: T): T | undefined;
  removeAt(intex: number): T | undefined;
  append(item: T): void;
  prepend(item: T): void;
  get(index: number): T | undefined;
}

type Node<T> = {
  value: T,
  prev?: Node<T>,
  next?: Node<T>
}

class DoublyLinkedList<T> {
  public length: number;
  private head?: Node<T>;
  private tail?: Node<T>;
  
  constructor() {
    this.length = 0;
    this.head = undefined;
    this.tail = undefined;
  }
  
  prepend(item: T): void {
    const node = { value: item } as Node<T>;
    
    this.length++;
    if(!this.head) {
      this.head = this.tail = node;
      return;
    }
    
    node.next = this.head;
    this.head.prev = node;
    this.head = node;
  }
  
  insertAt(item: T, index: number): void {
    if(index > this.length) {
      throw new Error("Index not found!");
    } 
    
    if(index === this.length) {
      this.append(item);
      return;
    } else if(index === 0) {
      this.prepend(item);
      return;
    }
    
    this.length++;
    const current = this.getAt(index) as Node<T>;
    const node = { value: item } as Node<T>;
    
    node.next = current;
    node.prev = current.prev;
    current.prev = node;
    
    if(node.prev) {
      node.prev.next = node;
    }
  }
  
  append(item: T): void {
    
    this.length++;
    const node = { value: item } as Node<T>;
    
    if(!this.tail) {
      this.head = this.tail = node;
      return;
    }
    
    node.prev = this.tail;
    this.tail.next = node;
    this.tail = node;
    
  }
  
  remove(item: T): T | undefined {
    let current = this.head;
    for(let i = 0; current && i < this.length; ++i) {
      if(current.value === item) {
        break;
      }
      current = current.next;
    }
    
    if(!current) {
      return undefined;
    }
    
    return this.removeNode(current);
  }
  
  get(index: number): T | undefined {
    return this.getAt(index)?.value;
  }
  
  removeAt(index: number): T | undefined {
    const node = this.getAt(index);
    if(!node) {
      return undefined;
    }
    
    return this.removeNode(node);
  }
  
  private removeNode(node: Node<T>): T | undefined {
    
    this.length--;
    
    if(this.length === 0) {
      const out = this.head?.value;
      this.head = this.tail = undefined;
      return out;
    }
    
    if(node.prev) {
      node.prev.next = node.next;
    }
    
     if (node.next) {
      node.next.prev = node.prev;
    }
    
    if (node === this.head) {
      this.head = node.next;
    }
    
    if(node === this.tail) {
      this.tail = node.prev;
    }
    
    node.prev = node.next = undefined;
    return node.value;
  }
  
  
  private getAt(index: number): Node<T> | undefined {
    let current = this.head;
    for(let i = 0; current && i < index; ++i) {
      current = current.next;
    }
    return current;
  }
}

function testDoublyLinkedList() {
  const list = new DoublyLinkedList<number>();

  console.log('Test: Initial length');
  console.assert(list.length === 0, `Expected 0, got ${list.length}`);

  console.log('Test: Prepend elements');
  list.prepend(1);
  console.assert(list.length === 1, `Expected 1, got ${list.length}`);
  console.assert(list.get(0) === 1, `Expected 1, got ${list.get(0)}`);

  list.prepend(2);
  console.assert(list.length === 2, `Expected 2, got ${list.length}`);
  console.assert(list.get(0) === 2, `Expected 2, got ${list.get(0)}`);
  console.assert(list.get(1) === 1, `Expected 1, got ${list.get(1)}`);

  console.log('Test: Append elements');
  list.append(3);
  console.assert(list.length === 3, `Expected 3, got ${list.length}`);
  console.assert(list.get(2) === 3, `Expected 3, got ${list.get(2)}`);

  console.log('Test: Insert at index');
  list.insertAt(4, 1);
  console.assert(list.length === 4, `Expected 4, got ${list.length}`);
  console.assert(list.get(0) === 2, `Expected 2, got ${list.get(0)}`);
  console.assert(list.get(1) === 4, `Expected 4, got ${list.get(1)}`);
  console.assert(list.get(2) === 1, `Expected 1, got ${list.get(2)}`);
  console.assert(list.get(3) === 3, `Expected 3, got ${list.get(3)}`);

  console.log('Test: Remove element');
  let removed = list.remove(4);
  console.assert(removed === 4, `Expected 4, got ${removed}`);
  console.assert(list.length === 3, `Expected 3, got ${list.length}`);
  console.assert(list.get(0) === 2, `Expected 2, got ${list.get(0)}`);
  console.assert(list.get(1) === 1, `Expected 1, got ${list.get(1)}`);
  console.assert(list.get(2) === 3, `Expected 3, got ${list.get(2)}`);

  console.log('Test: Remove at index');
  removed = list.removeAt(1);
  console.assert(removed === 1, `Expected 1, got ${removed}`);
  console.assert(list.length === 2, `Expected 2, got ${list.length}`);
  console.assert(list.get(0) === 2, `Expected 2, got ${list.get(0)}`);
  console.assert(list.get(1) === 3, `Expected 3, got ${list.get(1)}`);

  console.log('Test: Remove non-existent element');
  removed = list.remove(5);
  console.assert(removed === undefined, `Expected undefined, got ${removed}`);
  console.assert(list.length === 2, `Expected 2, got ${list.length}`);

  console.log('Test: Insert at invalid index');
  try {
    list.insertAt(6, 5);
  } catch (e) {
    console.assert(e.message === "Index not found!", `Expected "Index not found!", got ${e.message}`);
  }

  console.log('All tests passed!');
}

testDoublyLinkedList();