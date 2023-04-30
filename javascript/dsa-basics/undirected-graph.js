/*
  Types of graph - 
  directed - edge have a direction
  undirected - edges are bidirectional
  ex - google maps, social media sites
  graph representation - adjacency matrix, adjacency list
  adjacency matrix - 
  adjacency list - vertices are stored in a map like d.s., and every vertex stores a list of its adjacent vertices
  storage wise, adjacency list is way more efficient, because in adjacency list, we only need to store the values for the edges that exist.
  --> in adjacency list, insertion and searching adjacnt nodes is constant time and it also allows to store additional values such as weight of the edge
  all the methods have constant time complexity, except for the removeVertex, whose complexity depends on number of adjacent vertices
*/

// within array, subarrays for each vertex, A, B, C, where connection means 1, A -> b (1)
// 3 items from 1 vertex to other
// adjacency matrix
/**
const matrix = [
  [0, 1, 0],
  [1, 0, 1],
  [0, 1, 0]
]

console.log(matrix[0][0]);


adjacencyList = {
  'A': ['B'],
  'B': ['A', 'C'],
  'C': ['B']
}

console.log(adjacencyList['C']);
*/

// undirected graph
class Graph {
  constructor() {
    this.adjacencyList = {};
  }
  
  addVertex(vertex) {
    if (!this.adjacencyList[vertex]) {
      this.adjacencyList[vertex] = new Set(); // for better performance, use set
    }
  }
  
  addEdge(vertex1, vertex2) {
    if (!this.adjacencyList[vertex1]) {
      this.addVertex(vertex1);
    }
    if (!this.adjacencyList[vertex2]) {
      this.addVertex(vertex2);
    }
    //  to establish a connection, add is available on set d.s, here vertex2 is adjacent to vertex1
    this.adjacencyList[vertex1].add(vertex2);
    this.adjacencyList[vertex2].add(vertex1); 
  }
  
  removeEdge(vertex1, vertex2) {
    // delete is built in of Set d.d
    this.adjacencyList[vertex1].delete(vertex2);
    this.adjacencyList[vertex2].delete(vertex1);
  }
  
  removeVertex(vertex) {
    if (!this.adjacencyList[vertex]) {
      return;
    }
    for(let adjacentVertex of this.adjacencyList[vertex]) {
      this.removeEdge(vertex, adjacentVertex);
    }
    delete this.adjacencyList[vertex];
  }
  
  display() {
    for(let vertex in this.adjacencyList) {
      console.log(vertex + " --> " + [...this.adjacencyList[vertex]]);
    }
  }
  
  hasEdge(vertex1, vertex2) {
    // using has method on set data structure
    return (
      this.adjacencyList[vertex1].has(vertex2) && this.adjacencyList[vertex2].has(vertex1)
    );
  }
}

const graph = new Graph();
graph.addVertex("A");
graph.addVertex("B");
graph.addVertex("C");

graph.addEdge("A", "B");
graph.addEdge("B", "C");

graph.display();
console.log(graph.hasEdge("A", "C"));
// graph.removeEdge("A", "B");
graph.removeVertex("B");
graph.display();