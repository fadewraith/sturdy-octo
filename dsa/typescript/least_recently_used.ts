type Node<T> = {
  value: T,
  next?: Node<T>,
  prev?: Node<T>,
}

function createNode<V>(value: V): Node<V> {
  return { value };
}

class LRU<K, V> {
  private length: number;
  private head?: Node<V>;
  private tail?: Node<V>;
  
  private lookup: Map<K, Node<V>>;
  private reverseLookup: Map<Node<V>, K>;
  
  constructor(private capacity: number = 10) {
    this.length = 0;
    this.head = this.tail = undefined;
    this.lookup = new Map<K, Node<V>>();
    this.reverseLookup = new Map<Node<V>, K>();
  }
  
  update(key: K, value: V): void {
    if (this.capacity === 0) {
      return; // No operation allowed if the capacity is 0
    }
    // does it exist ?
    let node = this.lookup.get(key);
    if(!node) {
      node = createNode(value);
      this.length++;
      this.prepend(node);
      this.trimCache();
      
      this.lookup.set(key, node);
      this.reverseLookup.set(node, key);
    } else {
      this.detach(node);
      this.prepend(node);
      node.value = value;
    }
    
    // if it doesnt we need to insert
    //  - check capacity and evict if over
    // it if does, we need to update to the front of the list
    // and update the value
  }
  
  get(key: K): V | undefined {
    // check the cache for existence
    const node = this.lookup.get(key);
    if(!node) {
      return undefined;
    }
    
    // update the value we found and move it to the front
    this.detach(node);
    this.prepend(node);
    
    // return out the value found or undefined if not exist
    return node.value;
  }
  
  private detach(node: Node<V>) {
    if(node.prev) {
      node.prev.next = node.next;
    }
    
    if(node.next) {
      node.next.prev = node.prev;
    }
    
    
    
    if(this.head === node) {
      this.head = this.head.next;
    }
    
    if(this.tail === node) {
      this.tail = this.tail.prev;
    }
    
    node.next = undefined;
    node.prev = undefined;
  }
  
  private prepend(node: Node<V>) {
    if(!this.head) {
      this.head = this.tail = node;
      return;
    }
    
    node.next = this.head;
    this.head.prev = node;
    this.head = node;
  }
  
  private trimCache(): void {
    if(this.length <= this.capacity) {
      return;
    }
    
    const tail = this.tail as Node<V>;
    this.detach(this.tail as Node<V>);
    
    const key = this.reverseLookup.get(tail) as K;
    this.lookup.delete(key);
    this.reverseLookup.delete(tail);
    this.length--;
    
  }
}

function runLRUTests() {
  const lru = new LRU<string, number>(3);

  // Test Case 1: Basic Insertion and Retrieval
  lru.update("a", 1);
  lru.update("b", 2);
  console.assert(lru.get("a") === 1, "Test Case 1 Failed: 'a' should return 1");
  console.assert(lru.get("b") === 2, "Test Case 1 Failed: 'b' should return 2");

  // Test Case 2: Exceeding Capacity
  lru.update("c", 3);
  lru.update("d", 4); // 'a' should be evicted
  console.assert(lru.get("a") === undefined, "Test Case 2 Failed: 'a' should be evicted");
  console.assert(lru.get("d") === 4, "Test Case 2 Failed: 'd' should return 4");

  // Test Case 3: Update Existing Value
  lru.update("b", 5); // 'b' should move to the front
  console.assert(lru.get("b") === 5, "Test Case 3 Failed: 'b' should return 5");

  // Test Case 4: Eviction Order
  lru.update("e", 6); // 'c' should be evicted
  console.assert(lru.get("c") === undefined, "Test Case 4 Failed: 'c' should be evicted");
  console.assert(lru.get("e") === 6, "Test Case 4 Failed: 'e' should return 6");

  // Test Case 5: Retrieve Non-Existent Key
  console.assert(lru.get("z") === undefined, "Test Case 5 Failed: 'z' should return undefined");

  // Test Case 6: Check Order of Elements
  lru.update("f", 7); // 'd' should be evicted
  console.assert(lru.get("d") === undefined, "Test Case 6 Failed: 'd' should be evicted");
  console.assert(lru.get("b") === 5, "Test Case 6 Failed: 'b' should return 5");
  console.assert(lru.get("e") === 6, "Test Case 6 Failed: 'e' should return 6");
  console.assert(lru.get("f") === 7, "Test Case 6 Failed: 'f' should return 7");

  // Test Case 7: Edge Case - Update in a small capacity LRU (capacity = 1)
  const lruSmall = new LRU<string, number>(1);
  lruSmall.update("x", 10);
  console.assert(lruSmall.get("x") === 10, "Test Case 7 Failed: 'x' should return 10");
  lruSmall.update("y", 20); // 'x' should be evicted
  console.assert(lruSmall.get("x") === undefined, "Test Case 7 Failed: 'x' should be evicted");
  console.assert(lruSmall.get("y") === 20, "Test Case 7 Failed: 'y' should return 20");

  // Test Case 8: Edge Case - Empty LRU
  const lruEmpty = new LRU<string, number>(0);
  lruEmpty.update("a", 1);
  console.assert(lruEmpty.get("a") === undefined, "Test Case 8 Failed: LRU with 0 capacity should not store anything");

  console.log("All test cases passed!");
}

// Run the test cases
runLRUTests();
