type WeightedAdjacencyMatrix = number[][];

function bfs_on_adjacency_matrix (
  graph: WeightedAdjacencyMatrix,
  source: number,
  needle: number
): number[] | null {
  
   // Edge case: source is the same as needle
  if (source === needle) {
    return [source];
  }
  
  const seen = new Array(graph.length).fill(false);
  const prev = new Array(graph.length).fill(-1);
  
  seen[source] = true;
  const q: number[] = [source];
  
  do {
    const current = q.shift() as number;
    if(current === needle) {
      break;
    }
    
    const adjacencies = graph[current];
    for(let i = 0; i < adjacencies.length; ++i) {
      if(adjacencies[i] === 0) {
        continue;
      }
      
      if(seen[i]) {
        continue;
      }
      seen[i] = true;
      prev[i] = current;
      q.push(i);
      
    }
    
  } while(q.length);
  
  if(prev[needle] === -1) {
    return null;
  }
  
  // build it backwards
  let current = needle;
  const out: number[] = [];
  
  while(prev[current] !== -1) {
    out.push(current);
    current = prev[current];
  }
  
  
  return [source].concat(out.reverse());
  
}


function runBfsTests() {
  function assertEqual(actual: number[] | null, expected: number[] | null, testName: string) {
    const isEqual = JSON.stringify(actual) === JSON.stringify(expected);
    if (isEqual) {
      console.log(`✅ ${testName}`);
    } else {
      console.error(`❌ ${testName}`);
      console.error(`   Expected: ${JSON.stringify(expected)}`);
      console.error(`   Received: ${JSON.stringify(actual)}`);
    }
  }

  // Test Case 1: Simple graph with direct path
  (function() {
    const graph: WeightedAdjacencyMatrix = [
      [0, 1, 0, 0],
      [1, 0, 1, 1],
      [0, 1, 0, 1],
      [0, 1, 1, 0],
    ];
    const source = 0;
    const needle = 3;
    const expected = [0, 1, 3];
    const result = bfs_on_adjacency_matrix(graph, source, needle);
    assertEqual(result, expected, 'Test Case 1: Simple graph with direct path');
  })();

  // Test Case 2: Simple graph with no path
  (function() {
    const graph: WeightedAdjacencyMatrix = [
      [0, 1, 0, 0],
      [1, 0, 0, 0],
      [0, 0, 0, 1],
      [0, 0, 1, 0],
    ];
    const source = 0;
    const needle = 3;
    const expected = null;
    const result = bfs_on_adjacency_matrix(graph, source, needle);
    assertEqual(result, expected, 'Test Case 2: Simple graph with no path');
  })();

  // Test Case 3: Source is the same as the needle
  (function() {
    const graph: WeightedAdjacencyMatrix = [
      [0, 1, 0, 0],
      [1, 0, 1, 1],
      [0, 1, 0, 1],
      [0, 1, 1, 0],
    ];
    const source = 2;
    const needle = 2;
    const expected = [2];
    const result = bfs_on_adjacency_matrix(graph, source, needle);
    assertEqual(result, expected, 'Test Case 3: Source is the same as the needle');
  })();

  // Test Case 4: Large graph with multiple paths
  (function() {
    const graph: WeightedAdjacencyMatrix = [
      [0, 1, 1, 0, 0, 0],
      [1, 0, 0, 1, 1, 0],
      [1, 0, 0, 0, 1, 1],
      [0, 1, 0, 0, 0, 1],
      [0, 1, 1, 0, 0, 1],
      [0, 0, 1, 1, 1, 0],
    ];
    const source = 0;
    const needle = 5;
    const expected = [0, 2, 5];
    const result = bfs_on_adjacency_matrix(graph, source, needle);
    assertEqual(result, expected, 'Test Case 4: Large graph with multiple paths');
  })();

  // Test Case 5: Needle is not present in the graph
  (function() {
    const graph: WeightedAdjacencyMatrix = [
      [0, 1, 0],
      [1, 0, 0],
      [0, 0, 0], // Node 2 is isolated
    ];
    const source = 0;
    const needle = 2;
    const expected = null;
    const result = bfs_on_adjacency_matrix(graph, source, needle);
    assertEqual(result, expected, 'Test Case 5: Needle is not present in the graph');
  })();
}

// Run the tests
runBfsTests();