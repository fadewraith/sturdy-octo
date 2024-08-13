type GraphEdge = { to: number, weight: number };
type WeightedAdjacencyList = GraphEdge[][];

function walk(
  graph: WeightedAdjacencyList, 
  current: number, 
  needle: number, 
  seen: boolean[], 
  path: number[]): boolean {
  
  if(seen[current]) {
    return false;
  }
  
  seen[current] = true;
  
  // recurse
  // pre
  
  path.push(current);
  
  if(current === needle) {
    return true;
  }
  
  // recurse
  const list = graph[current];
  for(let i = 0; i < list.length; ++i) {
    const edge = list[i];
    
    if(walk(graph, edge.to, needle, seen, path)) {
      return true;
    }
  }
  
  // post
  path.pop();
  
  return false;
}

function dfs_on_adjacency_list(
  graph: WeightedAdjacencyList,
  source: number,
  needle: number
): number[] | null {
  const seen: boolean[] = new Array(graph.length).fill(false);
  const path: number[] = [];
  
  walk(graph, source, needle, seen, path);
  
  if(path.length === 0) {
    return null;
  }
  
  return path;
}


function testDfsOnAdjacencyList() {
  // Helper function to assert equality
  function assertEqual(actual: number[] | null, expected: number[] | null, testName: string) {
    const resultMatch = JSON.stringify(actual) === JSON.stringify(expected);

    if (resultMatch) {
      console.log(`✅ ${testName}`);
    } else {
      console.error(`❌ ${testName}`);
      console.error(`   Expected: ${JSON.stringify(expected)}`);
      console.error(`   Received: ${JSON.stringify(actual)}`);
    }
  }

  // Test Case 1: Simple graph with a direct path
  (function() {
    const graph: WeightedAdjacencyList = [
      [{ to: 1, weight: 1 }],
      [{ to: 2, weight: 1 }],
      [{ to: 3, weight: 1 }],
      [],
    ];
    const result = dfs_on_adjacency_list(graph, 0, 3);
    assertEqual(result, [0, 1, 2, 3], 'Test Case 1: Simple graph with a direct path');
  })();

  // Test Case 2: No path to the needle
  (function() {
    const graph: WeightedAdjacencyList = [
      [{ to: 1, weight: 1 }],
      [{ to: 2, weight: 1 }],
      [],
      [],
    ];
    const result = dfs_on_adjacency_list(graph, 0, 3);
    assertEqual(result, null, 'Test Case 2: No path to the needle');
  })();

  // Test Case 3: Source is the same as the needle
  (function() {
    const graph: WeightedAdjacencyList = [
      [{ to: 1, weight: 1 }],
      [{ to: 2, weight: 1 }],
      [{ to: 3, weight: 1 }],
      [],
    ];
    const result = dfs_on_adjacency_list(graph, 2, 2);
    assertEqual(result, [2], 'Test Case 3: Source is the same as the needle');
  })();

  // Test Case 4: Large graph with multiple paths
  (function() {
    const graph: WeightedAdjacencyList = [
      [{ to: 1, weight: 1 }, { to: 2, weight: 1 }],
      [{ to: 3, weight: 1 }],
      [{ to: 3, weight: 1 }],
      [],
    ];
    const result = dfs_on_adjacency_list(graph, 0, 3);
    assertEqual(result, [0, 1, 3], 'Test Case 4: Large graph with multiple paths');
  })();

  // Test Case 5: Needle is not present in the graph
  (function() {
    const graph: WeightedAdjacencyList = [
      [{ to: 1, weight: 1 }],
      [{ to: 2, weight: 1 }],
      [{ to: 1, weight: 1 }],
      [],
    ];
    const result = dfs_on_adjacency_list(graph, 0, 4);
    assertEqual(result, null, 'Test Case 5: Needle is not present in the graph');
  })();
}

// Run the test cases
testDfsOnAdjacencyList();

