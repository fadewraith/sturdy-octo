type BinaryNode<T> = {
  value: T;
  left: BinaryNode<T> | null;
  right: BinaryNode<T> | null;
};

function search(current: BinaryNode<number> | null, needle: number): boolean {
  if(!current) {
    return false;
  }
  
  if(current.value === needle) {
    return true;
  }
  
  if(current.value < needle) {
    return search(current.right, needle);
  }
  
  return search(current.left, needle);
}

function bst_dfs(head: BinaryNode<number>, needle: number): boolean {
  return search(head, needle);
}

// Define a basic test function
function test_bst_dfs() {
  // Test 1: Empty Tree
  let emptyTree: BinaryNode<number> | null = null;
  console.assert(bst_dfs(emptyTree, 5) === false, 'Test 1 Failed: Empty Tree');

  // Test 2: Single Node Tree - Value Present
  let singleNodeTree: BinaryNode<number> = { value: 5, left: null, right: null };
  console.assert(bst_dfs(singleNodeTree, 5) === true, 'Test 2 Failed: Single Node Tree - Value Present');

  // Test 3: Single Node Tree - Value Not Present
  console.assert(bst_dfs(singleNodeTree, 10) === false, 'Test 3 Failed: Single Node Tree - Value Not Present');

  // Test 4: Two-Level Tree - Value Present (Left)
  let twoLevelTree: BinaryNode<number> = {
    value: 10,
    left: { value: 5, left: null, right: null },
    right: { value: 15, left: null, right: null },
  };
  console.assert(bst_dfs(twoLevelTree, 5) === true, 'Test 4 Failed: Two-Level Tree - Value Present (Left)');

  // Test 5: Two-Level Tree - Value Present (Right)
  console.assert(bst_dfs(twoLevelTree, 15) === true, 'Test 5 Failed: Two-Level Tree - Value Present (Right)');

  // Test 6: Two-Level Tree - Value Not Present
  console.assert(bst_dfs(twoLevelTree, 20) === false, 'Test 6 Failed: Two-Level Tree - Value Not Present');

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
  console.assert(bst_dfs(multiLevelTree, 25) === true, 'Test 7 Failed: Multi-Level Tree - Value Present');

  // Test 8: Multi-Level Tree - Value Not Present
  console.assert(bst_dfs(multiLevelTree, 40) === false, 'Test 8 Failed: Multi-Level Tree - Value Not Present');

  // Test 9: Multi-Level Tree - Root Node Value
  console.assert(bst_dfs(multiLevelTree, 20) === true, 'Test 9 Failed: Multi-Level Tree - Root Node Value');

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
  console.assert(bst_dfs(largeTree, 80) === true, 'Test 10 Failed: Large Tree - Value Present');

  // Test 11: Large Tree - Value Not Present
  console.assert(bst_dfs(largeTree, 90) === false, 'Test 11 Failed: Large Tree - Value Not Present');

  console.log('All tests passed!');
}

// Run the tests
test_bst_dfs();
