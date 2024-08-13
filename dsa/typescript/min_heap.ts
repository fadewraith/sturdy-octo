class MinHeap {
  public length: number;
  private data: number[];
  
  constructor() {
    this.data = [];
    this.length = 0;
  }
  
  insert(value: number): void {
    this.data[this.length] = value;
    this.heapify_up(this.length);
    this.length++;
  }
  
  delete(): number | undefined {
    if(this.length === 0) {
      return -1;
    }
    
    const out = this.data[0];
    this.length--;
    
    if(this.length === 0) {
      this.data = [];
      return out;
    }
    
    this.data[0] = this.data[this.length];
    this.heapify_down(0);
    
    return out;
  }
  
  private heapify_down(index: number): void {
    
    const left_index = this.left_child(index);
    const right_index = this.right_child(index);
    
    if(index >= this.length || left_index >= this.length) {
      return;
    }
    
    const left_value = this.data[left_index];
    const right_value = this.data[right_index];
    const value = this.data[index];
    
    if(left_value > right_value && value > right_value) {
      this.data[index] = right_value;
      this.data[right_index] = value;
      this.heapify_down(right_index);
    } else if(right_value > left_value && value > left_value) {
      this.data[index] = left_value;
      this.data[left_index] = value;
      this.heapify_down(left_index);
    }
    
  }
  
  private heapify_up(index: number): void {
    if(index === 0) {
      return;
    }
    
    const p = this.parent(index);
    const parentV = this.data[p];
    const v = this.data[index];
    
    if(parentV > v) {
      this.data[index] = parentV;
      this.data[p] = v;
      this.heapify_up(p);
    }
    
  }
  
  private parent(index: number): number {
    return Math.floor((index - 1) / 2);
  }
  
  private left_child(index: number): number {
    return index * 2 + 1;
  }
  
  private right_child(index: number): number {
    return index * 2 + 2;
  }
}

function test_MinHeap() {
  // Test 1: Create an empty heap
  const heap = new MinHeap();
  console.assert(heap.length === 0, 'Test 1 Failed: Heap should be empty upon creation.');

  // Test 2: Insert single element
  heap.insert(10);
  console.assert(heap.length === 1, 'Test 2 Failed: Heap length should be 1 after inserting one element.');
  console.assert(heap.delete() === 10, 'Test 2 Failed: Deleted value should be 10.');

  // Test 3: Insert multiple elements and validate min-heap property
  heap.insert(20);
  heap.insert(15);
  heap.insert(30);
  heap.insert(5);

  console.assert(heap.delete() === 5, 'Test 3 Failed: Deleted value should be 5 (min).');
  console.assert(heap.delete() === 15, 'Test 3 Failed: Deleted value should be 15.');
  console.assert(heap.delete() === 20, 'Test 3 Failed: Deleted value should be 20.');
  console.assert(heap.delete() === 30, 'Test 3 Failed: Deleted value should be 30.');

  // Test 4: Insert and delete elements to maintain heap property
  heap.insert(50);
  heap.insert(40);
  heap.insert(30);
  heap.insert(20);
  heap.insert(10);

  console.assert(heap.delete() === 10, 'Test 4 Failed: Deleted value should be 10.');
  console.assert(heap.delete() === 20, 'Test 4 Failed: Deleted value should be 20.');
  console.assert(heap.delete() === 30, 'Test 4 Failed: Deleted value should be 30.');
  console.assert(heap.delete() === 40, 'Test 4 Failed: Deleted value should be 40.');
  console.assert(heap.delete() === 50, 'Test 4 Failed: Deleted value should be 50.');

  // Test 5: Insert and delete in bulk
  const bulkHeap = new MinHeap();
  const values = [15, 10, 20, 5, 25, 30];
  values.forEach(value => bulkHeap.insert(value));

  console.assert(bulkHeap.delete() === 5, 'Test 5 Failed: Deleted value should be 5.');
  console.assert(bulkHeap.delete() === 10, 'Test 5 Failed: Deleted value should be 10.');
  console.assert(bulkHeap.delete() === 15, 'Test 5 Failed: Deleted value should be 15.');
  console.assert(bulkHeap.delete() === 20, 'Test 5 Failed: Deleted value should be 20.');
  console.assert(bulkHeap.delete() === 25, 'Test 5 Failed: Deleted value should be 25.');
  console.assert(bulkHeap.delete() === 30, 'Test 5 Failed: Deleted value should be 30.');

  // Test 6: Deleting from an empty heap
  const emptyHeap = new MinHeap();
  console.assert(emptyHeap.delete() === -1, 'Test 6 Failed: Deleting from an empty heap should return -1.');

  // Test 7: Insert and delete negative numbers
  heap.insert(-10);
  heap.insert(-20);
  heap.insert(0);
  heap.insert(5);
  heap.insert(-5);

  console.assert(heap.delete() === -20, 'Test 7 Failed: Deleted value should be -20.');
  console.assert(heap.delete() === -10, 'Test 7 Failed: Deleted value should be -10.');
  console.assert(heap.delete() === -5, 'Test 7 Failed: Deleted value should be -5.');
  console.assert(heap.delete() === 0, 'Test 7 Failed: Deleted value should be 0.');
  console.assert(heap.delete() === 5, 'Test 7 Failed: Deleted value should be 5.');

  // Test 8: Heap after deleting all elements
  heap.insert(1);
  heap.insert(2);
  heap.insert(3);
  heap.delete();
  heap.delete();
  heap.delete();
  console.assert(heap.length === 0, 'Test 8 Failed: Heap should be empty after deleting all elements.');

  console.log('All MinHeap tests passed!');
}

// Run the tests
test_MinHeap();
