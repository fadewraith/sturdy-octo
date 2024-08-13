type GraphEdge = { to: number, weight: number };
type WeightedAdjacencyList = GraphEdge[][];
type WeightedAdjacencyMatrix = number[][];
type CompleteGraphEdge = { from: number; to: number; weight: number };

type AdjacencyList = number[][];
type AdjacencyMatrix = number[][];

type GeneralNode<T> = {
  value: T;
  children: GeneralNode<T>[];
}


function hasUnvisited(seen: boolean[], distances: number[]): boolean {
  return seen.some((s, i) => !s && distances[i] < Infinity);
}

function getLowestUnvisited(seen: boolean[], distances: number[]): number {
  let index: number = -1;
  let lowestDistance: number = Infinity;
  for(let i = 0; i < seen.length; ++i) {
    if(seen[i]) {
      continue;
    }
    
    if(lowestDistance > distances[i]) {
      lowestDistance = distances[i];
      index = i;
    }
  }
  
  return index;
}

function dijkstra_list(
  source: number,
  sink: number,
  arr: WeightedAdjacencyList
): number[] {
  
  const seen = new Array(arr.length).fill(false);
  const prev = new Array(arr.length).fill(-1);
  const distances = new Array(arr.length).fill(Infinity);
  distances[source] = 0;
  
  while(hasUnvisited(seen, distances)) {
    const current = getLowestUnvisited(seen, distances);
    
    // if (current === -1) {
    //   break; // No more unvisited nodes with finite distance
    // }
    
    seen[current] = true;
    
    const adjacencies = arr[current];
    for(let i = 0; i < adjacencies.length; ++i) {
      const edge = adjacencies[i];
      if(seen[edge.to]) {
        continue;
      }
      
      const dist = distances[current] + edge.weight;
      if(dist < distances[edge.to]) {
        distances[edge.to] = dist;
        prev[edge.to] = current;
      }
    }
  }
  
  const out: number[] = [];
  let curr = sink;
  
  if (distances[curr] === Infinity) {
    return out; // If sink is unreachable, return an empty array
  }
  
  while(prev[curr] !== -1) {
    out.push(curr);
    curr = prev[curr];
  }
  
  out.push(source);
  return out.reverse();
}

function testDijkstraList() {
  function assertEqual(actual: number[], expected: number[], testName: string) {
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
      [{ to: 2, weight: 2 }],
      [{ to: 3, weight: 3 }],
      []
    ];
    const result = dijkstra_list(0, 3, graph);
    assertEqual(result, [0, 1, 2, 3], 'Test Case 1: Simple graph with a direct path');
  })();

  // Test Case 2: No path to the sink
  (function() {
    const graph: WeightedAdjacencyList = [
      [{ to: 1, weight: 1 }],
      [{ to: 2, weight: 2 }],
      [],
      []
    ];
    const result = dijkstra_list(0, 3, graph);
    assertEqual(result, [], 'Test Case 2: No path to the sink');
  })();

  // Test Case 3: Source is the same as sink
  (function() {
    const graph: WeightedAdjacencyList = [
      [{ to: 1, weight: 1 }],
      [{ to: 2, weight: 2 }],
      [{ to: 3, weight: 3 }],
      []
    ];
    const result = dijkstra_list(2, 2, graph);
    assertEqual(result, [2], 'Test Case 3: Source is the same as sink');
  })();

  // Test Case 4: Graph with multiple paths
  (function() {
    const graph: WeightedAdjacencyList = [
      [{ to: 1, weight: 4 }, { to: 2, weight: 1 }],
      [{ to: 3, weight: 1 }],
      [{ to: 1, weight: 2 }, { to: 3, weight: 5 }],
      []
    ];
    const result = dijkstra_list(0, 3, graph);
    assertEqual(result, [0, 2, 1, 3], 'Test Case 4: Graph with multiple paths');
  })();

  // Test Case 5: Large graph with cycles
  (function() {
    const graph: WeightedAdjacencyList = [
      [{ to: 1, weight: 1 }, { to: 2, weight: 4 }],
      [{ to: 2, weight: 2 }, { to: 3, weight: 5 }],
      [{ to: 3, weight: 1 }],
      [{ to: 0, weight: 3 }]
    ];
    const result = dijkstra_list(0, 3, graph);
    assertEqual(result, [0, 1, 2, 3], 'Test Case 5: Large graph with cycles');
  })();

  // Test Case 6: Disconnected graph
  (function() {
    const graph: WeightedAdjacencyList = [
      [{ to: 1, weight: 2 }],
      [],
      [{ to: 3, weight: 1 }],
      []
    ];
    const result = dijkstra_list(0, 3, graph);
    assertEqual(result, [], 'Test Case 6: Disconnected graph');
  })();

  // Test Case 7: All nodes are connected directly
  (function() {
    const graph: WeightedAdjacencyList = [
      [{ to: 1, weight: 2 }, { to: 2, weight: 5 }],
      [{ to: 2, weight: 1 }, { to: 3, weight: 4 }],
      [{ to: 3, weight: 2 }],
      []
    ];
    const result = dijkstra_list(0, 3, graph);
    assertEqual(result, [0, 1, 2, 3], 'Test Case 7: All nodes are connected directly');
  })();
}

// Run the test cases
testDijkstraList();
