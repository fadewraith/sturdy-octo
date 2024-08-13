type BinaryNode<T> = {
  value: T;
  left: BinaryNode<T> | null;
  right: BinaryNode<T> | null;
};

function binary_tree_bfs(head: BinaryNode<number>, needle: number): boolean {
  
  const q: (BinaryNode<number> | null)[] = [head];
  
  while(q.length) {
    const current = q.shift() as BinaryNode<number> | undefined | null;
    
    if(!current) {
      continue;
    }
    
    // search
    if(current.value === needle) {
      return true;
    }
    
    q.push(current.left);
    q.push(current.right);
  }
  
  return false;
}

function compareBT(a: BinaryNode<number> | null, b: BinaryNode<number> | null): boolean {
  
  // structural check
  if(a === null && b === null) {
    return true;
  }
  
  // // structural check
  // if(a === null || b === null) {
  //   return false;
  // }
  
  // // value check
  // if(a.value !== b.value) {
  //   return false;
  // }
  
  // value check
  if(a?.value !== b?.value) {
    return false;
  }
  
  return compareBT(a.left, b.left) && compareBT(a.right, b.right);
  
  
}

// Test function for binary_tree_bfs
function test_binary_tree_bfs() {
  // Test 1: Empty Tree
  let emptyTree: BinaryNode<number> | null = null;
  console.assert(binary_tree_bfs(emptyTree, 5) === false, 'Test 1 Failed: Empty Tree');

  // Test 2: Single Node Tree - Value Present
  let singleNodeTree: BinaryNode<number> = { value: 5, left: null, right: null };
  console.assert(binary_tree_bfs(singleNodeTree, 5) === true, 'Test 2 Failed: Single Node Tree - Value Present');

  // Test 3: Single Node Tree - Value Not Present
  console.assert(binary_tree_bfs(singleNodeTree, 10) === false, 'Test 3 Failed: Single Node Tree - Value Not Present');

  // Test 4: Two-Level Tree - Value Present (Left)
  let twoLevelTree: BinaryNode<number> = {
    value: 10,
    left: { value: 5, left: null, right: null },
    right: { value: 15, left: null, right: null },
  };
  console.assert(binary_tree_bfs(twoLevelTree, 5) === true, 'Test 4 Failed: Two-Level Tree - Value Present (Left)');

  // Test 5: Two-Level Tree - Value Present (Right)
  console.assert(binary_tree_bfs(twoLevelTree, 15) === true, 'Test 5 Failed: Two-Level Tree - Value Present (Right)');

  // Test 6: Two-Level Tree - Value Not Present
  console.assert(binary_tree_bfs(twoLevelTree, 20) === false, 'Test 6 Failed: Two-Level Tree - Value Not Present');

  // Test 7: Multi-Level Tree - Value Present
  let multiLevelTree: BinaryNode<number> = {
    value: 20,
    left: {
      value: 10,
      left: { value: 5, left: null, right: null },
      right: { value: 15, left: null, right: null },
    },
    right: {
      value: 30,
      left: { value: 25, left: null, right: null },
      right: { value: 35, left: null, right: null },
    },
  };
  console.assert(binary_tree_bfs(multiLevelTree, 25) === true, 'Test 7 Failed: Multi-Level Tree - Value Present');

  // Test 8: Multi-Level Tree - Value Not Present
  console.assert(binary_tree_bfs(multiLevelTree, 40) === false, 'Test 8 Failed: Multi-Level Tree - Value Not Present');

  // Test 9: Multi-Level Tree - Root Node Value
  console.assert(binary_tree_bfs(multiLevelTree, 20) === true, 'Test 9 Failed: Multi-Level Tree - Root Node Value');

  // Test 10: Large Tree - Value Present
  let largeTree: BinaryNode<number> = {
    value: 50,
    left: multiLevelTree,
    right: {
      value: 70,
      left: { value: 60, left: null, right: null },
      right: { value: 80, left: null, right: null },
    },
  };
  console.assert(binary_tree_bfs(largeTree, 80) === true, 'Test 10 Failed: Large Tree - Value Present');

  // Test 11: Large Tree - Value Not Present
  console.assert(binary_tree_bfs(largeTree, 90) === false, 'Test 11 Failed: Large Tree - Value Not Present');

  console.log('All tests passed!');
}

// Run the tests
test_binary_tree_bfs();



// Test function for compareBT
function test_compareBT() {
  // Test 1: Both Trees are Null
  console.assert(compareBT(null, null) === true, 'Test 1 Failed: Both Trees are Null');

  // Test 2: One Tree is Null
  let tree1: BinaryNode<number> | null = { value: 5, left: null, right: null };
  console.assert(compareBT(tree1, null) === false, 'Test 2 Failed: One Tree is Null');
  console.assert(compareBT(null, tree1) === false, 'Test 2 Failed: One Tree is Null');

  // Test 3: Both Trees Identical Single Node
  let tree2: BinaryNode<number> | null = { value: 5, left: null, right: null };
  console.assert(compareBT(tree1, tree2) === true, 'Test 3 Failed: Both Trees Identical Single Node');

  // Test 4: Trees with Different Values
  let tree3: BinaryNode<number> | null = { value: 10, left: null, right: null };
  console.assert(compareBT(tree1, tree3) === false, 'Test 4 Failed: Trees with Different Values');

  // Test 5: Trees with Identical Structure and Values
  let multiLevelTreeA: BinaryNode<number> = {
    value: 20,
    left: {
      value: 10,
      left: { value: 5, left: null, right: null },
      right: { value: 15, left: null, right: null },
    },
    right: {
      value: 30,
      left: { value: 25, left: null, right: null },
      right: { value: 35, left: null, right: null },
    },
  };
  let multiLevelTreeB: BinaryNode<number> = {
    value: 20,
    left: {
      value: 10,
      left: { value: 5, left: null, right: null },
      right: { value: 15, left: null, right: null },
    },
    right: {
      value: 30,
      left: { value: 25, left: null, right: null },
      right: { value: 35, left: null, right: null },
    },
  };
  console.assert(compareBT(multiLevelTreeA, multiLevelTreeB) === true, 'Test 5 Failed: Trees with Identical Structure and Values');

  // Test 6: Trees with Identical Structure but Different Values
  let multiLevelTreeC: BinaryNode<number> = {
    value: 20,
    left: {
      value: 10,
      left: { value: 5, left: null, right: null },
      right: { value: 99, left: null, right: null }, // Different value
    },
    right: {
      value: 30,
      left: { value: 25, left: null, right: null },
      right: { value: 35, left: null, right: null },
    },
  };
  console.assert(compareBT(multiLevelTreeA, multiLevelTreeC) === false, 'Test 6 Failed: Trees with Identical Structure but Different Values');

  console.log('All compareBT tests passed!');
}

// Run the tests
test_compareBT();
