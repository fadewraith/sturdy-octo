/*
  Tree  - hierarchical data structure and it is a non-linear data structure, compared to arrays, linked lists, stacks and queues which are linear data structures. A tree will not contain any loops or cycles.
  ex - file systems for dir structure, a family tree, an organisation tree, DOM, chat bots, abstract syntax trees.
  Terminology - parent, child, root node(origin of the tree), leaf nodes(no child nodes), node with same parents are siblings, ancestor nodes, path(sequence of nodes and edges), distance(no of edges along the shortest paths between 2 nodes), degree(total no of child nodes), degree of tree(max of the degree of node in the tree), depth(no of edges from the root to that node), depth of the root node is always 0, height = no of edges from the deepest leave to that node, height of root node = considered as height of the tree.
  
  Binary Search Tree - is a binary tree having foll. properties - 
    value of each left node must be smaller than the parent node
    value of each right node must be greater than the parent node
    each node has atmost 2 children
  operations - insertion, search, dfs and bfs, deletion
  application - searching, sorting, abstract data types such as lookup tables and priority queues
  
  3 types of DFS - preorder, postorder, inorder
  preorder - read the data root, left, right
  postorder - left, right, read the data root
  inorder - left, read the data root, right
  
  BFS - 
  create a queue
  enqueue the root node
  as long as node exists in the queue - 
    dequeue the node from the front
    read the node's value
    enqueue the node's left child if it exists
    enqueue the node's right child if it exists
*/

class Node {
  constructor(value) {
    this.value = value;
    this.left = null; // left pointer
    this.right = null; // right pointer
  }
}

class BinarySearchTree {
  constructor() {
    this.root = null;
  }
  
  isEmpty() {
    return this.root === null;
  }
  
  insert(value) {
    const newNode = new Node(value);
    if (this.isEmpty()) {
      this.root = newNode;
    } else {
      this.insertNode(this.root, newNode) // done to make the recursion possible
    }
  }
  
  insertNode(root, newNode) {
    if (newNode.value < root.value) {
      if (root.left === null) {
        root.left = newNode;
      } else {
      this.insertNode(root.left, newNode);
      }
    } else {
      if (root.right === null) {
        root.right = newNode;
      } else {
        this.insertNode(root.right, newNode);
      }
    }
  }
  
  search(root, value) {
    if (!root) {
      return false;
    } else {
      if (root.value === value) {
        return true;
      } else if (value < root.value) {
        return this.search(root.left, value);
      } else {
        return this.search(root.right, value);
      }
    }
  }
  
  preOrder(root) {
    if (root) {
      console.log(root.value);
      this.preOrder(root.left);
      this.preOrder(root.right);
    }
  }
  
  inOrder(root) {
    if (root) {
      this.inOrder(root.left);
      console.log(root.value);
      this.inOrder(root.right);
    }
  }
  
  postOrder(root) {
    if (root) {
      this.postOrder(root.left);
      this.postOrder(root.right);
      console.log(root.value);
    }
  }
  
  // bfs
  levelOrder() {
    // array is uses, but use optimised queue implementation
    const queue = [];
    queue.push(this.root);
    while(queue.length) {
      let current = queue.shift();
      console.log(current.value);
      if (current.left) {
        queue.push(current.left);
      }
      if (current.right) {
        queue.push(current.right);
      }
    }
  }
  
  // min node
  min (root) {
    if (!root.left) {
      return root.value;
    } else {
      return this.min(root.left);
    }
  }
  
  // max node
  max (root) {
    if (!root.right) {
      return root.value;
    } else {
      return this.max(root.right);
    }
  }
  
  delete(value) {
    this.root = this.deleteNode(this.root, value);
  }
  
  deleteNode(root, value) {
    if (root === null) {
      return root;
    }
    if (value < root.value) {
      root.left  = this.deleteNode(root.left, value);
    } else if (value > root.value) {
      root.right = this.deleteNode(root.right, value);
    } else {
      if (!root.left && !root.right) {
        return null;
      }
      if (!root.left) {
        return root.right;
      } else if (!root.right) {
        return root.left;
      }
      root.value = this.min(root.right);
      root.right = this.deleteNode(root.right, root.value);
    }
    return root;
  }
}

const bst = new BinarySearchTree();
console.log('Tree is empty? ', bst.isEmpty());

bst.insert(10);
bst.insert(5);
bst.insert(17);
bst.insert(3);
// bst.insert(7);

// console.log(bst.search(bst.root, 10));
// console.log(bst.search(bst.root, 5));
// console.log(bst.search(bst.root, 17));
// console.log(bst.search(bst.root, 15));
// bst.preOrder(bst.root);
// bst.inOrder(bst.root);
// bst.postOrder(bst.root);
// bst.levelOrder();
// console.log(bst.min(bst.root));
// console.log(bst.max(bst.root));
bst.levelOrder();
bst.delete(10);
bst.levelOrder();