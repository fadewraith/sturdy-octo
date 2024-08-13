interface Point {
  x: number,
  y: number
}

const dir = [
  [-1, 0],
  [1, 0],
  [0, -1],
  [0, 1]
];

function walk(maze: string[], wall: string, current: Point, end: Point, seen: boolean[][], path: Point[]): boolean {
  // 1. base case
  // off the map
  if(current.x < 0 || current.x >= maze[0].length || current.y < 0 || current.y >= maze.length) {
    return false;
    
  }
  
  // on a wall
  if(maze[current.y][current.x] === wall) {
    return false;
  }
  
  // reached the end point
  if(current.x === end.x && current.y === end.y) {
    // path.push({ ...current }); // push a copy of the current point
    path.push(end);
    return true;
  }
  
  // already seen
  if(seen[current.y][current.x]) {
    return false;
  }
  
  // 3 recurse
  // pre
  seen[current.y][current.x] = true;
  // path.push({ ...current }); // push a copy of the current point
  path.push(current);
  
  // recurse
  for(let i = 0; i < dir.length; ++i) {
    const [x, y] = dir[i];
    if (walk(maze, wall, {
      x: current.x + x,
      y: current.y + y,
    }, end, seen, path)) {
      return true;
    }
  }
  
  // post
  path.pop();
  
  return false;
}

function solve(maze: string[], wall: string, start: Point, end: Point): Point[] {
  // const seen: boolean[][] = Array.from({ length: maze.length }, () => new Array(maze[0].length).fill(false));
  const seen: boolean[][] = [];
  const path: Point[] = [];
  
  for(let i = 0; i < maze.length; ++i) {
    seen.push(new Array(maze[0].length).fill(false));
  }
  
  walk(maze, wall, start, end, seen, path);
  
  return path;
}


function testSolve() {
  const maze = [
    "########",
    "#S     #",
    "# ##### #",
    "#      E#",
    "########"
  ];
  const wall = "#";
  const start: Point = { x: 1, y: 1 };
  const end: Point = { x: 7, y: 3 };

  const path = solve(maze, wall, start, end);
  console.log("Path:", path);
}

function testSolveNoPath() {
  const maze = [
    "########",
    "#S#    #",
    "# ##### #",
    "#      E#",
    "########"
  ];
  const wall = "#";
  const start: Point = { x: 1, y: 1 };
  const end: Point = { x: 7, y: 3 };

  const path = solve(maze, wall, start, end);
  console.log("Path (no path expected):", path);
}

function testSolveSimple() {
  const maze = [
    "###",
    "#S#",
    "#E#",
    "###"
  ];
  const wall = "#";
  const start: Point = { x: 1, y: 1 };
  const end: Point = { x: 1, y: 2 };

  const path = solve(maze, wall, start, end);
  console.log("Path (simple case):", path);
}

testSolve();
testSolveNoPath();
testSolveSimple();
