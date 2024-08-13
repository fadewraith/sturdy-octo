type BinaryNode<T> = {
  value: T;
  left?: BinaryNode<T>;
  right?: BinaryNode<T>;
};

// function walk(current: BinaryNode<number> | undefined, path: number[]): number[] {
//   if(!current) {
//     return path;
//   }
  
//   // pre order
//   path.push(current.value);
//   walk(current.left, path);
//   walk(current.right, path);
  
//   // // in order
//   // walk(current.left, path);
//   // path.push(current.value);
//   // walk(current.right, path);
  
//   // // post order
//   // walk(current.left, path);
//   // walk(current.right, path);
//   // path.push(current.value);
  
//   // post
//   return path;
  
// }

// function pre_order_search(head: BinaryNode<number>): number[] {
//   return walk(head, []);
// }

// function in_order_search(head: BinaryNode<number>): number[] {
//   return walk(head, []);
// }

// function post_order_search(head: BinaryNode<number>): number[] {
//   return walk(head, []);
// }


function walk(
  current: BinaryNode<number> | undefined,
  path: number[],
  order: 'pre' | 'in' | 'post'
): number[] {
  if (!current) {
    return path;
  }

  if (order === 'pre') {
    path.push(current.value);
  }

  walk(current.left, path, order);

  if (order === 'in') {
    path.push(current.value);
  }

  walk(current.right, path, order);

  if (order === 'post') {
    path.push(current.value);
  }

  return path;
}

function pre_order_search(head: BinaryNode<number>): number[] {
  return walk(head, [], 'pre');
}

function in_order_search(head: BinaryNode<number>): number[] {
  return walk(head, [], 'in');
}

function post_order_search(head: BinaryNode<number>): number[] {
  return walk(head, [], 'post');
}


function testPreOrder() {
  // Test 1: Empty Tree
  let tree: BinaryNode<number> | undefined = undefined;
  let result = pre_order_search(tree!);
  console.assert(JSON.stringify(result) === JSON.stringify([]), `Empty tree failed`);

  // Test 2: Single Node Tree
  tree = { value: 1 };
  result = pre_order_search(tree);
  console.assert(JSON.stringify(result) === JSON.stringify([1]), `Single node tree failed`);

  // Test 3: Tree with Only Left Subtree
  tree = {
    value: 1,
    left: { value: 2, left: { value: 3 }, right: undefined },
    right: undefined,
  };
  result = pre_order_search(tree);
  console.assert(JSON.stringify(result) === JSON.stringify([1, 2, 3]), `Only left subtree failed`);

  // Test 4: Tree with Only Right Subtree
  tree = {
    value: 1,
    left: undefined,
    right: { value: 2, left: undefined, right: { value: 3 } },
  };
  result = pre_order_search(tree);
  console.assert(JSON.stringify(result) === JSON.stringify([1, 2, 3]), `Only right subtree failed`);

  // Test 5: Full Tree
  tree = {
    value: 1,
    left: { value: 2, left: { value: 4 }, right: { value: 5 } },
    right: { value: 3, left: undefined, right: { value: 6 } },
  };
  result = pre_order_search(tree);
  console.assert(
    JSON.stringify(result) === JSON.stringify([1, 2, 4, 5, 3, 6]),
    `Full tree failed`
  );
}

function testInOrder() {
  // Test 1: Empty Tree
  let tree: BinaryNode<number> | undefined = undefined;
  let result = in_order_search(tree!);
  console.assert(JSON.stringify(result) === JSON.stringify([]), `Empty tree failed`);

  // Test 2: Single Node Tree
  tree = { value: 1 };
  result = in_order_search(tree);
  console.assert(JSON.stringify(result) === JSON.stringify([1]), `Single node tree failed`);

  // Test 3: Tree with Only Left Subtree
  tree = {
    value: 1,
    left: { value: 2, left: { value: 3 }, right: undefined },
    right: undefined,
  };
  result = in_order_search(tree);
  console.assert(JSON.stringify(result) === JSON.stringify([3, 2, 1]), `Only left subtree failed`);

  // Test 4: Tree with Only Right Subtree
  tree = {
    value: 1,
    left: undefined,
    right: { value: 2, left: undefined, right: { value: 3 } },
  };
  result = in_order_search(tree);
  console.assert(JSON.stringify(result) === JSON.stringify([1, 2, 3]), `Only right subtree failed`);

  // Test 5: Full Tree
  tree = {
    value: 1,
    left: { value: 2, left: { value: 4 }, right: { value: 5 } },
    right: { value: 3, left: undefined, right: { value: 6 } },
  };
  result = in_order_search(tree);
  console.assert(
    JSON.stringify(result) === JSON.stringify([4, 2, 5, 1, 3, 6]),
    `Full tree failed`
  );
}


function testPostOrder() {
  // Test 1: Empty Tree
  let tree: BinaryNode<number> | undefined = undefined;
  let result = post_order_search(tree!);
  console.assert(JSON.stringify(result) === JSON.stringify([]), `Empty tree failed`);

  // Test 2: Single Node Tree
  tree = { value: 1 };
  result = post_order_search(tree);
  console.assert(JSON.stringify(result) === JSON.stringify([1]), `Single node tree failed`);

  // Test 3: Tree with Only Left Subtree
  tree = {
    value: 1,
    left: { value: 2, left: { value: 3 }, right: undefined },
    right: undefined,
  };
  result = post_order_search(tree);
  console.assert(JSON.stringify(result) === JSON.stringify([3, 2, 1]), `Only left subtree failed`);

  // Test 4: Tree with Only Right Subtree
  tree = {
    value: 1,
    left: undefined,
    right: { value: 2, left: undefined, right: { value: 3 } },
  };
  result = post_order_search(tree);
  console.assert(JSON.stringify(result) === JSON.stringify([3, 2, 1]), `Only right subtree failed`);

  // Test 5: Full Tree
  tree = {
    value: 1,
    left: { value: 2, left: { value: 4 }, right: { value: 5 } },
    right: { value: 3, left: undefined, right: { value: 6 } },
  };
  result = post_order_search(tree);
  console.assert(
    JSON.stringify(result) === JSON.stringify([4, 5, 2, 6, 3, 1]),
    `Full tree failed`
  );
}

function runAllTests() {
  testPreOrder();
  testInOrder();
  testPostOrder();
  console.log('All test cases passed successfully!');
}

runAllTests();
